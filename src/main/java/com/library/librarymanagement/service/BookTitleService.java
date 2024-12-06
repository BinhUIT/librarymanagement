package com.library.librarymanagement.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.librarymanagement.request.BookTitleCreationRequest;
import com.library.librarymanagement.request.BookTitleUpdateRequest;
import com.library.librarymanagement.resource.ResourceStrings;
import com.library.librarymanagement.entity.BookTitleImageData;
import com.library.librarymanagement.entity.BookTitleImagePath;
import com.library.librarymanagement.entity.BookTypeImagePath;
import com.library.librarymanagement.repository.BookTitleRepository;
import com.library.librarymanagement.repository.BookTypeRepository;
import com.library.librarymanagement.ulti.File;
import com.library.librarymanagement.ulti.Report;

import java.sql.Date;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public final class BookTitleService {
    private final BookTitleRepository bookTitleRepository;
    private final BookTypeRepository bookTypeRepository;
    private final DataSource dataSource;

    public BookTitleService(final BookTitleRepository bookTitleRepository,
            final BookTypeRepository bookTypeRepository, final DataSource dataSource) {
        this.bookTitleRepository = bookTitleRepository;
        this.bookTypeRepository = bookTypeRepository;
        this.dataSource = dataSource;
    }

    public List<BookTitleImagePath> getBookTitlesImagePathByPage(final Integer start, final Integer amount) {
        List<BookTitleImagePath> result = Collections.emptyList();

        if ((this.bookTitleRepository != null) && (start >= 0) && (amount > 0)) {
            final var pageable = PageRequest.of(start, amount);
            result = this.bookTitleRepository.findAll(pageable).getContent();
        }

        return result;
    }

    public List<BookTitleImageData> getBookTitlesImageDataByPage(final Integer start, final Integer amount) {
        List<BookTitleImageData> result = new ArrayList<>();

        final var listBookTitleImagePath = this.getBookTitlesImagePathByPage(start, amount);
        for (final var bookTitleImagePath : listBookTitleImagePath) {
            result.add(new BookTitleImageData(bookTitleImagePath));
        }

        return result;
    }

    public BookTitleImagePath getBookTitleImagePathById(final Integer id) {
        BookTitleImagePath result = null;

        if ((this.bookTitleRepository != null) && (id != null)) {
            result = this.bookTitleRepository.findById(id).orElse(null);
        }

        return result;
    }

    public BookTitleImageData getBookTitleImageDataById(final Integer id) {
        BookTitleImageData result = null;

        final var bookTitleImagePath = this.getBookTitleImagePathById(id);
        if (bookTitleImagePath != null) {
            result = new BookTitleImageData(bookTitleImagePath);
        }

        return result;
    } 
    public ResponseEntity<List<BookTitleImageData>> getBookTitleByType(short typeId) 
    { 
        BookTypeImagePath bookType = bookTypeRepository.findById(typeId).orElse(null); 
        if(bookType==null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } 
        List<BookTitleImagePath> listBookTitle= bookTitleRepository.findByType(bookType); 
        List<BookTitleImageData> listBookTitleImageData = new ArrayList<>();
        for(int i=0;i<listBookTitle.size();i++) 
        {
            BookTitleImageData bookTitleImageData= new BookTitleImageData(listBookTitle.get(i)); 
            listBookTitleImageData.add(bookTitleImageData); 

        }  
        return new ResponseEntity<>(listBookTitleImageData, HttpStatus.OK);

    }

    public boolean createBookTitle(final BookTitleCreationRequest request) {
        if ((this.bookTitleRepository == null) || (request == null) || (ResourceStrings.DIR_BOOK_TITLE_IMAGE == null)
                || this.bookTitleRepository.existsByName(request.getName())) {
            return false;
        }

        final var newBookTypeImagePath = this.bookTypeRepository.findById(request.getTypeId()).orElse(null);
        if (newBookTypeImagePath == null) {
            return false;
        }

        var newImagePath = String.format("%s/%s", ResourceStrings.DIR_BOOK_TITLE_IMAGE, request.getName());
        var newImageFile = new File(newImagePath);
        if (!newImageFile.createAndWrite(request.getImageData())) {
            return false;
        }

        final var newBookTitleImagePath = new BookTitleImagePath(request.getName(), newBookTypeImagePath,
                request.getAuthor(), newImagePath);

        this.bookTitleRepository.save(newBookTitleImagePath);
        return true;
    }

    public boolean updateBookTitle(final BookTitleUpdateRequest request) {
        if ((request == null) || (this.bookTitleRepository == null) || (this.bookTypeRepository == null)
                || (ResourceStrings.DIR_BOOK_TITLE_IMAGE == null)) {
            return false;
        }

        final var newBookTypeImagePath = this.bookTypeRepository.findById(request.getTypeId()).orElse(null);
        if (newBookTypeImagePath == null) {
            return false;
        }

        var bookTitleImagePath = this.getBookTitleImagePathById(request.getId());
        if (bookTitleImagePath == null) {
            return false;
        }

        var oldImagePath = bookTitleImagePath.getImagePath();
        var newImagePath = String.format("%s/%s", ResourceStrings.DIR_BOOK_TITLE_IMAGE, request.getName());
        if (oldImagePath == null || newImagePath == null) {
            return false;
        }

        var oldImageFile = new File(oldImagePath);
        if (newImagePath.equals(oldImagePath)) {
            return oldImageFile.write(request.getImageData());
        }
        if (Boolean.TRUE.equals(oldImageFile.isExisted()) && Boolean.FALSE.equals(oldImageFile.isModifiable())) {
            return false;
        }

        var newImageFile = new File(newImagePath);
        if ((!newImageFile.createAndWrite(request.getImageData())) || (!oldImageFile.delete())) {
            newImageFile.delete();
            return false;
        }

        bookTitleImagePath.setName(request.getName());
        bookTitleImagePath.setType(newBookTypeImagePath);
        bookTitleImagePath.setAuthor(request.getAuthor());
        bookTitleImagePath.setImagePath(newImagePath);
        this.bookTitleRepository.save(bookTitleImagePath);

        return true;
    } 


    public ResponseEntity<List<BookTitleImageData>> getBookTitleByAuthor(String author) {
        List<BookTitleImagePath> listBookTitleImagePath = bookTitleRepository.findByAuthor(author);  
        List<BookTitleImageData> listBookTitleImageData = new ArrayList<>();
        for (int i = 0; i < listBookTitleImagePath.size(); i++)
        {
            BookTitleImageData bookTitleImageData = new BookTitleImageData(listBookTitleImagePath.get(i)); 
            listBookTitleImageData.add(bookTitleImageData);
        }
        return new ResponseEntity<>(listBookTitleImageData, HttpStatus.OK);
    } 

    public ResponseEntity<BookTitleImageData> findByName(String name) {
        BookTitleImagePath bookTitleImagePath = bookTitleRepository.findByName(name); 
        return new ResponseEntity<>(new BookTitleImageData(bookTitleImagePath), HttpStatus.OK);
    }

    public byte[] exportReportBorrowingByTypeAndDateRange(
            final Short bookTypeId, final Date startDate, final Date endDate) throws Exception {
        final HashMap<String, Object> parameters = HashMap.newHashMap(3);
        parameters.put("BookTypeId", bookTypeId);
        parameters.put("ReportStartDate", startDate);
        parameters.put("ReportEndDate", endDate);

        return Report.exportReportFromJasper("/reports/BookTitleBorrowing.jasper", parameters, dataSource);
    }
}
