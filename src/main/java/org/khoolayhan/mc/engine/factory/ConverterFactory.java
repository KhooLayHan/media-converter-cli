package org.khoolayhan.mc.engine.factory;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.engine.exceptions.UnsupportedFormatException;
import org.khoolayhan.mc.utils.FileType;

/**
 * Defines the contract for creating conversion strategies. Concrete implementations (JsonFactory,
 * CsvFactory) will decide which specific Strategy class to instantiate based on the target format.
 */
public interface ConverterFactory {
    /**
     * Creates a conversion strategy for the specified target format.
     *
     * @param targetFormat The extension of the output file (e.g., "csv", "xml").
     * @return The appropriate ConversionStrategy.
     * @throws UnsupportedFormatException if the target format is not supported.
     */
    ConversionStrategy createStrategy(FileType targetFormat) throws UnsupportedFormatException;
}
