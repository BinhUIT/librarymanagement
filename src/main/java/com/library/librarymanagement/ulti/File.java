package com.library.librarymanagement.ulti;

import java.io.FileOutputStream;
import java.nio.file.Files;

import jakarta.annotation.Nullable;

public class File {
    private java.io.File core = null;

    public File(final String path) {
        if (path != null) {
            this.core = new java.io.File(path);
        } else {
            this.core = null;
        }
    }

    public boolean create() {
        try {
            return this.core.createNewFile();
        } catch (final Throwable throwable) {
            return false;
        }
    }

    @Nullable
    public Boolean isExisted() {
        try {
            return this.core.exists();
        } catch (final Throwable throwable) {
            return null;
        }
    }

    @Nullable
    public Boolean isModifiable() {
        try {
            return this.core.canWrite();
        } catch (final Throwable throwable) {
            return null;
        }
    }

    public boolean delete() {
        try {
            return this.core.delete();
        } catch (final Throwable throwable) {
            return false;
        }
    }

    public boolean write(final byte[] bytes) {
        try (FileOutputStream stream = new FileOutputStream(this.core)) {
            stream.write(bytes);
            return true;
        } catch (final Throwable throwable) {
            return false;
        }
    }

    public boolean createAndWrite(final byte[] bytes) {
        boolean result = false;

        if (!this.create()) {
            // Do nothing...
        } else if (!this.write(bytes)) {
            this.delete();
        } else {
            result = true;
        }

        return result;
    }

    public boolean createAndWriteOverride(final byte[] bytes) {
        this.create();
        return this.write(bytes);
    }

    public byte[] readBytes() {
        try {
            byte[] result = null;

            if (this.core != null) {
                result = Files.readAllBytes(this.core.toPath());
            }

            return result;
        } catch (final Throwable throwable) {
            return null;
        }
    }
}
