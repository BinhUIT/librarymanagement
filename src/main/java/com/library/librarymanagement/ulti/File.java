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
            final boolean result;

            if (this.core != null) {
                result = this.core.createNewFile();
            } else {
                result = false;
            }

            return result;
        } catch (final Throwable throwable) {
            return false;
        }
    }

    @Nullable
    public Boolean isExisted() {
        try {
            final Boolean result;

            if (this.core != null) {
                result = this.core.exists();
            } else {
                result = null;
            }

            return result;
        } catch (final Throwable throwable) {
            return null;
        }
    }

    @Nullable
    public Boolean isModifiable() {
        try {
            final boolean result;

            if (this.core != null) {
                result = this.core.canWrite();
            } else {
                result = false;
            }

            return result;
        } catch (final Throwable throwable) {
            return null;
        }
    }

    public boolean delete() {
        try {
            final boolean result;

            if (this.core != null) {
                result = this.core.delete();
            } else {
                result = false;
            }

            return result;
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

    public boolean writeIfBytesNotNull(final byte[] bytes) {
        if (bytes != null) {
            return this.write(bytes);
        } else {
            return true;
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

    public boolean renameTo(final File dest) {
        try {
            boolean result = false;

            if ((this.core != null) && (dest.core != null)) {
                result = this.core.renameTo(dest.core);
            }

            return result;
        } catch (final Throwable throwable) {
            return false;
        }
    }

    public byte[] readBytes() {
        try {
            final byte[] result;

            if (this.core != null) {
                result = Files.readAllBytes(this.core.toPath());
            } else {
                result = null;
            }

            return result;
        } catch (final Throwable throwable) {
            return null;
        }
    }
}
