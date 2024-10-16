package com.library.librarymanagement.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.library.librarymanagement.JWTBlackList.JWTBlackList;
import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.repository.UserRepository;
import com.library.librarymanagement.request.ChangePasswordRequest;
import com.library.librarymanagement.request.RegisterRequest;
import com.library.librarymanagement.request.UserChangeNameRequest;
import com.library.librarymanagement.request.UserLoginRequest;
import com.library.librarymanagement.request.UserResetPasswordRequest;
import com.library.librarymanagement.request.UserUpdateEmailRequest;
import com.library.librarymanagement.request.UserUpdateNormalInfoRequest;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage; 
@Service
public class UserService { 
    @Autowired 
    private UserRepository userRepo;  
    @Autowired 
    private AuthenticationManager authManager;  
    @Autowired 
    private JWTService jwtService; 
    @Autowired 
    private JavaMailSender mailSender;   
    @Autowired 
    private JWTBlackList jwtBlackList;
    private Random rand= new Random(); 
    private final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder(12); 
    public String login(UserLoginRequest loginRequest) 
    {  
        Authentication auth= authManager.authenticate( new UsernamePasswordAuthenticationToken(loginRequest.getFullname(), loginRequest.getPassword()));  
        if(auth.isAuthenticated()) 
        {
            return "Success:" + jwtService.generateToken(loginRequest.getFullname());
        } 
        return "Failed";

    } 
    public String register(RegisterRequest request, String siteURL)  throws MessagingException, UnsupportedEncodingException
    { 
        int randomCode = rand.nextInt(1000);  
        String verificationCode=Integer.toString(randomCode);

        List<User> users = userRepo.findAll();  
        int newId=users.size();  
        User user = userRepo.findByFullName(request.getFulllname());
        if(user!=null) return "This name is already be used";   
        String pass = passwordEncoder.encode(request.getPassword());
        user = new User(newId, request.getFulllname(),request.getAddress(), request.getPhoneNumber(), request.getEmail(),false, pass, false,verificationCode); 
        userRepo.save(user); 
        sendVerificationEmail(user, siteURL); 
        return "Register success";
    } 
    public void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException 
    {   
        String toAddress = user.getEmail(); 
        String fromAddress = "leyen15121971@gmail.com"; 
        String senderName="Dang Le Binh"; 
        String subject="Verify your email"; 
        String content="Dear [[name]],<br>"
            + "Please click the link below to verify your email:<br>"
            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
            + "Thank you,<br>"
            + "BHD."; 
        MimeMessage mimeMessage = mailSender.createMimeMessage(); 
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage); 
        helper.setFrom(fromAddress, senderName); 
        helper.setTo(toAddress); 
        helper.setSubject(subject); 
         
        content = content.replace("[[name]]", user.getFullname()); 
        String verifyURL = siteURL+"/verify?code=" +user.getVerificationCode(); 
        content= content.replace("[[URL]]", verifyURL);  
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
    public String changeNormalInfo(UserUpdateNormalInfoRequest updateRequest) 
    {
        User user = userRepo.findById(updateRequest.getUserId()).orElse(null);  
        if(user==null) 
        return "User does not exist";
        user.setAddress(updateRequest.getAddress()); 
        user.setPhoneNumber(updateRequest.getPhoneNumber());   
        userRepo.save(user);
        return "Update information success";
    } 
     
    public String changeEmail(UserUpdateEmailRequest request, String siteURL) throws MessagingException, UnsupportedEncodingException 
    {  
        User user = userRepo.findById(request.getUserId()).orElse(null); 
        if(user==null) return "User does not exit"; 
        user.setEmail(request.getEmail()); 
        user.setEnable(false);   
        int randomCode= rand.nextInt(10000); 
        String verificationCode = Integer.toString(randomCode); 
        user.setVerificationCode(verificationCode);
        sendVerificationEmail(user, siteURL); 
        userRepo.save(user);
        return "Email updated, we sent verification link to " + request.getEmail();
    } 

    public String changePassword(ChangePasswordRequest request) 
    {
        User user = userRepo.findById(request.getUserId()).orElse(null); 
        if(user==null) return "User does not exist"; 
        String pass = passwordEncoder.encode(request.getOldPassword());  
        if(pass!=user.getPassword()) return "Old password does not correct!"; 
        if(request.getNewPassword()!=request.getConfirmPassword()) 
        return "New password and confirm password are not correct";  
         
        pass= passwordEncoder.encode(request.getNewPassword()); 
        user.setPassword(pass);  
        userRepo.save(user);
        return "Change password success";

    } 
    public String forgetPassword(int userId, String siteURL) throws MessagingException, UnsupportedEncodingException 
    { 
        User user = userRepo.findById(userId).orElse(null);  
        if(user==null) return "User does not exist"; 
        String toAddress= user.getEmail(); 
        String fromAddress = "leyen15121971@gmail.com";  
        String senderName="Dang Le Binh"; 
        String subject="Reset your password"; 
        String content="Dear [[name]],<br>"
            + "Please click the link below to reset your password:<br>"
            + "<h3><a href=\"[[URL]]\" target=\"_self\">RESET PASSWORD</a></h3>"
            + "Thank you,<br>"
            + "BHD."; 
        MimeMessage mimeMessage = mailSender.createMimeMessage(); 
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage); 
        helper.setFrom(fromAddress, senderName); 
        helper.setTo(toAddress);  
        helper.setSubject(subject); 
 
        content.replace("[[name]]",user.getFullname()); 
        String resetURL = siteURL +"/resetPassword"; 
        content.replace("[[URL]]", resetURL);
        helper.setText(content, true);  
        mailSender.send(mimeMessage); 
        return "We sent reset link to your email, check your email to reset password";

    } 
    public String resetPassword(UserResetPasswordRequest request) 
    {
        User user = userRepo.findById(request.getUserId()).orElse(null); 
        if(user==null) return "User does not exist"; 
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));  
        userRepo.save(user);
        return "Password reset sucessfully";
    }
     
    public String changeUserName(UserChangeNameRequest request, String token)  
    {  

        User user = userRepo.findById(request.getUserId()).orElse(null); 
        if(user==null) return "User does not exist"; 
        User u = userRepo.findByFullName(request.getFullName()); 
        if(u!=null) return "This name is already be used"; 
        jwtBlackList.addToken(token); 
        user.setFullname(request.getFullName()); 
        userRepo.save(user); 
        return "Change name success, new token: " +jwtService.generateToken(user.getFullname());

    }
}
