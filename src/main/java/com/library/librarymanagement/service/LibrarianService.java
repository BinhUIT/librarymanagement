package com.library.librarymanagement.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.library.librarymanagement.entity.BookImagePath;
import com.library.librarymanagement.entity.BookStatus;
import com.library.librarymanagement.entity.BookTitleImageData;
import com.library.librarymanagement.entity.BookTitleImagePath;
import com.library.librarymanagement.entity.BookTypeImagePath;
import com.library.librarymanagement.entity.BorrowingCardDetail;
import com.library.librarymanagement.entity.BuyBookBill;
import com.library.librarymanagement.entity.BuyBookBillDetail;
import com.library.librarymanagement.entity.Notification;
import com.library.librarymanagement.entity.RenewalCardDetail;
import com.library.librarymanagement.entity.ReturningCardDetail;
import com.library.librarymanagement.entity.SellBookBill;
import com.library.librarymanagement.entity.SellBookBillDetail;
import com.library.librarymanagement.entity.ServiceType;
import com.library.librarymanagement.entity.UnlockRequest;
import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.entity.WorkDetail;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.repository.BookStatusRepository;
import com.library.librarymanagement.repository.BookTitleRepository;
import com.library.librarymanagement.repository.BookTypeRepository;
import com.library.librarymanagement.repository.BorrowingCardDetailRepository;
import com.library.librarymanagement.repository.BuyBookBillDetailRepository;
import com.library.librarymanagement.repository.BuyBookBillRepository;
import com.library.librarymanagement.repository.NotificationRepository;
import com.library.librarymanagement.repository.RenewalDetailRepository;
import com.library.librarymanagement.repository.ReturningCardDetailRpository;
import com.library.librarymanagement.repository.SellBookBillDetailRepository;
import com.library.librarymanagement.repository.SellBookBillRepository;
import com.library.librarymanagement.repository.ServiceRepository;
import com.library.librarymanagement.repository.ServiceTypeRepository;
import com.library.librarymanagement.repository.UnlockRequestRepository;
import com.library.librarymanagement.repository.UserRepository;
import com.library.librarymanagement.repository.WorkDetailRepository;
import com.library.librarymanagement.request.BookTitleCreateRequest;
import com.library.librarymanagement.request.BookTitleUpdateRequest;
import com.library.librarymanagement.request.BookTypeCreateRequest;
import com.library.librarymanagement.request.BookTypeUpdateRequest;
import com.library.librarymanagement.request.BuyBookBillDetailRequest;
import com.library.librarymanagement.request.BuyBookBillRequest;
import com.library.librarymanagement.request.ReturningDetailRequest;
import com.library.librarymanagement.request.ReturningRequest;
import com.library.librarymanagement.request.SellBookBillCreateRequest;
import com.library.librarymanagement.request.SellBookBillDetailRequest;
import com.library.librarymanagement.request.UnlockResponse;
import com.library.librarymanagement.resource.ResourceStrings;
import com.library.librarymanagement.response.BookBorrowingDetailResponse;
import com.library.librarymanagement.ulti.File;



@Service
public class LibrarianService { 
    @Autowired 
    private UserRepository userRepo; 
    @Autowired 
    private BookRepository bookRepo; 
    @Autowired 
    private BookTitleRepository bookTitleRepo;  
    @Autowired 
    private BuyBookBillDetailRepository buyBookBillDetailRepo; 
    @Autowired 
    private BuyBookBillRepository buyBookBillRepo;  
    @Autowired 
    private BookTypeRepository bookTypeRepo;

    @Autowired 
    private ResourceStrings resourceStrings;

    @Autowired 
    private BookStatusRepository bookStatusRepo; 

    @Autowired 
    private SellBookBillRepository sellBookBillRepo; 
    @Autowired 
    private SellBookBillDetailRepository sellBookBillDetailRepo; 

    @Autowired 
    private RenewalDetailRepository renewalDetailRepo; 

    @Autowired 
    private NotificationRepository notifRepo; 

    @Autowired 
    private ServiceTypeRepository serviceTypeRepo; 
    @Autowired 
    private ServiceRepository serviceRepo; 

