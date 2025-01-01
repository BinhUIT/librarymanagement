package com.library.librarymanagement.service;

import java.util.List;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.HashMap; 
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.library.librarymanagement.request.BookTypeCreationRequest;
import com.library.librarymanagement.request.BookTypeUpdateRequest;
import com.library.librarymanagement.resource.ResourceStrings;
import com.library.librarymanagement.entity.BookTypeImageData;
import com.library.librarymanagement.entity.BookTypeImagePath;
import com.library.librarymanagement.repository.BookTypeRepository;
import com.library.librarymanagement.ulti.File;
import com.library.librarymanagement.ulti.JasperReport;




@Service
public final class BookTypeService {
    private final BookTypeRepository repository;
    private PlatformTransactionManager transactionManager;
    private final DataSource dataSource;
    @Autowired(required = true)
    public BookTypeService(final BookTypeRepository repository, final PlatformTransactionManager transactionManager, final DataSource dataSource) {
        this.repository = repository;
        this.transactionManager = transactionManager;
        this.dataSource= dataSource;
    }

    public List<BookTypeImageData> getAllBookTypes() {
        List<BookTypeImageData> result = new ArrayList<>();

        if (this.repository != null) {
            for (final var bookTypeImagePath : this.repository.findAll()) { 
                if(bookTypeImagePath.getEnable()) {
                result.add(new BookTypeImageData(bookTypeImagePath));
                }
            }
        }

        return result;
    }

    private BookTypeImagePath getBookTypeImagePathById(final Short id) {
        BookTypeImagePath result = null;

        if ((this.repository != null) && (id != null)) {
            result = this.repository.findById(id).orElse(null);
        }

        return result;
    }

    public BookTypeImageData getBookTypeImageById(final Short id) {
        BookTypeImageData result = null;

        final var bookTypeImagePath = this.getBookTypeImagePathById(id);
        if (bookTypeImagePath != null) {
            result = new BookTypeImageData(bookTypeImagePath);
        }

        return result;
    }

    public boolean isInValidState() {
        return (this.repository != null) && (this.transactionManager != null)
                && (ResourceStrings.DIR_BOOK_TYPE_IMAGE != null);
    }

    public boolean createBookType(final BookTypeCreationRequest request) {
        boolean result = false;

        final String newBookTypeName;
        if ((request != null) && this.isInValidState()) {
            newBookTypeName = request.getName();
        } else {
            newBookTypeName = null;
        }

        final String newImagePath;
        if (newBookTypeName != null) {
            newImagePath = String.format("%s/%s", ResourceStrings.DIR_BOOK_TYPE_IMAGE, request.getName());
        } else {
            newImagePath = null;
        }

        final File newImageFile;
        if (newImagePath != null) {
            newImageFile = new File(newImagePath);
        } else {
            newImageFile = null;
        }

        final byte[] imageData;
        if ((newImageFile != null) && Boolean.FALSE.equals(newImageFile.isExisted())) {
            imageData = request.getImageData();
        } else {
            imageData = null;
        }

        final TransactionStatus transactionStatus;
        if (imageData != null) {
            final var defaultTransactionDefinition = new DefaultTransactionDefinition();
            defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
        } else {
            transactionStatus = null;
        }

        if (transactionStatus != null) {
            this.repository.save(new BookTypeImagePath(newBookTypeName, newImagePath));
            if (newImageFile.createAndWrite(imageData)) {
                this.transactionManager.commit(transactionStatus);
                result = true;
            } else {
                newImageFile.delete();
                this.transactionManager.rollback(transactionStatus);
            }
        }

        return result;
    }

