package org.khoolayhan.mc.engine.factory;

import java.util.HashMap;
import java.util.Map;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.engine.exceptions.UnsupportedFormatException;
import org.khoolayhan.mc.utils.FileType;

/**
 * The entry point for the creation logic. This class acts as a registry for the specific
 * ConverterFactories.
 */
public final class StrategyFactory {
    private static final Map<FileType, ConverterFactory> factoryMap = new HashMap<>();

    static {
        factoryMap.put(FileType.JSON, new JsonFactory());
        factoryMap.put(FileType.CSV, new CsvFactory());
        factoryMap.put(FileType.XML, new XmlFactory());
    }

    private StrategyFactory() {}

    /**
     * Resolves the correct strategy using the Factory Method pattern.
     *
     * @param fromExtension The source file extension (e.g., "json").
     * @param toExtension The target file extension (e.g., "csv").
     * @return An instance of the required ConversionStrategy.
     * @throws UnsupportedFormatException if the requested conversion is not supported.
     */
    public static ConversionStrategy getStrategy(FileType fromExtension, FileType toExtension)
            throws UnsupportedFormatException {

        ConverterFactory factory = factoryMap.get(fromExtension);
        if (factory == null) {
            throw new UnsupportedFormatException(
                    "Input format " + fromExtension + " is not supported");
        }

        return factory.createStrategy(toExtension);
    }
}
