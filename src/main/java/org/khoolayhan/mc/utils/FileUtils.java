package org.khoolayhan.mc.utils;

import java.io.File;
import java.util.Optional;

public final class FileUtils {
	private FileUtils() {
		throw new UnsupportedOperationException("FileUtils is a utility class and should not be instantiated.");
	}

	/**
	 * Gets the file extension from a file name.
	 *
	 * @param file The file.
	 * @return An Optional containing the lowercase extension without the dot, or empty if none exists.
	 */
	public static Optional<String> getExtension(File file) {
		String name = file.getName();

		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return Optional.empty();
		}

		return Optional.of(name.substring(lastIndexOf + 1).toLowerCase());
	}

}
