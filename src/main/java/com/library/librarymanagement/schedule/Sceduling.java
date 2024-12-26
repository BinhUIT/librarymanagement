package com.library.librarymanagement.schedule;

import java.io.UnsupportedEncodingException;
import java.security.Provider.Service;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.library.librarymanagement.entity.BookImagePath;
import com.library.librarymanagement.entity.BookTitleImagePath;
import com.library.librarymanagement.entity.BorrowingCardDetail;
import com.library.librarymanagement.entity.Regulation;
import com.library.librarymanagement.entity.RenewalDetail;
import com.library.librarymanagement.entity.BorrowingCardDetail.Status;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.repository.BookStatusRepository;
import com.library.librarymanagement.repository.BookTitleRepository;
import com.library.librarymanagement.repository.BorrowingCardDetailRepository;
import com.library.librarymanagement.repository.RenewalDetailRepository;
import com.library.librarymanagement.repository.ReulationRepository;
import com.library.librarymanagement.repository.ServiceRepository;
import com.library.librarymanagement.response.BorrowResponse;
import com.library.librarymanagement.service.UserService;

import jakarta.mail.MessagingException;

@Component

public class Sceduling {
    @Autowired 
    private BookRepository bookRepo;

    @Autowired 
    private BookTitleRepository bookTitleRepo;
    @Autowired 
    private RenewalDetailRepository renewalDetailRepo;
    @Autowired
    private UserService userService;

    @Autowired 
    private ServiceRepository serviceRepo;

    @Autowired 
    private BorrowingCardDetailRepository borrowingDetailRepo;
    @Autowired 
    private ReulationRepository regulationRepo; 

    @Autowired
    private BookStatusRepository statusRepo;
    private void checkDaysToTakeBook() throws UnsupportedEncodingException, MessagingException 
    { 
        Regulation regulation= regulationRepo.findById(1).orElse(null);
        List<com.library.librarymanagement.entity.Service> listService = serviceRepo.findAll(); 
        for(int i=0;i<listService.size();i++) 
        {
            boolean isSendMail=false;
            List<BorrowingCardDetail> listBorrowDetail = borrowingDetailRepo.findByService_ServiceId(listService.get(i).getServiceId());
            for(int j=0;j<listBorrowDetail.size();j++) 
            {
                if(listBorrowDetail.get(j).getStatus()==Status.PENDING) 
                {
                    Date currentDate = new Date();
                    Date implementDate= listService.get(i).getImplementDate();
                    LocalDate endDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); 
                    LocalDate startDate = implementDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int daysBetween =(int) ChronoUnit.DAYS.between(startDate, endDate); 
                    if(daysBetween>regulation.getDaysToTakeBook()) 
                    {
                        listBorrowDetail.get(j).updateStatus(Status.CANCELLED);
                        borrowingDetailRepo.save(listBorrowDetail.get(j));
                        BookImagePath book = listBorrowDetail.get(j).getBook();
                        book.setStatus(statusRepo.findById((byte)0).orElse(null)); 
                        bookRepo.save(book);
                        BookTitleImagePath bookTitle= book.getTitle();
                        bookTitle.setAmountRemaining(bookTitle.getAmountRemaining()+1); 
                        bookTitleRepo.save(bookTitle); 
                        isSendMail=true;
                    } 
                    else {
                        if(daysBetween==regulation.getDaysToTakeBook()&&!listService.get(i).getRemindTake()) 
                        {
                            String content="Nhắc nhở lấy sách";
                            String subjext="Bạn còn 1 ngày để lấy sách, nếu không lấy thì các sách bạn đã đăng ký mượn sẽ bị hủy và trả về cho thư viện";
                            userService.sendEmail(listService.get(i).getReader(), content, subjext);  
                            listService.get(i).setRemindTake(true); 
                            serviceRepo.save( listService.get(i));
                            break;
                        }
                    }

                } 
                
            }
            if(isSendMail) 
            {
                String content="Đã hủy phiếu mượn";
                String subjext="Phiếu mượn của bạn đã bị hủy do bạn không đến lấy sách đúng hạn";
                
                userService.sendEmail(listService.get(i).getReader(), content, subjext); 
            }
        }

    }

    private void checkDayToResponseRenewal() throws UnsupportedEncodingException, MessagingException 
    {
        Regulation regulation= regulationRepo.findById(1).orElse(null);
        List<RenewalDetail> listRenewal = renewalDetailRepo.findAll();
        for(int i=0;i<listRenewal.size();i++) 
        {
            Date currentDate= new Date();
            Date sendDate = listRenewal.get(i).getSendDate();
            LocalDate startDate= sendDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); 
            LocalDate endDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int daysBetween = (int)ChronoUnit.DAYS.between(startDate, endDate);
            if(daysBetween>regulation.getDaysToResponseRenewal()) 
            {
                renewalDetailRepo.delete(listRenewal.get(i));
                String content="Gia hạn thất bại";
                String subjext="Bạn đã gia hạn thất bại, vui lòng trả sách đúng hạn đã đăng ký";
                userService.sendEmail(listRenewal.get(i).getBorrowingCardDetail().getService().getReader(), content, subjext); 
            }
        }
    } 
    private void checkDayToReturnBook() throws UnsupportedEncodingException, MessagingException 
    {
        Regulation regulation= regulationRepo.findById(1).orElse(null);
        List<com.library.librarymanagement.entity.Service> listService = serviceRepo.findAll(); 
        for(int i=0;i<listService.size();i++) 
        {
            boolean isSendMail=false;
            List<BorrowingCardDetail> listBorrowDetail = borrowingDetailRepo.findByService_ServiceId(listService.get(i).getServiceId());
            for(int j=0;j<listBorrowDetail.size();j++) 
            {
                if(listBorrowDetail.get(j).getStatus()==Status.BORROWING||listBorrowDetail.get(j).getStatus()==Status.RENEWAL) 
                {
                    
                    Date currentDate= new Date();
                    
                    LocalDate startDate= listBorrowDetail.get(j).getExpireDate().toLocalDate();
                    LocalDate endDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int daysBetween = (int)ChronoUnit.DAYS.between(startDate, endDate); 
                    if(daysBetween<2&&!listService.get(i).getRemindReturn()) 
                    {
                        String content="Nhắc nhở trả sách";
                        String subjext="Bạn sắp đến hạn trả sách, vui lòng mang các sách đã mượn đến thư viện trả đúng hạn hoặc gia hạn thêm"; 
                        listService.get(i).setRemindReturn(true);
                        serviceRepo.save(listService.get(i));
                        userService.sendEmail(listService.get(i).getReader(), content, subjext);  
                        break;

                    } 
                    
                } 
                
            }
            
        }
    }
    @Scheduled(fixedRate = 1000000)  
    public void scheduleDatabase() throws UnsupportedEncodingException, MessagingException 
    {
        checkDaysToTakeBook(); 
        checkDayToResponseRenewal(); 
        checkDayToReturnBook();
        System.out.println("Auto");
        
    }
}
