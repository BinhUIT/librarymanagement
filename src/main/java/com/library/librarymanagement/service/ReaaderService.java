package com.library.librarymanagement.service;

import java.util.ArrayList;
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
import com.library.librarymanagement.entity.Notification;
import com.library.librarymanagement.entity.ServiceType;
import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.repository.BookStatusRepository;
import com.library.librarymanagement.repository.BookTitleRepository;
import com.library.librarymanagement.repository.BorrowingCardDetailRepository;
import com.library.librarymanagement.repository.NotificationRepository;
import com.library.librarymanagement.repository.ServiceRepository;
import com.library.librarymanagement.repository.ServiceTypeRepository;
import com.library.librarymanagement.repository.UserRepository;
import com.library.librarymanagement.request.BorrowingDetailRequest;
import com.library.librarymanagement.request.BorrowingRequest;


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
   public ResponseEntity<String> borrowingBook(BorrowingRequest request) 
   {   
        List<BorrowingDetailRequest> listBookTitle = request.getListBook();  
        int borrowingDetailAmount = borrowingDetailRepo.findAll().size();  
        int newServiceId = serviceRepo.findAll().size();  
        List<BorrowingCardDetail> detailList = new ArrayList<>();
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
        
        for(BorrowingDetailRequest i : listBookTitle) 
        {   
            BookTitleImagePath bookTitle = bookTitleRepo.findById(i.getBookTitleId()).orElse(null);
            if(bookTitle==null) 
            { 
                return new ResponseEntity<>("Book does not exists", HttpStatus.BAD_REQUEST);
            } 
            if(bookTitle.getAmountRemaining()<i.getAmount()) 
            { 
               return new ResponseEntity<>("Not enough book", HttpStatus.BAD_REQUEST);
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
                    BorrowingCardDetail newDetail = new BorrowingCardDetail(borrowingDetailAmount, newService, book);  
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
        return new ResponseEntity<>("Borrowing books success, please go to library and take your book", HttpStatus.OK);
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

    



    
    
}