    @Autowired 
    private BorrowingCardDetailRepository borrowingCardDetailRepo; 

    @Autowired 
    private ReturningCardDetailRpository returningCardDetailRepo;
    
    @Autowired
    private WorkDetailRepository workDetailRepo;

    @Autowired 
    private UnlockRequestRepository unlockRequestRepo;
    public ResponseEntity<String> buyBook(BuyBookBillRequest request, int userId) 
    {   
        if(userId!=request.getLibrarianId()) 
            return new ResponseEntity<>("Wrong information", HttpStatus.BAD_REQUEST);
        User librarian = userRepo.findById(request.getLibrarianId()).orElse(null);  
        List<BuyBookBillDetail> listBuyBookBillDetail = new ArrayList<>(); 
        List<Integer> bookTitleDoesNotExist= new ArrayList<>();
        if(librarian==null) return new ResponseEntity<>("User does not exitst", HttpStatus.BAD_REQUEST); 
        for(BuyBookBillDetailRequest i: request.getListDetailRequest()) 
        {  
            BookTitleImagePath bookTitle = bookTitleRepo.findById(i.getBookTitleId()).orElse(null);  
            if(bookTitle==null)
            { 
                bookTitleDoesNotExist.add(i.getBookTitleId()); 
                continue; 
            } 
            BuyBookBillDetail buyBookBillDetail = new BuyBookBillDetail(0, null, bookTitle, i.getAmount(), i.getPrice()); 
            listBuyBookBillDetail.add(buyBookBillDetail);

            
        }
        if(!bookTitleDoesNotExist.isEmpty()) 
        { 
            String resultString = "Book-titles with id: "; 
            for(int i=0; i<bookTitleDoesNotExist.size();i++) 
            {
                if(i==0) 
                {
                    resultString+=Integer.toString(bookTitleDoesNotExist.get(i));
                } 
                else {
                    resultString+=", "+Integer.toString(bookTitleDoesNotExist.get(i));
                }
            } 
            resultString+= " does not exits"; 
            return new ResponseEntity<>(resultString, HttpStatus.BAD_REQUEST);
        }
        BuyBookBill buyBookBill = new BuyBookBill(0, librarian, request.getImplementDate());
        buyBookBillRepo.save(buyBookBill);
        for(BuyBookBillDetail i: listBuyBookBillDetail) 
        { 
            BookTitleImagePath bookTitleImagePath = bookTitleRepo.findById(i.getBookTitle().getId()).orElse(null); 
            
            i.setBuyBookBill(buyBookBill); 
            buyBookBillDetailRepo.save(i);  
            int newStatus=0;
            BookStatus bookStatus = bookStatusRepo.findById((byte)newStatus).orElse(null); 
            for(int j =0;j<i.getAmount();j++) 
            { 
                int newId = bookRepo.findAll().size();
                BookImagePath newBook = new BookImagePath(newId,i.getBookTitle(), bookStatus, true); 
                bookRepo.save(newBook); 
            }  
            int newAmount= bookTitleImagePath.getAmount()+i.getAmount(); 
            int newAmountRemaining = bookTitleImagePath.getAmountRemaining()+i.getAmount();
            bookTitleImagePath.setAmount(newAmount); 
            bookTitleImagePath.setAmountRemaining(newAmountRemaining);
            bookTitleRepo.save(bookTitleImagePath); 
            

        } 
        return new ResponseEntity<>("Buy book success", HttpStatus.OK);
    }  

    

    public boolean createNewBookTitleImage(MultipartFile imageFile) 
    {  
        int bookTitleId = bookTitleRepo.findAll().size();
        if(imageFile.isEmpty()) return false;  
        try{
        byte[] imageBytes= imageFile.getBytes();
        String path = resourceStrings.DIR_BOOK_TITLE_IMAGE +"/BookTitle"+Integer.toString(bookTitleId)+".png"; 
        
        File newFile = new File(path); 
        newFile.createAndWrite(imageBytes) ; 
        
        

        } 
        catch(IOException e) 
        { 
            return false;
        }

        return true;
    }