    public boolean updateBookType(final BookTypeUpdateRequest request) {
        boolean result = false;

        final Short bookTypeImagePathId;
        if ((request != null) && this.isInValidState()) {
            bookTypeImagePathId = request.getId();
        } else {
            bookTypeImagePathId = null;
        }

        final BookTypeImagePath bookTypeImagePath;
        if (bookTypeImagePathId != null) {
            bookTypeImagePath = this.repository.findById(bookTypeImagePathId).orElse(null);
        } else {
            bookTypeImagePath = null;
        }

        final TransactionStatus transactionStatus;
        if (bookTypeImagePath != null) {
            final var defaultTransactionDefinition = new DefaultTransactionDefinition();
            defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            transactionStatus = this.transactionManager.getTransaction(defaultTransactionDefinition);
        } else {
            transactionStatus = null;
        }

        final String bookTypeImagePathName;
        if (transactionStatus != null) {
            final var oldName = bookTypeImagePath.getName();
            final var newName = request.getName();

            if ((newName != null) && (!newName.equals(oldName))) {
                bookTypeImagePath.setName(newName);
            }

            bookTypeImagePathName = bookTypeImagePath.getName();
        } else {
            bookTypeImagePathName = null;
        }

        final String oldImagePath;
        final String newImagePath;
        if (bookTypeImagePathName != null) {
            oldImagePath = bookTypeImagePath.getImagePath();
            newImagePath = String.format("%s/%s", ResourceStrings.DIR_BOOK_TYPE_IMAGE, bookTypeImagePathName);
        } else {
            oldImagePath = null;
            newImagePath = null;
        }

        final File oldImageFile;
        final File newImageFile;
        if (newImagePath != null) {
            final var tempOldImageFile = new File(oldImagePath);
            final var tempNewImageFile = new File(newImagePath);
            final boolean samePath = newImagePath.equals(oldImagePath);

            if ((!samePath) && Boolean.TRUE.equals(tempNewImageFile.isExisted())) {
                oldImageFile = null;
                newImageFile = null;
            } else if (samePath && Boolean.TRUE.equals(tempOldImageFile.isExisted())) {
                oldImageFile = tempOldImageFile;
                newImageFile = null;
            } else if (Boolean.FALSE.equals(tempOldImageFile.isExisted())) {
                oldImageFile = null;
                newImageFile = tempNewImageFile;
            } else {
                oldImageFile = tempOldImageFile;
                newImageFile = tempNewImageFile;
            }
        } else {
            oldImageFile = null;
            newImageFile = null;
        }

        if (newImageFile != null) {
            bookTypeImagePath.setImagePath(newImagePath);
        }

        if (transactionStatus != null) {
            this.repository.save(bookTypeImagePath);

            final boolean fileCorrected;
            if ((newImageFile != null) && (oldImageFile != null)) {
                fileCorrected = oldImageFile.renameTo(newImageFile);
            } else if (newImageFile != null) {
                fileCorrected = newImageFile.create();
            } else if (oldImageFile != null) {
                fileCorrected = true;
            } else {
                fileCorrected = false;
            }

            if (fileCorrected) {
                final var imageData = request.getImageData();

                if (imageData == null) {
                    result = true;
                } else if (newImageFile != null) {
                    result = newImageFile.write(imageData);
                } else if (oldImageFile != null) {
                    result = oldImageFile.write(imageData);
                } else {
                    // Do nothing...
                }
            }

            if (result) {
                this.transactionManager.commit(transactionStatus);
            } else {
                this.transactionManager.rollback(transactionStatus);

                if (fileCorrected && newImageFile != null) {
                    if (oldImageFile != null) {
                        newImageFile.renameTo(oldImageFile);
                    } else {
                        newImageFile.delete();
                    }
                }
            }
        }

        return result;
    } 

    public ResponseEntity<BookTypeImageData> findByName(String name)
    { 
        return new ResponseEntity<>(new BookTypeImageData(repository.findByName(name)), HttpStatus.OK);
    } 
     public byte[] exportReportBorrowingByDateRange(final Date startDate, final Date endDate) throws Exception {
        final HashMap<String, Object> parameters = HashMap.newHashMap(2);
        parameters.put("ReportStartDate", startDate);
        parameters.put("ReportEndDate", endDate);

        return JasperReport.exportReportFromJrxmlFile("E:\\New Library\\librarymanagement\\src\\main\\resources\\reports\\BookTypeBorrowing.jrxml", parameters, dataSource);
    }

}
