package org.khoolayhan.mc.cli;

import org.slf4j.Logger;

/**
 * Handles all user-facing messages separately from internal logging.
 * Centralizes output formatting and provides a single point for UI changes.
 */
public class UserFeedback {
	private final Logger logger;

	public UserFeedback(Logger logger) {
		this.logger = logger;
	}

	public void showProgress(String message) {
		System.out.println(message);
		logger.debug("Progress: {}", message);
	}

	public void showSuccess(String message) {
		System.out.println("Success! " + message);
		logger.info("Operation completed successfully: {}", message);
	}

	public void showError(String message) {
		System.err.println("Error: " + message);
		logger.error("User-facing error displayed: {}", message);
	}

	public void showError(String message, String hint) {
		// User-facing output
		System.err.println("Error: " + message + " – " + hint);

		// Internal logging
		logger.error("Error occurred: {} | Hint provided: {}", message, hint);
	}

	public void showWarning(String message) {
		System.err.println("Warning: " + message);
		logger.warn("User warning: {}", message);
	}

	public void showInfo(String message) {
		System.out.println(message);
		logger.debug("User info: {}", message);
	}

	public void showPlain(String message) {
		System.out.println(message);
		// No logging for cosmetic output like banners
	}

	public void showDetailedError(String userMessage, Exception exception) {
		System.err.println("Error: " + userMessage);
		System.err.println("\tDetails: " + exception.getMessage());
		System.err.println("\t(Check the logs for full technical details)");

		// Internal logging - full details with stack trace
		logger.error("Detailed error: {} | Exception: {}",
				userMessage, exception.getClass().getName(), exception);
	}

	public void logTechnicalDetails(String message, Object... args) {
		logger.debug(message, args);
	}

	public void logPerformance(String operation, long durationMs) {
		logger.info("Performance: {} completed in {}ms", operation, durationMs);
	}
}
