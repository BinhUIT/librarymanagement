package com.library.librarymanagement.resource;

import org.springframework.stereotype.Component;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.FieldNameConstants.Include;

@Component
@FieldNameConstants
public final class ResourceStrings {
    @Include
    public static final String DIR_BOOK_TITLE_IMAGE;

    @Include
    public static final String DIR_BOOK_TYPE_IMAGE;

    private static final Dotenv dotenv = Dotenv.configure() .directory("E:\\New Library\\librarymanagement\\.env") .load();

    static {
        DIR_BOOK_TITLE_IMAGE = dotenv.get(ResourceStrings.Fields.DIR_BOOK_TITLE_IMAGE);
        DIR_BOOK_TYPE_IMAGE = dotenv.get(ResourceStrings.Fields.DIR_BOOK_TYPE_IMAGE);
    }

    private ResourceStrings() {
    }
}
