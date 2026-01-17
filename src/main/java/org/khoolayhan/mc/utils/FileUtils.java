package org.khoolayhan.mc.utils;

import java.io.File;
import java.util.Locale;
import java.util.Optional;

public final class FileUtils {
    private FileUtils() {
        throw new UnsupportedOperationException(
                "FileUtils is a utility class and should not be instantiated.");
    }

    /**
     * Gets the file extension from a file name.
     *
     * @param file The file.
     * @return An Optional containing the FileType, or empty if no valid extension exists.
     */
    public static Optional<FileType> getExtension(File file) {
        String name = file.getName();

        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf <= 0 || lastIndexOf == name.length() - 1) {
            return Optional.empty();
        }

        String extension = name.substring(lastIndexOf + 1).toLowerCase(Locale.ROOT);

        try {
            return Optional.of(FileType.fromExtension(extension));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