    public ResponseEntity<String> createNewBookTitle(BookTitleCreateRequest request) 
    { 
        int newBookTitleId= bookTitleRepo.findAll().size();
        String imagePath = resourceStrings.DIR_BOOK_TITLE_IMAGE +"/BookTitle"+Integer.toString(newBookTitleId)+".png";  
        File checkFile = new File(imagePath); 
        if(!checkFile.isExisted()) 
        { 
            return new ResponseEntity<>("You need to upload image", HttpStatus.BAD_REQUEST);
        } 
        BookTypeImagePath bookType = bookTypeRepo.findById((short)request.getBookTypeId()).orElse(null); 
        if(bookType==null) 
        {  
            checkFile.delete();
            return new ResponseEntity<>("Book type does not exits", HttpStatus.BAD_REQUEST);
        }  
        if(bookTitleRepo.existsByName(request.getName())) return new ResponseEntity<>("Book with this title is already exists", HttpStatus.OK);
        BookTitleImagePath  newBookTitle = new BookTitleImagePath(newBookTitleId,request.getName(), bookType,request.getAuthor(), imagePath, request.getNxb(), request.getYear(), request.getLanguage(), request.getPageAmount(), request.getReview()); 
        bookTitleRepo.save(newBookTitle);
        return new ResponseEntity<>("Created new book title", HttpStatus.OK);
    }
    public boolean createNewBookTypeImage(MultipartFile imageFile) 
    { 
        int bookTypeId= bookTypeRepo.findAll().size()+1; 
        if(imageFile.isEmpty()) return false; 
        try{
            byte[] imageBytes= imageFile.getBytes();
            String path = resourceStrings.DIR_BOOK_TYPE_IMAGE +"/BookType"+Integer.toString(bookTypeId)+".png"; 
            
            File newFile = new File(path); 
            newFile.createAndWrite(imageBytes) ; 
            
            
    
            } 
            catch(IOException e) 
            { 
                return false;
            }
    
            return true;
    } 

    public ResponseEntity<String> createNewBookType(BookTypeCreateRequest request) 
    {
        int newBookTypeId= bookTypeRepo.findAll().size()+1;
        String imagePath = resourceStrings.DIR_BOOK_TYPE_IMAGE +"/BookType"+Integer.toString(newBookTypeId)+".png";  
        File checkFile = new File(imagePath); 
        if(!checkFile.isExisted()) 
        { 
            return new ResponseEntity<>("You need to upload image", HttpStatus.BAD_REQUEST);
        } 
        if(bookTypeRepo.existsByName(request.getName())) 
        {  
            checkFile.delete();
            return new ResponseEntity<>("Book type with this name is already exists", HttpStatus.BAD_REQUEST); 

        }
        BookTypeImagePath  newBookType  = new BookTypeImagePath((short)newBookTypeId, request.getName(), imagePath); 
        bookTypeRepo.save(newBookType);
        return new ResponseEntity<>("Book Type created", HttpStatus.OK);
    } 

