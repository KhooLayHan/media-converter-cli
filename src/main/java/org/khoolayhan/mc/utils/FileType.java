package org.khoolayhan.mc.utils;

import java.util.Locale;

/** Enum representing supported file types for conversion operations. */
public enum FileType {
    CSV("csv"),
    XML("xml"),
    JSON("json"),
    YAML("yaml"),
    TOML("toml");

    private final String extension;

    FileType(String extension) {
        this.extension = extension;
    }

    /**
     * Converts a file extension string to the corresponding FileType enum.
     *
     * @param extension The file extension (case-insensitive)
     * @return The matching {@link FileType}
     * @throws IllegalArgumentException if the extension is not supported
     */
    public static FileType fromExtension(String extension) {
        if (extension == null || extension.isEmpty()) {
            throw new IllegalArgumentException("Extension cannot be null or empty");
        }

        String normalized = extension.toLowerCase(Locale.ROOT).trim();
        if (normalized.startsWith(".")) {
            normalized = normalized.substring(1);
        }

        for (FileType fileType : FileType.values()) {
            if (fileType.extension.equals(normalized)) {
                return fileType;
            }
        }

        throw new IllegalArgumentException("Unsupported file extension: " + extension);
    }

    /**
     * Checks if the given extension is supported.
     *
     * @param extension The file extension to check
     * @return {@code true} if the extension is supported, false otherwise
     */
    public static boolean isSupported(String extension) {
        try {
            fromExtension(extension);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public String getExtension() {
        return extension;
    }
}
