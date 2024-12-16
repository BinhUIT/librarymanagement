package com.library.librarymanagement.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.librarymanagement.entity.BookImagePath;
import com.library.librarymanagement.entity.BookStatus;
import com.library.librarymanagement.entity.BookTitleImagePath;
import com.library.librarymanagement.entity.BorrowingCardDetail;
import com.library.librarymanagement.entity.CartDetail;
import com.library.librarymanagement.entity.Notification;
import com.library.librarymanagement.entity.RenewalCardDetail;
import com.library.librarymanagement.entity.ServiceType;
import com.library.librarymanagement.entity.UnlockRequest;
import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.repository.BookStatusRepository;
import com.library.librarymanagement.repository.BookTitleRepository;
import com.library.librarymanagement.repository.BorrowingCardDetailRepository;
import com.library.librarymanagement.repository.CartDetailRepository;
import com.library.librarymanagement.repository.NotificationRepository;
import com.library.librarymanagement.repository.RenewalDetailRepository;
import com.library.librarymanagement.repository.ServiceRepository;
import com.library.librarymanagement.repository.ServiceTypeRepository;
import com.library.librarymanagement.repository.UnlockRequestRepository;
import com.library.librarymanagement.repository.UserRepository;
import com.library.librarymanagement.request.AddCartDetailRequest;
import com.library.librarymanagement.request.BorrowingDetailRequest;
import com.library.librarymanagement.request.BorrowingRequest;
import com.library.librarymanagement.request.CartDetailUpdateRequest;
import com.library.librarymanagement.request.RenewalCardDetailRequest;
import com.library.librarymanagement.request.RenewalRequest;
import com.library.librarymanagement.response.CartDetailResponse;
import com.library.librarymanagement.response.ResponseData;
import com.library.librarymanagement.security.TokenSecurity;


@Service
public class ReaaderService {

   @Autowired 
   private UserRepository userRepo; 
   @Autowired 
   private ServiceTypeRepository serviceTypeRepo; 
   @Autowired 
   private ServiceRepository serviceRepo;  
   @Autowired 
   private BookStatusRepository bookStatusRepo; 
   @Autowired 
   private BookRepository bookRepo; 
   @Autowired 
   private BookTitleRepository bookTitleRepo; 
   @Autowired 
   private BorrowingCardDetailRepository borrowingDetailRepo; 
   @Autowired 
   private NotificationRepository notifRepo; 
   @Autowired 
   private CartDetailRepository cartDetailRepo; 

   @Autowired 
   private RenewalDetailRepository renewalDetailRepo; 

   @Autowired 
   private TokenSecurity tokenSecurity; 

    @Autowired 
   private UnlockRequestRepository unlockRequestRepo;
   
   public ResponseEntity<ResponseData> borrowingBook(BorrowingRequest request) 
   {   
        List<BorrowingDetailRequest> listBookTitle = request.getListBook();  
        int borrowingDetailAmount = borrowingDetailRepo.findAll().size();  
        int newServiceId = serviceRepo.findAll().size();  
        List<BorrowingCardDetail> detailList = new ArrayList<>();
        User reader = userRepo.findById(request.getServiceRequest().getReaderId()).orElse(null); 
        if(reader==null) 
        { 
            return new ResponseEntity<>(new ResponseData("Reader does not exists",null), HttpStatus.BAD_REQUEST);
        } 
        ServiceType serviceType = serviceTypeRepo.findById(request.getServiceRequest().getServiceTypeId()).orElse(null);
        if(serviceType==null) 
        { 
            return new ResponseEntity<>(new ResponseData("We do not have this service",null), HttpStatus.BAD_REQUEST);
        } 
        com.library.librarymanagement.entity.Service newService = new com.library.librarymanagement.entity.Service(newServiceId,
        reader, new Date(), serviceType);
        
        for(BorrowingDetailRequest i : listBookTitle) 
        {   
            BookTitleImagePath bookTitle = bookTitleRepo.findById(i.getBookTitleId()).orElse(null);
            if(bookTitle==null) 
            { 
                return new ResponseEntity<>(new ResponseData("Book does not exists",null), HttpStatus.BAD_REQUEST);
            } 
            if(bookTitle.getAmountRemaining()<i.getAmount()) 
            { 
               return new ResponseEntity<>(new ResponseData("Not enough book",null), HttpStatus.BAD_REQUEST);
            }  
            
            List<BookImagePath> listBookWithTitle = bookRepo.findByTitle(bookTitle);
            int bookIndex=0; 
            for(BookImagePath book: listBookWithTitle)  
            { 
                
                if(bookIndex>=i.getAmount()) 
                { 
                    break;
                } 
                if(book.getStatus().getId()==0) {  
                    Integer val=1;
                    book.setStatus(new BookStatus(val.byteValue(),"Borrowing"));
                    bookRepo.save(book);  
                    Date sendDate = request.getServiceRequest().getImplementDate(); 
                    Calendar calendar = Calendar.getInstance(); 
                    calendar.setTime(sendDate); 
                    calendar.add(Calendar.DAY_OF_MONTH, 30); 
                    Date expireDate = calendar.getTime();
                    BorrowingCardDetail newDetail = new BorrowingCardDetail(borrowingDetailAmount, newService, book,expireDate);  
                    borrowingDetailAmount++;
                    detailList.add(newDetail);  
                    bookIndex++;


                }
            } 
            bookTitle.setAmountRemaining(bookTitle.getAmountRemaining()-i.getAmount()); 
            bookTitleRepo.save(bookTitle); 

           


        } 
        serviceRepo.save(newService);  
        for(BorrowingCardDetail i: detailList) 
        {
            borrowingDetailRepo.save(i); 
            
        } 
        
        Date sendDate= request.getServiceRequest().getImplementDate();
        int readerId = request.getServiceRequest().getReaderId(); 
        sendNotification(readerId, sendDate, "You borrowed our book, please go to the library and take your book", "Borrow Book");
        return new ResponseEntity<>(new ResponseData("Borrowing books success, please go to library and take your book", detailList), HttpStatus.OK);
   }   