    public ResponseEntity<String> SellBook(SellBookBillCreateRequest request, int librarianId) 
    { 
        User librarian = userRepo.findById(librarianId).orElse(null); 
        if(librarian==null) return new ResponseEntity<>("User does not exist", HttpStatus.OK);  

        int newSellBookBillId= sellBookBillRepo.findAll().size(); 
        List<Integer> listFail = new ArrayList<>(); 
        List<SellBookBillDetail> listDetail = new ArrayList<>();
        SellBookBill newSellBookBill = new SellBookBill(newSellBookBillId,request.getDate(), librarian);  
        
        for(SellBookBillDetailRequest detailRequest: request.getListDetailRequest()) 
        { 
            BookImagePath book = bookRepo.findById(detailRequest.getBookId()).orElse(null); 
            if(book == null) { 
                listFail.add(detailRequest.getBookId());
                continue;
            }   
            int readeyStatus=0;
            BookStatus readyStatus = bookStatusRepo.findById((byte)readeyStatus).orElse(null);
            if(book.getStatus().getId()!=readyStatus.getId()) 
            { 
                listFail.add(detailRequest.getBookId()); 
                continue;
            } 
    
            SellBookBillDetail newDetail = new SellBookBillDetail(Integer.MAX_VALUE,newSellBookBill, book, detailRequest.getPrice());
            listDetail.add(newDetail);

        } 
        if(!listFail.isEmpty()) 
        { 
            String responseString = "Fail to create bill, books with id: "; 
            for(int i=0;i<listFail.size();i++) 
            {  
                if(i==0) 
                {
                    responseString +=Integer.toString(i);
                } 
                else { 
                    responseString +=","+Integer.toString(i);
                }
            } 
            return new ResponseEntity<>(responseString, HttpStatus.BAD_REQUEST);
        }  
        sellBookBillRepo.save(newSellBookBill);
        for(SellBookBillDetail i: listDetail) 
        { 
            BookImagePath soldBook = i.getBook();
            BookTitleImagePath bookTitle = soldBook.getTitle(); 
            bookTitle.setAmount(bookTitle.getAmount()-1); 
            bookTitle.setAmountRemaining(bookTitle.getAmountRemaining()-1);  
            Integer status=2;
            BookStatus soldStatus = bookStatusRepo.findById(status.byteValue()).orElse(null);  
            soldBook.setStatus(soldStatus); 
            soldBook.setIsUsable(false);
            bookRepo.save(soldBook);
            bookTitleRepo.save(bookTitle); 
            sellBookBillDetailRepo.save(i);
        } 
        return new ResponseEntity<>("Sold books and created bill", HttpStatus.OK);

    } 

    public ResponseEntity<String> responseARenewalRequest(int requestDetailId, boolean response, int librarianId) 
    { 
        RenewalCardDetail renewalDetail = renewalDetailRepo.findById(requestDetailId).orElse(null); 
        if(renewalDetail==null) 
        return new ResponseEntity<>("Renewal request doeas not exist", HttpStatus.BAD_REQUEST);

        renewalDetail.setState(response);  
        String message=""; 
        BorrowingCardDetail borrowingCardDetail = renewalDetail.getBorrowingCardDetail();  
        User librarian = userRepo.findById(librarianId).orElse(null); 
        renewalDetail.setLibrarian(librarian); 
        int newNotifId = notifRepo.findAll().size();  
        User reader = borrowingCardDetail.getService().getReader();
            
        if(response==true) 
        { 
            
            Calendar calendar= Calendar.getInstance(); 
            calendar.setTime(borrowingCardDetail.getExpireDate()); 
            calendar.add(Calendar.DAY_OF_MONTH,30); 
            Date newExpireDate= calendar.getTime(); 
            borrowingCardDetail.setExpireDate(newExpireDate);  
            message="Your book is renewal";
            
        }  
        else 
        {
            message="Your request is denied";
        }
        Notification notification = new Notification(newNotifId,reader,new Date(),false,"Renewal",message ); 
        notifRepo.save(notification); 
        return new ResponseEntity<>("Comoleted response", HttpStatus.OK);
    } 

    public ResponseEntity<String> createReturnBookCard(int librarianId, ReturningRequest request) 
    { 
        User librarian = userRepo.findById(librarianId).orElse(null); 
        List<ReturningDetailRequest> listDetail = request.getListRequest(); 
        List<ReturningCardDetail> listReturningDetail = new ArrayList<>(); 
        ServiceType serviceType= serviceTypeRepo.findById(request.getServiceRequest().getServiceTypeId()).orElse(null);  
        if(serviceType==null) 
        return new ResponseEntity<>("Create returning card fail", HttpStatus.BAD_REQUEST);  
        int newServiceId = serviceRepo.findAll().size(); 
        com.library.librarymanagement.entity.Service newService = new com.library.librarymanagement.entity.Service(newServiceId, librarian, new Date(),serviceType);
        for(ReturningDetailRequest i: listDetail) 
        { 
            BorrowingCardDetail borrowingCardDetail = borrowingCardDetailRepo.findById(i.getBorrowingCardDetailId()).orElse(null); 
            if(borrowingCardDetail==null) return new ResponseEntity<>("Create returning card fail", HttpStatus.BAD_REQUEST);  
            ReturningCardDetail  newReturingDetail = new ReturningCardDetail(Integer.MAX_VALUE, newService,borrowingCardDetail); 
            listReturningDetail.add(newReturingDetail); 
            
        }  
        serviceRepo.save(newService);
        for(ReturningCardDetail i: listReturningDetail) 
        { 
            BookImagePath book= i.getBorrowingCardDetail().getBook(); 
            BookStatus bookStatus = bookStatusRepo.findById((byte)0).orElse(null);  
            book.setStatus(bookStatus); 
            bookRepo.save(book);   
            BookTitleImagePath bookTitle = book.getTitle(); 
            bookTitle.setAmountRemaining(bookTitle.getAmountRemaining()+1); 
            bookTitleRepo.save(bookTitle);
            returningCardDetailRepo.save(i);
            


        }
        return new ResponseEntity<>("Book received", HttpStatus.OK);
    } 


