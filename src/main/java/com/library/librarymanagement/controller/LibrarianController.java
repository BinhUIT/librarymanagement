package com.library.librarymanagement.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.library.librarymanagement.entity.BookImagePath;
import com.library.librarymanagement.entity.BorrowingCardDetail;
import com.library.librarymanagement.entity.Penalty;
import com.library.librarymanagement.entity.RenewalDetail;
import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.entity.WorkDetail;
import com.library.librarymanagement.request.BookTitleCreateRequest;
import com.library.librarymanagement.request.BookTitleUpdateRequest;
import com.library.librarymanagement.request.BookTypeCreateRequest;
import com.library.librarymanagement.request.BookTypeUpdateRequest;
import com.library.librarymanagement.request.BorrowOfflineRequest;
import com.library.librarymanagement.request.BuyBookBillRequest;
import com.library.librarymanagement.request.RenewalRequest;
import com.library.librarymanagement.request.SellBookBillCreateRequest;
import com.library.librarymanagement.request.UnlockResponse;
import com.library.librarymanagement.request.UpdatePenaltyRequest;
import com.library.librarymanagement.response.BookBorrowingDetailResponse;
import com.library.librarymanagement.security.TokenSecurity;
import com.library.librarymanagement.service.LibrarianService;

import jakarta.mail.MessagingException;

@RestController
public class LibrarianController { 
    @Autowired 
    private LibrarianService librarianService;

    @Autowired 
    private TokenSecurity tokenSecurity;  
    