   public void sendNotification(int readerId, Date sendDate, String message, String subject)  
   {    
        

        User reader = userRepo.findById(readerId).orElse(null); 
        if(reader==null) return; 
        int newId = notifRepo.findAll().size();
        
        Notification notif = new Notification( newId,reader,sendDate, false, subject, message);
        notifRepo.save(notif);

   } 

   public ResponseEntity<List<Notification>> getAllNotification(int userId) 
   { 
        User user = userRepo.findById(userId).orElse(null); 
        if(user==null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); 
        List<Notification> listNotification = notifRepo.findByReader(user); 
        return new ResponseEntity<>(listNotification, HttpStatus.OK);
   } 

   public ResponseEntity<List<Notification>> getUnreadNotification(int userId) 
   { 
        User user = userRepo.findById(userId).orElse(null); 
        if(user==null) return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST); 
        List<Notification> listNotification = notifRepo.findByReader(user); 
        List<Notification> unreadNotifications = listNotification.stream().filter(notif->!notif.getIsRead()).collect(Collectors.toList()); 
        return new ResponseEntity<>(unreadNotifications, HttpStatus.OK);
   } 

   @SuppressWarnings("null")
    public ResponseEntity<Notification> readANotification(int userId, int notifId) 
   { 
        User user = userRepo.findById(userId).orElse(null); 
        if(user==null) return new ResponseEntity<>(new Notification(-1,null, null, false, "User does not exits",""), HttpStatus.BAD_REQUEST);
        
        Notification notification = notifRepo.findById(notifId).orElse(null); 
        if(notification==null) return new ResponseEntity<>(new Notification(-1,null, null, false, "Notification does not exits",""), HttpStatus.BAD_REQUEST); 

        if(notification.getReader().getUserId()!=userId) 
        return new ResponseEntity<>(new Notification(-1,null, null, false, "This notification is not your",""), HttpStatus.BAD_REQUEST);
        notification.setIsread(true);  
        notifRepo.save(notification);
        return new ResponseEntity<>(notification, HttpStatus.OK);
   } 

   @SuppressWarnings("null")
    public ResponseEntity<List<CartDetailResponse>> getCart(int userId) 
   { 
    User user= userRepo.findById(userId).orElse(null); 
    if(user==null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    List<CartDetail> cart= cartDetailRepo.findByUser(user); 
    List<CartDetailResponse> listRes= new ArrayList<>();
    for(int i=0;i<cart.size();i++) 
    {
        listRes.add(new CartDetailResponse(cart.get(i)));
    }
    return new ResponseEntity<>(listRes, HttpStatus.OK);
   } 

   public ResponseEntity<String> addToCart(AddCartDetailRequest request, int userId) 
   { 
        User user = userRepo.findById(userId).orElse(null);
        if(user==null||user.getEnable()==false) 
        {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } 
        BookTitleImagePath bookTitleImagePath = bookTitleRepo.findById(request.getBookTitleId()).orElse(null);
        if(bookTitleImagePath==null) 
        {
            return new ResponseEntity<>("Book not found", HttpStatus.BAD_REQUEST);
        } 
        List<CartDetail> listCartDetail= cartDetailRepo.findByUser(user);
        for(int i=0;i<listCartDetail.size();i++) 
        {
            if(listCartDetail.get(i).getBookTitleImagePath().getId()==request.getBookTitleId())
            {
                return new ResponseEntity<>("Added to cart", HttpStatus.OK);
            }
        }
        CartDetail newCartDetail = new CartDetail(user, bookTitleImagePath, 1);
        cartDetailRepo.save(newCartDetail);
        return new ResponseEntity<>("Added to cart", HttpStatus.OK);
       
        

   } 

   public ResponseEntity<String> removeFromCart(int cartDetailId, int userId) 
   { 
    
        CartDetail cartDetail = cartDetailRepo.findById(cartDetailId).orElse(null); 
        if(cartDetail==null) return new ResponseEntity<>("This cart detail does not exists", HttpStatus.BAD_REQUEST);   
        if(cartDetail.getUser().getUserId()!=userId) 
        {
            return new ResponseEntity<>("User id does not match", HttpStatus.BAD_REQUEST);
        }
        cartDetailRepo.delete(cartDetail); 
        return new ResponseEntity<>("Deleted item", HttpStatus.OK);
   } 

   public ResponseEntity<String> clearCart(int userId) 
   { 
        User user = userRepo.findById(userId).orElse(null); 
        if(user==null) return new ResponseEntity<>("User does not exists", HttpStatus.BAD_REQUEST);  
        List<CartDetail> listCartByUser = cartDetailRepo.findByUser(user); 
        for(CartDetail i : listCartByUser) 
        {
            cartDetailRepo.delete(i);
        } 
        return new ResponseEntity<>("Deleted", HttpStatus.OK);

   } 

    


   public ResponseEntity<List<CartDetailResponse>> updateReaderCart(List<CartDetailUpdateRequest> listRequest, int userId) 
   {
        List<CartDetailResponse> listRes= new ArrayList<>();
        for(int i=0;i<listRequest.size();i++) 
        {
            CartDetail cartDetail = cartDetailRepo.findById(listRequest.get(i).getCartDetailId()).orElse(null);
            if(cartDetail==null) 
            {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }  
            if(cartDetail.getUser().getUserId()!=userId)
            {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } 
            cartDetail.setAmount(listRequest.get(i).getAmount()); 
            cartDetailRepo.save(cartDetail);
            listRes.add(new CartDetailResponse(cartDetail));
        } 
        return new ResponseEntity<>(listRes, HttpStatus.OK);
   }
    


   public ResponseEntity<String> sendRenewalRequest(RenewalRequest request, int userId) 
   {
        int newServiceId = serviceRepo.findAll().size();  
        List<RenewalCardDetail> detailList = new ArrayList<>(); 
        if(userId!=request.getServiceRequest().getReaderId()) 
        {
            return new ResponseEntity<>("Wrong information", HttpStatus.BAD_REQUEST);
        }
        User reader = userRepo.findById(request.getServiceRequest().getReaderId()).orElse(null); 
        if(reader==null) 
        { 
            return new ResponseEntity<>("Reader does not exists", HttpStatus.BAD_REQUEST);
        } 
        ServiceType serviceType = serviceTypeRepo.findById(request.getServiceRequest().getServiceTypeId()).orElse(null);
        if(serviceType==null) 
        { 
            return new ResponseEntity<>("We do not have this service", HttpStatus.BAD_REQUEST);
        } 
        com.library.librarymanagement.entity.Service newService = new com.library.librarymanagement.entity.Service(newServiceId,
        reader, new Date(), serviceType);  
        for(RenewalCardDetailRequest detailRequest: request.getListRenewalDetails()) 
        {  
            BorrowingCardDetail borrowingDetail = borrowingDetailRepo.findById(detailRequest.getBorrowingCardDetailId()).orElse(null); 
            if(borrowingDetail==null) return new ResponseEntity<>("Renewal Failed", HttpStatus.BAD_REQUEST); 
            if(borrowingDetail.getService().getReader().getUserId()!=request.getServiceRequest().getReaderId()) 
            return new ResponseEntity<>("Renewal send failed", HttpStatus.BAD_REQUEST);  
            User defaultLibrarian= userRepo.findById(-1).orElse(null);
            RenewalCardDetail newDetail = new RenewalCardDetail(Integer.MAX_VALUE,newService,defaultLibrarian,borrowingDetail ); 
            detailList.add(newDetail);
        }  
        serviceRepo.save(newService);   
        for(RenewalCardDetail detail: detailList) 
        {  
            renewalDetailRepo.save(detail);
        } 
        return new ResponseEntity<>("Send renewal success, please wait for librarian to response", HttpStatus.OK);
   } 


   public ResponseEntity<String> sendUnlockRequest(String authHeader) 
   { 
        int userId= tokenSecurity.extractUserId(authHeader);  
        User reader= userRepo.findById(userId).orElse(null); 
        if(reader.getEnable()==true) 
        { 
            return new ResponseEntity<>("User account is not locked", HttpStatus.BAD_REQUEST); 

        } 
        User defaultLibrarian = userRepo.findById(-1).orElse(null); 
        int maxValue= Integer.MAX_VALUE;
        UnlockRequest unlockRequest = new UnlockRequest(maxValue, reader, false, defaultLibrarian, null); 
        unlockRequestRepo.save(unlockRequest); 
        return new ResponseEntity<>("You sent unlock request, please wait for librarian to response", HttpStatus.OK);
   }


   


   



   
    



    
    
}
