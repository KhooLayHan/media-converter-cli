package org.khoolayhan.mc.engine.factory;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.converter.impls.CsvToJsonStrategy;
import org.khoolayhan.mc.converter.impls.CsvToXmlStrategy;
import org.khoolayhan.mc.engine.exceptions.UnsupportedFormatException;

public class CsvFactory implements ConverterFactory {
    @Override
    public ConversionStrategy createStrategy(String targetFormat)
            throws UnsupportedFormatException {
        if (targetFormat == null) {
            throw new UnsupportedFormatException("Target format cannot be null");
        }

        return switch (targetFormat.toLowerCase()) {
            case "json" -> new CsvToJsonStrategy();
            case "xml" -> new CsvToXmlStrategy();
            default ->
                    throw new UnsupportedFormatException(
                            "Conversion from CSV to " + targetFormat + " is not supported");
        };
    }
}
