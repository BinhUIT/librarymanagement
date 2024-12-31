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
import com.library.librarymanagement.entity.Penalty;
import com.library.librarymanagement.entity.RenewalCardDetail;
import com.library.librarymanagement.entity.RenewalDetail;
import com.library.librarymanagement.entity.ServiceType;
import com.library.librarymanagement.entity.UnlockRequest;
import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.entity.BorrowingCardDetail.Status;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.repository.BookStatusRepository;
import com.library.librarymanagement.repository.BookTitleRepository;
import com.library.librarymanagement.repository.BorrowingCardDetailRepository;
import com.library.librarymanagement.repository.CartDetailRepository;
import com.library.librarymanagement.repository.NotificationRepository;
import com.library.librarymanagement.repository.PenaltyRepository;
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
import com.library.librarymanagement.response.BorrowResponse;
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
   
   @Autowired 
   private PenaltyRepository penaltyRepo;
   

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


   public ResponseEntity<BorrowResponse> borrowViaCart(BorrowingRequest request, int userId) 
   { 
        BorrowResponse borrowResponse= new BorrowResponse("", new ArrayList<>()); 
        User user = userRepo.findById(userId).orElse(null);
        for(int i=0;i<request.getListRequest().size();i++) 
        {
            CartDetail cartDetail= cartDetailRepo.findById(request.getListRequest().get(i).getCartDetailId()).orElse(null);
            if(cartDetail==null) 
            {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } 
            if(cartDetail.getUser().getUserId()!=userId) 
            {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } 
            BookTitleImagePath bookTitle= bookTitleRepo.findById(cartDetail.getBookTitleImagePath().getId()).orElse(null);
            if(bookTitle==null||bookTitle.getEnable()==false) 
            {
                return new ResponseEntity<>(new BorrowResponse("Not exists",null), HttpStatus.OK);
            } 
            if(bookTitle.getAmountRemaining()<request.getListRequest().get(i).getAmount()) 
            {
                borrowResponse.setMessage("Not enough"); 
                borrowResponse.addResponse(bookTitle.getName());
            }
        }
        if(!borrowResponse.getMessage().equals("")) 
        {
            return new ResponseEntity<>(borrowResponse, HttpStatus.OK);
        }
        ServiceType serviceType= serviceTypeRepo.findById(1).orElse(null); 
        int newServiceId= serviceRepo.findAll().size()+1; 
       com.library.librarymanagement.entity.Service service = new com.library.librarymanagement.entity.Service(newServiceId, user, new Date(), serviceType); 
       serviceRepo.save(service);
       for(int i=0;i<request.getListRequest().size();i++) 
       { 
            CartDetail cartDetail= cartDetailRepo.findById(request.getListRequest().get(i).getCartDetailId()).orElse(null); 
            BookTitleImagePath bookTitle= bookTitleRepo.findById(cartDetail.getBookTitleImagePath().getId()).orElse(null);
            List<BookImagePath> listBook= bookRepo.findByTitle(bookTitle);
            int borrowAmount = request.getListRequest().get(i).getAmount();
            System.out.println(borrowAmount);
            int beginIndex=0;
            int newBorrowCardDetailId = borrowingDetailRepo.findAll().size();
            while(borrowAmount>0) 
            {
                if(listBook.get(beginIndex).getStatus().getId()==0) 
                { 
                    Date currentDate = new Date();  
                     Calendar calendar = Calendar.getInstance(); 
                     calendar.setTime(currentDate); 
                     calendar.add(Calendar.DAY_OF_MONTH, 7);  
                     Date expireDate = calendar.getTime();
                    BorrowingCardDetail borrowingCardDetail = new BorrowingCardDetail(newBorrowCardDetailId, service, listBook.get(beginIndex), expireDate);
                    BookStatus newStatus= bookStatusRepo.findById((byte)1).orElse(null);
                    listBook.get(beginIndex).setStatus(newStatus); 
                    bookRepo.save(listBook.get(beginIndex));
                    bookTitle.setAmountRemaining(bookTitle.getAmountRemaining()-1); 
                    bookTitleRepo.save(bookTitle);
                    borrowingDetailRepo.save(borrowingCardDetail);
                    borrowResponse.setMessage("Success");
                    borrowResponse.addResponse(borrowingCardDetail);
                    newBorrowCardDetailId++;
                    borrowAmount--;

                } 
                beginIndex++;
            }
            cartDetailRepo.delete(cartDetail);
       } 
       return new ResponseEntity<>(borrowResponse, HttpStatus.OK);

   }  

   public ResponseEntity<BorrowResponse> borrowOneBook(int bookTitleId, int userId) 
   {
    BorrowResponse borrowResponse= new BorrowResponse("", new ArrayList<>()); 
    BookTitleImagePath bookTitleImagePath = bookTitleRepo.findById(bookTitleId).orElse(null);
    if(bookTitleImagePath==null||bookTitleImagePath.getEnable()==false) 
    {
        return new ResponseEntity<>(borrowResponse, HttpStatus.BAD_REQUEST);
    } 
    if(bookTitleImagePath.getAmountRemaining()==0) 
    {
        borrowResponse.setMessage("Not enough"); 
        borrowResponse.addResponse(bookTitleImagePath.getName()); 
        return new ResponseEntity<>(borrowResponse, HttpStatus.OK);
    } 
    List<BookImagePath> listBook = bookRepo.findByTitle(bookTitleImagePath);
    for(int i=0;i<listBook.size();i++) 
    {
        if(listBook.get(i).getIsUsable()&&listBook.get(i).getStatus().getId()==0) 
        {
            ServiceType serviceType= serviceTypeRepo.findById(1).orElse(null);  
            User user= userRepo.findById(userId).orElse(null);
            BookTitleImagePath bookTitle = listBook.get(i).getTitle();
            bookTitle.setAmountRemaining(bookTitle.getAmountRemaining()-1); 
            bookTitleRepo.save(bookTitle);
        int newServiceId= serviceRepo.findAll().size()+1; 
       com.library.librarymanagement.entity.Service service = new com.library.librarymanagement.entity.Service(newServiceId, user, new Date(), serviceType);
       serviceRepo.save(service); 
       int newBorrowCardDetailId = borrowingDetailRepo.findAll().size();
       Date currentDate = new Date();  
        Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(currentDate); 
        calendar.add(Calendar.DAY_OF_MONTH, 7);  
        Date expireDate = calendar.getTime();
       BorrowingCardDetail borrowingCardDetail = new BorrowingCardDetail(newBorrowCardDetailId, service,listBook.get(i),expireDate); 
       borrowingDetailRepo.save(borrowingCardDetail); 
       borrowResponse.setMessage("Success"); 
       borrowResponse.addResponse(borrowingCardDetail);
       break;
        }
    }
    return new ResponseEntity<>(borrowResponse, HttpStatus.OK);

   }

  


   public ResponseEntity<List<BorrowingCardDetail>> getAllBorrowingCardDetail(int userId) 
   {
        User reader= userRepo.findById(userId).orElse(null); 
        if(reader==null) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }  
        List<BorrowingCardDetail> listRes= new ArrayList<>();
        List<com.library.librarymanagement.entity.Service> listService= serviceRepo.findByReader(reader); 
        for(int i=0;i<listService.size();i++) 
        {
            List<BorrowingCardDetail> listDetail = borrowingDetailRepo.findByService_ServiceId(listService.get(i).getServiceId()); 
            for(int j=0;j<listDetail.size();j++)
            {
                listRes.add(listDetail.get(j));
            }
        }
        return new ResponseEntity<>(listRes, HttpStatus.OK);
   } 


   public ResponseEntity<String> onDestroyBorrowingDetail(int detailId, int userId) 
   {
        BorrowingCardDetail borrowingCardDetail = borrowingDetailRepo.findById(detailId).orElse(null);
        if(borrowingCardDetail==null) 
        {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        } 
        if(borrowingCardDetail.getService().getReader().getUserId()!=userId) 
        {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        } 
        borrowingCardDetail.updateStatus(Status.CANCELLED);
        borrowingDetailRepo.save(borrowingCardDetail);
        return new ResponseEntity<>("Success", HttpStatus.OK);
   }

   public ResponseEntity<String> sendRenewalRequest(int borrowingCardDetailId, RenewalRequest request) 
   {
        BorrowingCardDetail borrowingCardDetail = borrowingDetailRepo.findById(borrowingCardDetailId).orElse(null);
        if(borrowingCardDetail==null) 
        {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        } 
        if(borrowingCardDetail.getStatus()!=Status.BORROWING) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); 
        }
        borrowingCardDetail.updateStatus(Status.RENEWAL);  
        int newRenewalDetailId = renewalDetailRepo.findAll().size(); 
        RenewalDetail renewalDetail;
        if(newRenewalDetailId!=0){
        renewalDetail = new RenewalDetail(borrowingCardDetail, request.getNewExpireDate());
        } 
        else {
            renewalDetail= new RenewalDetail(0, borrowingCardDetail, request.getNewExpireDate(), new Date());
        }
        renewalDetailRepo.save(renewalDetail);
        borrowingDetailRepo.save(borrowingCardDetail); 
        System.out.println("Renewal success"); 
        return new ResponseEntity<>("Success ", HttpStatus.OK);
   }

   public ResponseEntity<List<Penalty>> getAllPenalty(int userId) 
   {
        User reader= userRepo.findById(userId).orElse(null);
        if(reader==null||reader.getEnable()==false) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } 
        return new ResponseEntity<>(penaltyRepo.findByReader(reader), HttpStatus.OK);
   }

   public ResponseEntity<String> cancelBorrowingDetail(int borrowingDetailId) 
   {
        BorrowingCardDetail borrowDetail=borrowingDetailRepo.findById(borrowingDetailId).orElse(null);
        if(borrowDetail==null||borrowDetail.getStatus()!=Status.PENDING) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } 
        borrowDetail.updateStatus(Status.CANCELLED);
        borrowDetail.getBook().setStatus(bookStatusRepo.findById((byte)0).orElse(null));
        borrowDetail.getBook().getTitle().setAmountRemaining(borrowDetail.getBook().getTitle().getAmountRemaining()+1);
        borrowingDetailRepo.save(borrowDetail);
        bookRepo.save(borrowDetail.getBook()); 
        bookTitleRepo.save(borrowDetail.getBook().getTitle());
        return new ResponseEntity<>("Success", HttpStatus.OK);
   }

   

   


   



   
    



    
    
}
