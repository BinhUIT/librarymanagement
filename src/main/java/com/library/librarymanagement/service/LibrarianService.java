package com.library.librarymanagement.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.http.HttpClient.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
import com.library.librarymanagement.entity.Penalty;
import com.library.librarymanagement.entity.Regulation;
import com.library.librarymanagement.entity.RenewalCardDetail;
import com.library.librarymanagement.entity.RenewalDetail;
import com.library.librarymanagement.entity.ReturningCardDetail;
import com.library.librarymanagement.entity.SellBookBill;
import com.library.librarymanagement.entity.SellBookBillDetail;
import com.library.librarymanagement.entity.ServiceType;
import com.library.librarymanagement.entity.UnlockRequest;
import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.entity.WorkDetail;
import com.library.librarymanagement.entity.BorrowingCardDetail.Status;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.repository.BookStatusRepository;
import com.library.librarymanagement.repository.BookTitleRepository;
import com.library.librarymanagement.repository.BookTypeRepository;
import com.library.librarymanagement.repository.BorrowingCardDetailRepository;
import com.library.librarymanagement.repository.BuyBookBillDetailRepository;
import com.library.librarymanagement.repository.BuyBookBillRepository;
import com.library.librarymanagement.repository.NotificationRepository;
import com.library.librarymanagement.repository.PenaltyRepository;
import com.library.librarymanagement.repository.RenewalDetailRepository;
import com.library.librarymanagement.repository.ReturningCardDetailRpository;
import com.library.librarymanagement.repository.ReulationRepository;
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
import com.library.librarymanagement.request.BorrowOfflineRequest;
import com.library.librarymanagement.request.BuyBookBillDetailRequest;
import com.library.librarymanagement.request.BuyBookBillRequest;
import com.library.librarymanagement.request.CreatePenaltyRequest;
import com.library.librarymanagement.request.RenewalRequest;
import com.library.librarymanagement.request.ReturningDetailRequest;
import com.library.librarymanagement.request.ReturningRequest;
import com.library.librarymanagement.request.SellBookBillCreateRequest;
import com.library.librarymanagement.request.SellBookBillDetailRequest;
import com.library.librarymanagement.request.UnlockResponse;
import com.library.librarymanagement.request.UpdatePenaltyRequest;
import com.library.librarymanagement.resource.ResourceStrings;
import com.library.librarymanagement.response.BookBorrowingDetailResponse;
import com.library.librarymanagement.ulti.File;

