package org.khoolayhan.mc.engine.factory;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.converter.impls.JsonToCsvStrategy;
import org.khoolayhan.mc.engine.exceptions.UnsupportedFormatException;

public final class StrategyFactory {
	private StrategyFactory() {}

	/**
	 * Selects and returns the correct conversion strategy.
	 *
	 * @param fromExtension The source file extension (e.g., "json").
	 * @param toExtension   The target file extension (e.g., "csv").
	 * @return An instance of the required ConversionStrategy.
	 * @throws UnsupportedFormatException if the requested conversion is not supported.
	 */
	public static ConversionStrategy getStrategy(String fromExtension, String toExtension) throws UnsupportedFormatException {
		if ("json".equalsIgnoreCase(fromExtension) && "csv".equalsIgnoreCase(toExtension))
			return new JsonToCsvStrategy();

		// TODO: Add more strategy mappings here.

		// If none is found, throw our custom exception
		throw new UnsupportedFormatException(
			String.format("Conversion from '%s' to '%s' is not supported.", fromExtension, toExtension));
	}
}
