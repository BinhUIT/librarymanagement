package com.library.librarymanagement.service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.library.librarymanagement.entity.Regulation;
import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.repository.ReulationRepository;
import com.library.librarymanagement.repository.UserRepository;
import com.library.librarymanagement.request.LoginRequest;
import com.library.librarymanagement.request.RegisterRequest;
import com.library.librarymanagement.request.UpdateNormalInfoRequest;
import com.library.librarymanagement.request.UpdatePasswordRequest;
import com.library.librarymanagement.response.LoginResponse;
import com.library.librarymanagement.security.TokenSecurity;
import com.library.librarymanagement.securitySalt.BcryptSalt;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class UserService { 
    @Autowired 
    private UserRepository userRepo;   
    @Autowired 
    private BcryptSalt bcryptSalt;  
    @Autowired 
    private JavaMailSender mailSender; 
    @Autowired 
    private TokenSecurity tokenSecurity;
    @Autowired 
    private ReulationRepository regulationRepo;
    
    private Random rand; 
    public UserService() 
    {
       rand = new Random();
    }
    
    public ResponseEntity<LoginResponse> handleLogin(LoginRequest request) 
    { 
         
        User user = new User(); 
        if(request.getNameOrEmail().contains("@")) 
        {
            user= userRepo.findByEmail(request.getNameOrEmail()); 
        } 
        else 
        {
            user= userRepo.findByFullName(request.getNameOrEmail()); 
        }  
        if(user==null) 
        {
            return new ResponseEntity<>(new LoginResponse(-1,"Fullname or email is not correct",-1), HttpStatus.UNAUTHORIZED);
        } 
        if(user.getEnable()==false) 
        {
            return new ResponseEntity<>(new LoginResponse(user.getUserId(),"You can not use this account, the userId is: " ,-1), HttpStatus.UNAUTHORIZED);
        } 
        

        String pass = BCrypt.hashpw(request.getPassword(), bcryptSalt.getSalt()); 
        if(!pass.equals(user.getPassword())) 
        {
            return new ResponseEntity<>(new LoginResponse(-1,"Wrong password",-1), HttpStatus.UNAUTHORIZED);
        }  
        String token=tokenSecurity.generateToken(user.getFullname());
        return new ResponseEntity<>(new LoginResponse(user.getUserId(), token, user.getRole()), HttpStatus.OK);



    }  

    private String utf8String(String utf8String) 
    { 
        byte[] utf8Bytes = utf8String.getBytes(StandardCharsets.UTF_8); 
        String decodedString = new String(utf8Bytes, StandardCharsets.UTF_8); 
        return decodedString;
    }

    public ResponseEntity<String> handleRegister(RegisterRequest request, String siteURL) throws MessagingException, UnsupportedEncodingException
    { 
        
        User user = userRepo.findByFullName(request.getFullName()); 
        if(user!=null) 
        {
            return new ResponseEntity<>("Người dùng đã tồn tại", HttpStatus.ALREADY_REPORTED); 

        }
        user = userRepo.findByEmail(request.getEmail()); 
        if(user!=null) 
        {
            return new ResponseEntity<>("Email này đã được đăng ký", HttpStatus.ALREADY_REPORTED);
        }  
        if(!checkEmail(request.getEmail())) 
        {  
            return new ResponseEntity<>("Email không hợp lệ", HttpStatus.BAD_REQUEST);
        } 
        if(!checkUserName(request.getFullName())) 
        {
            return new ResponseEntity<>("Thông tin không hợp lệ", HttpStatus.BAD_GATEWAY);
        }
        user = new User(request); 
        List<User> users = userRepo.findAll(); 
        int newId = users.size(); 
        user.setUserId(newId); 
        user.setPassword(BCrypt.hashpw(request.getPassword(), bcryptSalt.getSalt()));  
        int verifyCode= rand.nextInt(10000); 
        String code = Integer.toString(verifyCode); 
        user.setVerificationCode(code);     
        userRepo.save(user);   

        String subject="Verify your email"; 
        String content="Dear [[name]],<br>"
            + "Please click the link below to verify your email:<br>"
            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
            + "Thank you,<br>"
            + "BHD.";
        content = content.replace("[[name]]", user.getFullname()); 
        String verifyURL = siteURL+"/verify?code=" +user.getVerificationCode(); 
        content= content.replace("[[URL]]", verifyURL);  
        sendEmail(user,content, subject);

        return new ResponseEntity<>("Đăng kí thành công, vui lòng kiểm tra email để kích hoạt tài khoản", HttpStatus.OK);
    } 
    public void sendEmail(User user, String content, String subject) throws MessagingException, UnsupportedEncodingException
    {    
        String toAddress = user.getEmail(); 
        String fromAddress = "leyen15121971@gmail.com";  
        String senderName ="Dang Le Binh"; 

        MimeMessage mimeMessage = mailSender.createMimeMessage(); 
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage); 
        helper.setFrom(fromAddress, senderName);  
        helper.setTo(toAddress);  
        helper.setSubject(subject); 
        helper.setText(content, true);  
        mailSender.send(mimeMessage); 

    }  
    public boolean verify(String verificationCode) 
    { 
        User user = userRepo.findByVerificationCode(verificationCode); 
        if(user==null||user.getEnable()) 
        { 
            return false;
        } 
        else 
        {
            user.setVerificationCode(""); 
            user.setEnable(true); 
            userRepo.save(user); 
            return true;
        }
    }  
    
    public ResponseEntity<String> testToken(String token) 
    {  
        if(!token.startsWith("Bearer ") || !tokenSecurity.validateToken(token.substring(7))) 
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED); 
        return new ResponseEntity<>("Accept", HttpStatus.OK);
    }   


    public ResponseEntity<String> changeNormalInfo(UpdateNormalInfoRequest request, String token) 
    {  
        if(!tokenSecurity.checkToken(token)) 
        {
            return new ResponseEntity<>("Something wrong", HttpStatus.NOT_FOUND);
        } 
        int userId= tokenSecurity.extractUserId(token); 
        User userWithName = userRepo.findByFullName(request.getFullname()); 
        
        User user = userRepo.findById(userId).orElse(null); 
        if(user==null) 
        { 
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } 
        if(userWithName!=null&&user.getUserId()!=userWithName.getUserId()) 
        {
            
            return new ResponseEntity<>("User with this name is already exist", HttpStatus.ALREADY_REPORTED); 
        
        } 
        user.setFullname(request.getFullname());
        user.setAddress(request.getAddress()); 
        user.setPhoneNumber(request.getPhoneNumber()); 
        userRepo.save(user); 
        return new ResponseEntity<>("Update user information success", HttpStatus.OK); 

    }  

    public ResponseEntity<String> changePassword(UpdatePasswordRequest request, String token) 
    { 
        if(!tokenSecurity.checkToken(token)) 
        { 
            return new ResponseEntity<>("Something wrong", HttpStatus.NOT_FOUND);
        } 
        int userId = tokenSecurity.extractUserId(token); 
        User user = userRepo.findById(userId).orElse(null); 
        if(user==null) 
        { 
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } 
        String oldPass = BCrypt.hashpw(request.getOldPassword(), bcryptSalt.getSalt()); 
        if(!oldPass.equals(user.getPassword())) 
        {  
            return new ResponseEntity<>("Wrong password", HttpStatus.UNAUTHORIZED);
        } 
        if(!request.getNewPassword().equals(request.getRepeatNewPassword())) 
        { 
            return new ResponseEntity<>("Fail, new password and repeat new password does not match", HttpStatus.BAD_REQUEST);
        } 
        String newPass = BCrypt.hashpw(request.getNewPassword(),bcryptSalt.getSalt()); 
        user.setPassword(newPass); 
        userRepo.save(user); 
        return  new ResponseEntity<>("Change password success", HttpStatus.OK);
    } 


    public ResponseEntity<String> changeEmail(String newEmail, String token, String siteURL) throws MessagingException, UnsupportedEncodingException
    { 
        if(!tokenSecurity.checkToken(token)) 
        { 
            return new ResponseEntity<>("Something wrong", HttpStatus.NOT_FOUND);
        } 
        int userId = tokenSecurity.extractUserId(token); 
        User user = userRepo.findById(userId).orElse(null); 
        if(user==null) 
        { 
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } 
        User userWithEmail = userRepo.findByEmail(newEmail); 
        if(userWithEmail!=null) 
        { 
            return new ResponseEntity<> ("This mail is already be used", HttpStatus.BAD_REQUEST);
        } 
        user.setEmail(newEmail); 

        int verifyCode= rand.nextInt(10000); 
        String code = Integer.toString(verifyCode); 
        user.setVerificationCode(code); 
        user.setEnable(false); 
        userRepo.save(user); 

        String subject="Verify your email"; 
        String content="Dear [[name]],<br>"
            + "Please click the link below to verify your email:<br>"
            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
            + "Thank you,<br>"
            + "BHD.";
        content = content.replace("[[name]]", user.getFullname()); 
        String verifyURL = siteURL+"/verify?code=" +user.getVerificationCode(); 
        content= content.replace("[[URL]]", verifyURL);  
        sendEmail(user,content, subject);   

        return new ResponseEntity<>("We sent verify email to your new email address, please confirm and login again to use our web", HttpStatus.OK);

    }
    

    public ResponseEntity<String> forgetPassword(String nameOrEmail)  throws MessagingException, UnsupportedEncodingException
    {  
        
        User user = new User(); 
        
        user = userRepo.findByEmail(nameOrEmail);
        
        if(user==null) 
        { 
            return new ResponseEntity<>("Chúng tôi đã gửi mật khẩu mới đến email của bạn, dùng mật khẩu đó để đăng nhập và thay đổi mật khẩu", HttpStatus.OK);
        } 
        String randomPassword = Integer.toString(rand.nextInt(100000)); 
        user.setPassword(BCrypt.hashpw(randomPassword, bcryptSalt.getSalt())); 
        userRepo.save(user); 

        String subject = "Reset your password"; 
        String content = "Dear [[name]]<br>" + 
        "This is your new password, use it to login and change it to your password<br>" + 
        "<h2>[[newPassword]]</h2>"+ 
        "Thank you,<br>" + 
        "BHD"; 
        content=content.replace("[[name]]", user.getFullname()); 
        content = content.replace("[[newPassword]]", randomPassword); 
        sendEmail(user, content, subject); 
        return new ResponseEntity<>("Chúng tôi đã gửi mật khẩu mới đến email của bạn, dùng mật khẩu đó để đăng nhập và thay đổi mật khẩu", HttpStatus.OK); 
        
        
    } 


    public User findUserById(int userId) { 
        return userRepo.findById(userId).orElse(null); 
    }   

    public boolean checkEmail(String email) 
    {
        //Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$ "); 
        //Matcher matcher = pattern.matcher(email); 
        return email.contains("@");
    } 
    public boolean checkUserName(String fullname) 
    { 
        Pattern pattern = Pattern.compile("[\\d\\~\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\=\\\\\\|\\{\\}\\[\\]\\;\\:\\\"\\'\\<\\,\\>\\.\\?\\/]");  
        Matcher matcher = pattern.matcher(fullname); 
        return !matcher.find();

    } 

    public Regulation getRegulation() 
    {
        return regulationRepo.findById(1).orElse(null);
    }
} 