import jakarta.mail.MessagingException;



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
    private PenaltyRepository penaltyRepo; 
    
    @Autowired 
    private UserService userService;  

    @Autowired 
    private ReulationRepository regulationRepo;


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
            
            if(book.getStatus().getId()!=(byte)0&&book.getStatus().getId()!=1) 
            { 
                listFail.add(detailRequest.getBookId()); 
                continue;
            } 
            if(book.getStatus().getId()==(byte)1) 
            {
                if(book.getTitle().getAmountRemaining()==0) 
                {
                    listFail.add(detailRequest.getBookId()); 
                    continue;
                }
            }
    
            SellBookBillDetail newDetail = new SellBookBillDetail(Integer.MAX_VALUE,newSellBookBill, book, detailRequest.getPrice());
            listDetail.add(newDetail);

        } 
        if(!listFail.isEmpty()) 
        { 
            String responseString = "Fail to create bill"; 
             
            return new ResponseEntity<>(responseString, HttpStatus.OK);
        }  
        sellBookBillRepo.save(newSellBookBill);
        for(SellBookBillDetail i: listDetail) 
        { 
            BookImagePath soldBook = i.getBook();
            BookTitleImagePath bookTitle = soldBook.getTitle();
            if(soldBook.getStatus().getId()==(byte)1) 
            {
                List<BorrowingCardDetail> borrowDetail = borrowingCardDetailRepo.findByBook(soldBook);
                for(int j=0;j<borrowDetail.size();j++) 
                {
                    if(borrowDetail.get(j).getStatus()==Status.PENDING) 
                    {
                        List<BookImagePath> listBookReady = bookRepo.findByTitle(bookTitle);
                        for(int g=0;g<listBookReady.size();g++) 
                        {
                            if(listBookReady.get(g).getStatus().getId()==(byte)0&&listBookReady.get(g).getIsUsable()==true) 
                            {
                                borrowDetail.get(j).setBook(listBookReady.get(g)); 
                                listBookReady.get(g).setStatus(bookStatusRepo.findById((byte)1).orElse(null));
                                bookRepo.save(listBookReady.get(g));
                                borrowingCardDetailRepo.save(borrowDetail.get(j));
                                break;
                            }
                        }
                        break;
                    }
                }
            } 
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


    public ResponseEntity<List<BorrowingCardDetail>> getAllBorrowingCardDetail() 
    {
        return new ResponseEntity<>(borrowingCardDetailRepo.findAll(), HttpStatus.OK);
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

    

    public ResponseEntity<List<BookImagePath>> getAllBook() 
    {
        return new ResponseEntity<>(bookRepo.findByIsUsable(true), HttpStatus.OK);
    }

        

    

    
    public ResponseEntity<String> readerTakeBook(int borrowingCardDetailId) 
    {
        BorrowingCardDetail borrowingCardDetail = borrowingCardDetailRepo.findById(borrowingCardDetailId).orElse(null);
        if(borrowingCardDetail==null) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } 
        if(borrowingCardDetail.getStatus()!=Status.PENDING) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } 
        borrowingCardDetail.updateStatus(Status.BORROWING);
        BookTitleImagePath bookTitle= borrowingCardDetail.getBook().getTitle();
        bookTitle.setBorrowTime(bookTitle.getBorrowTime()+1); 
        bookTitleRepo.save(bookTitle);
        BookImagePath book= borrowingCardDetail.getBook();
        book.setStatus(bookStatusRepo.findById((byte)3).orElse(null));
        bookRepo.save(book);
        borrowingCardDetailRepo.save(borrowingCardDetail);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    } 

    public ResponseEntity<String> readerReturnBook(int borrowingCardDetailId) 
    {  
        Regulation regulation= regulationRepo.findById(1).orElse(null);
        BorrowingCardDetail borrowingCardDetail = borrowingCardDetailRepo.findById(borrowingCardDetailId).orElse(null);
        if(borrowingCardDetail==null) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } 
        if(borrowingCardDetail.getStatus()!=Status.BORROWING&&borrowingCardDetail.getStatus()!=Status.RENEWAL) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }  
        borrowingCardDetail.updateStatus(Status.RETURNED);
        LocalDate startLocalDate = borrowingCardDetail.getExpireDate().toLocalDate(); 
        Date expire = borrowingCardDetail.getExpireDate();
        borrowingCardDetail.setExpireDate(new Date());

        borrowingCardDetailRepo.save(borrowingCardDetail); 
        BookImagePath book = borrowingCardDetail.getBook();
        book.setStatus(bookStatusRepo.findById((byte)0).orElse(null)); 
        bookRepo.save(book);

        BookTitleImagePath bookTitle = book.getTitle(); 
        if(bookTitle.getEnable()==false||bookTitle.getType().getEnable()==false) 
        {
            book.setIsUsable(false);
        }
        bookTitle.setAmountRemaining(bookTitle.getAmountRemaining()+1); 
        bookTitleRepo.save(bookTitle); 
        Date currentDate = new Date();
        if(expire.before(currentDate)) 
        { 
            
            LocalDate endLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); 
            System.out.println("Here"); // Calculate the number of days between the two dates 
            int price = -(int)ChronoUnit.DAYS.between(startLocalDate, endLocalDate)*regulation.getMoneyLatePerDay();  
            
            
            int penaltyId= penaltyRepo.findAll().size()+1;
            if(penaltyId==1) 
            {
                Penalty penalty = new Penalty(1, "Trả sách trễ", borrowingCardDetail.getService().getReader(),price, new Date());
                penaltyRepo.save(penalty);
            } 
            else {
            Penalty penalty= new Penalty("Trả sách trễ", borrowingCardDetail.getService().getReader(),price);
            User user = borrowingCardDetail.getService().getReader();
            user.setPenaltyTime(user.getPenaltyTime()+1); 
            userRepo.save(user);
            penaltyRepo.save(penalty);  
            }
            return  new ResponseEntity<>(Integer.toString(penaltyId), HttpStatus.OK);
            
        }
        
        return new ResponseEntity<>("Success", HttpStatus.OK);

    }

    public ResponseEntity<List<Penalty>> getAllPenalty() 
    {
        return new ResponseEntity<>(penaltyRepo.findAll(), HttpStatus.OK);
    } 
    public ResponseEntity<Penalty> findPenaltyById(int id) 
    {
        Penalty penalty = penaltyRepo.findById(id).orElse(null);
        if(penalty==null) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } 
        return new ResponseEntity<>(penalty, HttpStatus.OK);
    } 

    public ResponseEntity<Penalty> updatePenalty(UpdatePenaltyRequest request, int id)
    {
        Penalty penalty = penaltyRepo.findById(id).orElse(null);
        if(penalty==null) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        penalty.setContent(request.getContent()); 
        penaltyRepo.save(penalty);
        return new ResponseEntity<>(penalty, HttpStatus.OK);
    } 

    public ResponseEntity<Penalty> createPenalty(CreatePenaltyRequest request) 
    {
        User reader= userRepo.findById(request.getReaderId()).orElse(null);
        if(reader==null||reader.getRole()==1) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } 
        Penalty penalty= new Penalty(request.getContent(), reader, request.getMoney());
        penaltyRepo.save(penalty);
        reader.setPenaltyTime(reader.getPenaltyTime()+1); 
        userRepo.save(reader);
        return new ResponseEntity<>(penalty, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteBook(int bookId) 
    {
        BookImagePath bookImagePath= bookRepo.findById(bookId).orElse(null); 
        
        if(bookImagePath==null||bookImagePath.getIsUsable()==false) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } 
        BookTitleImagePath bookTitle= bookImagePath.getTitle();
        List<BorrowingCardDetail> listBorrowDetail = borrowingCardDetailRepo.findByBook(bookImagePath);
        for(int i=0;i<listBorrowDetail.size();i++) 
        {
            if(listBorrowDetail.get(i).getStatus()==Status.BORROWING||listBorrowDetail.get(i).getStatus()==Status.RENEWAL) 
            {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } 
            if(listBorrowDetail.get(i).getStatus()==Status.PENDING) 
            {
                
                if(bookTitle.getAmountRemaining()==0) 
                {
                    return new ResponseEntity<>("Not enough", HttpStatus.OK);
                }
                List<BookImagePath> listBook = bookRepo.findByTitle(bookTitle);
                for(int j=0;j<listBook.size();j++) 
                {
                    if(listBook.get(j).getId()!=bookImagePath.getId()) 
                    {
                        if(listBook.get(j).getIsUsable()==true&&listBook.get(j).getStatus().getId()==0) 
                        {
                            listBook.get(j).setStatus(bookStatusRepo.findById((byte)1).orElse(null)); 
                            listBorrowDetail.get(i).setBook(listBook.get(j));
                            bookTitle.setAmountRemaining(bookTitle.getAmountRemaining()-1);  
                            bookTitle.setAmount(bookTitle.getAmount()-1);
                            bookImagePath.setIsUsable(false);

                            bookRepo.save(listBook.get(j)); 
                            bookRepo.save(bookImagePath);
                            borrowingCardDetailRepo.save(listBorrowDetail.get(i));
                            bookTitleRepo.save(bookTitle);
                            break;
                        }
                    }
                } 
                return new ResponseEntity<>("Success", HttpStatus.OK);
            }
        } 
        bookTitle.setAmountRemaining(bookTitle.getAmountRemaining()-1);  
        bookTitle.setAmount(bookTitle.getAmount()-1);
        bookImagePath.setIsUsable(false); 
        bookRepo.save(bookImagePath);
        bookTitleRepo.save(bookTitle);
        return new ResponseEntity<>("Success", HttpStatus.OK);


    }

    public ResponseEntity<String> responseRenewal(int id,String isAccept ) throws UnsupportedEncodingException, MessagingException 
    {
        RenewalDetail renewalDetail = renewalDetailRepo.findById(id).orElse(null); 
        if(renewalDetail==null) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } 
        if(renewalDetail.getBorrowingCardDetail().getStatus()!=Status.RENEWAL) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } 
        BorrowingCardDetail borrowingCardDetail= borrowingCardDetailRepo.findById(renewalDetail.getBorrowingCardDetail().getId()).orElse(null);
        if(isAccept.equals("Accept")) 
        {
            borrowingCardDetail.updateStatus(Status.BORROWING); 
            borrowingCardDetail.setExpireDate(renewalDetail.getNewExpireDate()); 
            renewalDetail.setStatus(1); 
             
            String message = "Gia hạn sách với mã số "+Integer.toString(renewalDetail.getBorrowingCardDetail().getBook().getId())+" thành công";
            String content ="Gia hạn thành công";
            userService.sendEmail(borrowingCardDetail.getService().getReader(), content, message);

        } 
        else {
           borrowingCardDetail.updateStatus(Status.BORROWING); 
             
            String message = "Gia hạn sách với mã số "+Integer.toString(renewalDetail.getBorrowingCardDetail().getBook().getId())+" thất bại"; 
            renewalDetail.setStatus(0); 
            String content ="Gia hạn thất bại";
            userService.sendEmail(borrowingCardDetail.getService().getReader(), content, message);

            
        } 
        borrowingCardDetailRepo.save(borrowingCardDetail);
        renewalDetailRepo.delete(renewalDetail); 
        return new ResponseEntity<>("Success", HttpStatus.OK);

        

    }

    public ResponseEntity<List<RenewalDetail>> getWaitingRenewalRequest() 
    {
        return new ResponseEntity<>(renewalDetailRepo.findByStatus(-1), HttpStatus.OK);
    } 

    public ResponseEntity<List<Penalty>> getPenaltyByUser(int userId) 
    { 
        User reader = userRepo.findById(userId).orElse(null);
        if(reader==null) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(penaltyRepo.findByReader(reader), HttpStatus.OK);
    }


    public ResponseEntity<List<BorrowingCardDetail>> borrowManually(BorrowOfflineRequest request) 
    {
        User reader = userRepo.findById(request.getUserId()).orElse(null);
        if(reader==null||reader.getEnable()==false) 
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } 
        int newServiceId = serviceRepo.findAll().size()+1; 
        ServiceType serviceType = serviceTypeRepo.findById(0).orElse(null); 
        com.library.librarymanagement.entity.Service service = new com.library.librarymanagement.entity.Service(newServiceId, reader, new Date(), serviceType);
        for(int i=0;i<request.getListBook().size();i++) 
        {  
            BookImagePath book = bookRepo.findById(request.getListBook().get(i)).orElse(null);
            if(book==null||book.getIsUsable()==false||book.getStatus().getId()!=0) 
            {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } 
        for(int i=0;i<request.getListBook().size();i++) 
        { 
            BookImagePath book = bookRepo.findById(request.getListBook().get(i)).orElse(null);
            int newBorrowDetailId = borrowingCardDetailRepo.findAll().size(); 
            Date currentDate = new Date();  
            Calendar calendar = Calendar.getInstance(); 
            calendar.setTime(currentDate); 
            calendar.add(Calendar.DAY_OF_MONTH, 7);  
             Date expireDate = calendar.getTime();
            BorrowingCardDetail borrowingCardDetail = new BorrowingCardDetail(newBorrowDetailId, service, book,expireDate);
            borrowingCardDetail.updateStatus(Status.BORROWING);
            borrowingCardDetailRepo.save(borrowingCardDetail);

        } 
        return new ResponseEntity<>(borrowingCardDetailRepo.findByService_ServiceId(service.getServiceId()), HttpStatus.OK);


    }

    public ResponseEntity<String> destroyBookAndLockUser(int borrowDetailId) throws UnsupportedEncodingException, MessagingException 
    {
        BorrowingCardDetail borrowDetail = borrowingCardDetailRepo.findById(borrowDetailId).orElse(null); 
        if(borrowDetail==null) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        borrowDetail.updateStatus(Status.DESTROY);

        BookImagePath book= borrowDetail.getBook();
        book.setIsUsable(false); 
        BookTitleImagePath bookTitle = book.getTitle();
        bookTitle.setAmount(bookTitle.getAmount()-1); 
        bookTitle.setAmountRemaining(bookTitle.getAmountRemaining()-1); 
        User reader = userRepo.findById(borrowDetail.getService().getReader().getUserId()).orElse(null);
        reader.setEnable(false);  
        borrowingCardDetailRepo.save(borrowDetail);
        if(bookTitle.getEnable()==false||bookTitle.getType().getEnable()==false) 
        {
            book.setIsUsable(false);
        }
        bookRepo.save(book);
        bookTitleRepo.save(bookTitle);
        userRepo.save(reader);
        String content ="Khóa tài khoản";
        String subject ="Bạn đã bị khóa tài khoản vì trả sách trễ, vui lòng liên hệ thủ thư để giải quyết";
        userService.sendEmail(reader, content, subject); 
        return new ResponseEntity<>("Success", HttpStatus.OK);

    }

    public ResponseEntity<String> renewalOffline(int borrowDetailId, RenewalRequest request) throws UnsupportedEncodingException, MessagingException 
    {
        BorrowingCardDetail borrowingCardDetail = borrowingCardDetailRepo.findById(borrowDetailId).orElse(null);
        if(borrowingCardDetail==null||borrowingCardDetail.getStatus()!=Status.BORROWING) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } 
        borrowingCardDetail.setExpireDate(request.getNewExpireDate()); 
        borrowingCardDetailRepo.save(borrowingCardDetail);
        String message = "Gia hạn sách với mã số "+Integer.toString(borrowingCardDetail.getBook().getId())+" thành công";
           String content ="Gia hạn thành công";
           userService.sendEmail(borrowingCardDetail.getService().getReader(), content, message);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    } 
    public ResponseEntity<List<SellBookBillDetail>> getAllSellBookBillDetail() 
    {
        return new ResponseEntity<>(sellBookBillDetailRepo.findAll(), HttpStatus.OK);
    } 
    public ResponseEntity<List<BuyBookBillDetail>> getAllBuyBookBill() 
    {
        return new ResponseEntity<>(buyBookBillDetailRepo.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> updateRegulation(Regulation regulation) 
    {
        Regulation reg= regulationRepo.findById(1).orElse(null);
        reg.setDaysToResponseRenewal(regulation.getDaysToResponseRenewal()); 
        reg.setDaysToTakeBook(regulation.getDaysToTakeBook()); 
        reg.setDefaultBorrowingDays(regulation.getDefaultBorrowingDays()); 
        reg.setDaysToLockUser(regulation.getDaysToLockUser());
        regulationRepo.save(reg);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    
    public ResponseEntity<Object> checkCanSell(int bookId) 
    {
        BookImagePath book = bookRepo.findById(bookId).orElse(null);
        if(book==null||book.getIsUsable()==false||book.getStatus().getId()==(byte)2) 
        {
            return new ResponseEntity<>(new Object(){
                public String message="Không thể bán";
                
            }, HttpStatus.OK);
        }
        if(book.getStatus().getId()==(byte)0) 
        {
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
        if(book.getStatus().getId()==(byte)1) 
        {
            if(book.getTitle().getAmountRemaining()==0) 
            {
                return new ResponseEntity<>(new Object(){
                    public String message="Sách đang được chờ lấy và không có sách thay thế";
                    
                }, HttpStatus.OK);
            }
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
        return new ResponseEntity<>(new Object(){
            public String message="Sách đang được mượn, không thể bán";
            
        }, HttpStatus.OK);
    } 

    public ResponseEntity<String> deleteBookTitle(int bookTitleId) 
    {
        BookTitleImagePath bookTitleImagePath = bookTitleRepo.findById(bookTitleId).orElse(null);
        if(bookTitleImagePath==null||bookTitleImagePath.getEnable()==false) 
        {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        } 
        List<BookImagePath> listBook = bookRepo.findByTitle(bookTitleImagePath);
        for(int i=0;i<listBook.size();i++) 
        {
            if(listBook.get(i).getStatus().getId()==(byte)0) 
            {
                listBook.get(i).setIsUsable(false); 
                bookRepo.save(listBook.get(i));
            }
        }
        bookTitleImagePath.setEnable(false);
        bookTitleRepo.save(bookTitleImagePath);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    public ResponseEntity<String> deleteBookType(short bookTypeId) 
    {
        BookTypeImagePath bookType= bookTypeRepo.findById(bookTypeId).orElse(null);
        if(bookType==null||bookType.getEnable()==false) 
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        List<BookTitleImagePath> listBookTitle = bookTitleRepo.findByType(bookType);
        for(int i=0;i<listBookTitle.size();i++) 
        { 
            if(listBookTitle.get(i).getEnable()) {
            deleteBookTitle(listBookTitle.get(i).getId()); 
            }
        } 
        bookType.setEnable(false); 
        bookTypeRepo.save(bookType);
        return new ResponseEntity<>("Success", HttpStatus.OK);

    }
    

    


    




    














}