    public ResponseEntity<User> lockUser(int userId) 
    { 
        User user = userRepo.findById(userId).orElse(null); 
        if(user==null) 
        { 
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } 
        if(user.getRole()!=0) 
        { 
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } 
        if(user.getEnable()==false)  
        { 
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } 

        user.setEnable(false); 
        userRepo.save(user); 
        return new ResponseEntity<>(user, HttpStatus.OK);
    } 

    public ResponseEntity<User> unLockUser(int userId) 
    {
        User user = userRepo.findById(userId).orElse(null); 
        if(user==null) 
        { 
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } 
        if(user.getRole()!=0) 
        { 
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } 
        if(user.getEnable()==true)  
        { 
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } 
        user.setEnable(true); 
        userRepo.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    public ResponseEntity<String> responseUnlockRequest(int librarianId, UnlockResponse unlockResponse) 
    {   
        User librarian = userRepo.findById(librarianId).orElse(null);  
        UnlockRequest unlockRequest = unlockRequestRepo.findById(unlockResponse.getUnlockRequestId()).orElse(null);  
        if(unlockRequest==null) 
        { 
            return new ResponseEntity<>("Unlock request not found", HttpStatus.BAD_REQUEST); 
        } 
        unlockRequest.setLibrarian(librarian);  
        unlockRequest.setReponse(unlockResponse.getResponse()); 
        unlockRequestRepo.save(unlockRequest); 


        return new ResponseEntity<>("Response sent", HttpStatus.OK);
    }

    public ResponseEntity<String> updateBookTitleImage(MultipartFile imageFile, int bookTitleId) 
    {
        Path filePath=Paths.get( resourceStrings.DIR_BOOK_TITLE_IMAGE +"/BookTitle"+Integer.toString(bookTitleId)+".png");  
        try {
            Files.delete(filePath); 
            byte[] imageBytes= imageFile.getBytes();
            String path = resourceStrings.DIR_BOOK_TITLE_IMAGE +"/BookTitle"+Integer.toString(bookTitleId)+".png"; 
            
            File newFile = new File(path); 
            newFile.createAndWrite(imageBytes) ; 
            return new ResponseEntity<>("Update success", HttpStatus.OK);
        }
        catch(IOException e) 
        {
            return new ResponseEntity<>("Fail", HttpStatus.OK);
        }

    } 

    public ResponseEntity<String> updateBookTitleInfo(BookTitleUpdateRequest request) 
    {
        BookTitleImagePath bookTitle= bookTitleRepo.findById(request.getId()).orElse(null); 
        if(bookTitle==null||bookTitle.getEnable()==false) 
        {
            return new ResponseEntity<>("Book title not found", HttpStatus.OK);
        }   

        BookTypeImagePath bookType= bookTypeRepo.findById(request.getTypeId()).orElse(null); 
        if(bookType==null) 
        {
            return new ResponseEntity<>("Book type not found", HttpStatus.OK);
        } 

        bookTitle.setName(request.getName());
        bookTitle.setType(bookType);
        bookTitle.setNxb(request.getNxb());
        bookTitle.setYear(request.getYear()); 
        bookTitle.setLanguage(request.getLanguage());
        bookTitle.setPageAmount(request.getPageAmount()); 
        bookTitle.setReview(request.getReview());
        bookTitleRepo.save(bookTitle);
        return new ResponseEntity<>("Success", HttpStatus.OK);



    }


    public ResponseEntity<List<BookBorrowingDetailResponse>> getAllBorrowingCardDetail() 
    {
        List<BorrowingCardDetail> listBorrowingCard= borrowingCardDetailRepo.findAll();
        List<BookBorrowingDetailResponse> listRes= new ArrayList<>();
        for(int i=0;i<listBorrowingCard.size();i++) 
        {
            listRes.add(new BookBorrowingDetailResponse(listBorrowingCard.get(i)));
        } 
        return new ResponseEntity<>(listRes, HttpStatus.OK);
    } 

    public ResponseEntity<String> updateBookTypeImage(int id, MultipartFile imageFile) 
    { 
        BookTypeImagePath bookTypeImagePath= bookTypeRepo.findById((short)id).orElse(null);
        if(bookTypeImagePath==null) 
        {
            return new ResponseEntity<>("Không tìm thấy hiệu sách", HttpStatus.OK);
        }
        Path filePath=Paths.get( resourceStrings.DIR_BOOK_TYPE_IMAGE +"/BookType"+Integer.toString(id)+".png");  
        try {
            Files.delete(filePath); 
            byte[] imageBytes= imageFile.getBytes();
            String path = resourceStrings.DIR_BOOK_TYPE_IMAGE +"/BookType"+Integer.toString(id)+".png"; 
            
            File newFile = new File(path); 
            newFile.createAndWrite(imageBytes) ; 
            return new ResponseEntity<>("Update success", HttpStatus.OK);
        }
        catch(IOException e) 
        {
            return new ResponseEntity<>("Fail", HttpStatus.OK);
        }
    } 
    public ResponseEntity<String> updateBookTypeInfo(BookTypeUpdateRequest request) 
    {
        BookTypeImagePath bookType= bookTypeRepo.findById(request.getId()).orElse(null);
        if(bookType==null) 
        {
            return new ResponseEntity<>("Không tìm thấy thể loại sách", HttpStatus.OK);
        } 
        bookType.setName(request.getName()); 
        bookTypeRepo.save(bookType);
        return new ResponseEntity<>("Cập nhật thông tin thành công", HttpStatus.OK);
    }

    public ResponseEntity<List<User>> getAllReader() 
    {
        return new ResponseEntity<>(userRepo.findByRole(0), HttpStatus.OK);
    } 
    
    public ResponseEntity<List<WorkDetail>> getAllWorkDetail(int librarianId) 
    {
        User librarian= userRepo.findById(librarianId).orElse(null);
        if(librarian==null) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(workDetailRepo.findByLibrarian(librarian), HttpStatus.OK);
    } 

    public ResponseEntity<List<WorkDetail>> getFutureWorkDetail(int librarianId) 
    {
        User librarian= userRepo.findById(librarianId).orElse(null);
        if(librarian==null) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } 
        List<WorkDetail> listRes = new ArrayList<>();
        List<WorkDetail> listWorkDetail= workDetailRepo.findByLibrarian(librarian); 
        Date currentDate= new Date();
        for(int i=0;i<listWorkDetail.size();i++)
        {
            if(!listWorkDetail.get(i).getWorkDate().before(currentDate)) 
            {
                listRes.add(listWorkDetail.get(i));
            }
        } 
        return new ResponseEntity<>(listRes, HttpStatus.OK);
    }    

    public ResponseEntity<String> deleteABookTitle(int bookTitleId) 
    {
        BookTitleImagePath bookTitleImagePath= bookTitleRepo.findById(bookTitleId).orElse(null);
        if(bookTitleImagePath==null||bookTitleImagePath.getEnable()!=true) 
        {
            return new ResponseEntity<>("Cannot delete this book title", HttpStatus.BAD_REQUEST);
        } 
        List<BookImagePath> listBookImagePath= bookRepo.findByTitle(bookTitleImagePath);
        for(int i=0;i<listBookImagePath.size();i++) 
        {
            listBookImagePath.get(i).setIsUsable(false); 
            bookRepo.save(listBookImagePath.get(i));
        } 
        bookTitleImagePath.setEnable(false); 
        bookTitleRepo.save(bookTitleImagePath); 
        return new ResponseEntity<>("Success", HttpStatus.OK);
    } 


    

    


    




    














}