    @PostMapping("/librarian/buyBook") 
    public ResponseEntity<String> buyBook(@RequestHeader("Authorization") String authHeader, @RequestBody BuyBookBillRequest request)  
    {  
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.checkToken(authHeader.substring(7))||tokenSecurity.extractRole(authHeader)<1) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);

        } 
        int userId = tokenSecurity.extractUserId(authHeader.substring(7)); 
        return librarianService.buyBook(request, userId);
    } 
    @PostMapping("/librarian/create/BookTitle/image")  
    public ResponseEntity<String> createNewBookTitleImage(@RequestHeader("Authorization") String authHeader, @RequestParam("file") MultipartFile imageFile) 
    { 
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        }

        if(librarianService.createNewBookTitleImage(imageFile)) 
        {
            return new ResponseEntity<>("Create image Success", HttpStatus.OK);
        } 
        else { 
            return new ResponseEntity<>("Create image fail", HttpStatus.BAD_REQUEST);
        }


    } 
    @PostMapping("/librarian/create/BookTitle/info" ) 
    public ResponseEntity<String> createNewBookTitle(@RequestHeader("Authorization") String authHeader, @RequestBody BookTitleCreateRequest request) 
    {  
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        } 
        return librarianService.createNewBookTitle(request);
    }  

    @PostMapping("/librarian/create/BookType/image")  
    public ResponseEntity<String> createNewBookTypeImage(@RequestHeader("Authorization") String authHeader, @RequestParam("file") MultipartFile imageFile) 
    { 
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        }

        if(librarianService.createNewBookTypeImage(imageFile)) 
        {
            return new ResponseEntity<>("Create image Success", HttpStatus.OK);
        } 
        else { 
            return new ResponseEntity<>("Create image fail", HttpStatus.BAD_REQUEST);
        }
    }   
    @PostMapping("/librarian/create/BookType/info" ) 
    public ResponseEntity<String> createNewBookType(@RequestHeader("Authorization") String authHeader, @RequestBody BookTypeCreateRequest request) 
    {  
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        } 
        return librarianService.createNewBookType(request);
    } 

    @PostMapping("/librarian/sell/book") 
    public ResponseEntity<String> sellBook(@RequestHeader("Authorization") String authHeader, @RequestBody SellBookBillCreateRequest request) 
    {  
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        } 
        return librarianService.SellBook(request, userId);
    } 

    

    @PostMapping("/librarian/lock/{userId}") 
    public ResponseEntity<User> lockUser(@RequestHeader("Authorization") String authHeader, @PathVariable int userId) 
    { 
        int librarianId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(librarianId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }  
        return librarianService.lockUser(userId);
        
    }  

    @PostMapping("/librarian/response/unlockRequest") 
    public ResponseEntity<String> responseUnlockRequest(@RequestHeader("Authorization") String authHeader, @RequestBody UnlockResponse unlockResponse) 
    { 
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        } 

        return librarianService.responseUnlockRequest(userId,unlockResponse);
    }

    @GetMapping("/librarian/getAllBorrowing") 
    public ResponseEntity<List<BorrowingCardDetail>> getAllBorrowing(@RequestHeader("Authorization") String authHeader)
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }  
        return librarianService.getAllBorrowingCardDetail();
    } 

    @PutMapping("/librarian/update/bookTitle/image/{id}") 
    public ResponseEntity<String> updateBookTitleImage(@RequestHeader("Authorization") String authHeader, @RequestParam("file") MultipartFile file, @PathVariable int id) 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("No permission", HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("No permission", HttpStatus.BAD_REQUEST);
        }  
        return librarianService.updateBookTitleImage(file,id);
    }

    @PutMapping("/librarian/updates/bookTitle/info") 
    public ResponseEntity<String> updateBookTitleInfo(@RequestHeader("Authorization") String authHeader, @RequestBody BookTitleUpdateRequest request)
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("No permission", HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("No permission", HttpStatus.BAD_REQUEST);
        } 
        return librarianService.updateBookTitleInfo(request);
    } 

    @PutMapping("/librarian/update/bookType/image/{id}") 
    public ResponseEntity<String> updateBookTypeImage(@RequestHeader("Authorization") String authHeader, @PathVariable int id, @RequestParam("file") MultipartFile file) 
    
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("No permission", HttpStatus.OK);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("No permission", HttpStatus.OK);
        }  
        return librarianService.updateBookTypeImage(id, file);
    } 

    @PutMapping("/librarian/update/bookType/info") 
    public ResponseEntity<String> updateBookTypeInfo(@RequestHeader("Authorization") String authHeader, @RequestBody BookTypeUpdateRequest request) 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("No permission", HttpStatus.OK);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("No permission", HttpStatus.OK);
        } 
        return librarianService.updateBookTypeInfo(request);
    }

    @GetMapping("/librarian/getAllReader") 
    public ResponseEntity<List<User>> getAllUser(@RequestHeader("Authorization") String authHeader)
 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        return librarianService.getAllReader();
    } 

    @PostMapping("/librarian/unlock/user/{id}") 
    public ResponseEntity<User> unLockUser(@RequestHeader("Authorization") String authHeader, @PathVariable int id) 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        return librarianService.unLockUser(id);
    }

    @GetMapping("/librarian/getAllWorkDate") 
    public ResponseEntity<List<WorkDetail>> getAllWorkDetail(@RequestHeader("Authorization") String authHeader) 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        return librarianService.getAllWorkDetail(userId);
    } 

    @GetMapping("/librarian/getFutureWorkDate") 
    public ResponseEntity<List<WorkDetail>> getFutureWorkDetail(@RequestHeader("Authorization") String authHeader) 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        return librarianService.getFutureWorkDetail(userId);
    }

    @GetMapping("/librarian/getAllBook") 
    public ResponseEntity<List<BookImagePath>> getAllBook(@RequestHeader("Authorization") String authHeader) 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        return librarianService.getAllBook();
    }

    @DeleteMapping("/librarian/deleteBook/{id}") 
    public ResponseEntity<String> deleteBook(@RequestHeader("Authorization") String authHeader, @PathVariable int id) 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }  
       return librarianService.deleteBook(id);
    }     

    @GetMapping("/librarian/all_borrow_detail") 
    public ResponseEntity<List<BorrowingCardDetail>> getAllBorrowDetail(@RequestHeader("Authorization") String authHeader) 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        
        return librarianService.getAllBorrowingCardDetail();
    }
    
    

   
    @GetMapping("/librarian/penalty_all")
    public ResponseEntity<List<Penalty>> getAllPenaltyCard(@RequestHeader("Authorization") String authHeader) 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return librarianService.getAllPenalty();
    } 

    @GetMapping("/librarian/penalty/{id}") 
    public ResponseEntity<Penalty> getPenalty(@RequestHeader("Authorization") String authHeader,@PathVariable int id) 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
            if(userId<=-1) 
            {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            } 
            if(!tokenSecurity.userExist(userId))  
            { 
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        return librarianService.findPenaltyById(id);
    } 

    @PutMapping("/librarian/update/penalty/{id}") 
    public ResponseEntity<Penalty> updatePenalty(@RequestHeader("Authorization") String authHeader, @RequestBody UpdatePenaltyRequest request, @PathVariable int id) 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return librarianService.updatePenalty(request, id);
    }
    

    @PutMapping("/librarian/readerTakeBook/{id}") 
    public ResponseEntity<String> readerTakeBook(@RequestHeader("Authorization") String authHeader, @PathVariable int id) 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return librarianService.readerTakeBook(id);
    } 

    @PutMapping("/librarian/returnBook/{id}") 
    public ResponseEntity<String> returnBook(@RequestHeader("Authorization") String authHeader, @PathVariable int id) 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        return librarianService.readerReturnBook(id);
    }

    @PutMapping("/librarian/response/renewal/{id}") 
    public ResponseEntity<String> responseRenewal(@RequestHeader("Authorization") String authHeader, @PathVariable int id, @RequestBody String isAccept) throws UnsupportedEncodingException, MessagingException 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        return librarianService.responseRenewal(id, isAccept);
    }

    @GetMapping("/librarian/renewal_list") 
    public ResponseEntity<List<RenewalDetail>> getAllRenewalRequest(@RequestHeader("Authorization") String authHeader) 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        return librarianService.getWaitingRenewalRequest();
    }

    @PostMapping("/librarian/borrowOffline") 
    public ResponseEntity<List<BorrowingCardDetail>> borrowOffline(@RequestHeader("Authorization") String authHeader, @RequestBody BorrowOfflineRequest request) 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        return librarianService.borrowManually(request);
    }

    @DeleteMapping("/librarian/destroy_book_lock_user/{id}") 
    public ResponseEntity<String> destroyBookAndLockUser(@RequestHeader("Authorization") String authHeader, @PathVariable int id) throws UnsupportedEncodingException, MessagingException 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        return librarianService.destroyBookAndLockUser(id);
    } 

    @PutMapping("/librarian/renewalOffline/{id}") 
    public ResponseEntity<String> renewalOnline(@RequestHeader("Authorization") String authHeader, @PathVariable int id,@RequestBody RenewalRequest request) 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        return librarianService.renewalOffline(id, request);
    }






}
