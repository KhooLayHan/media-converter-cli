package org.khoolayhan.mc.engine.factory;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.converter.impls.CsvToJsonStrategy;
import org.khoolayhan.mc.converter.impls.CsvToXmlStrategy;
import org.khoolayhan.mc.engine.exceptions.UnsupportedFormatException;
import org.khoolayhan.mc.utils.FileType;

public class CsvFactory implements ConverterFactory {
    @Override
    public ConversionStrategy createStrategy(FileType targetFormat)
            throws UnsupportedFormatException {
        if (targetFormat == null) {
            throw new UnsupportedFormatException("Target format cannot be null");
        }

        return switch (targetFormat) {
            case JSON -> new CsvToJsonStrategy();
            case XML -> new CsvToXmlStrategy();
            default ->
                    throw new UnsupportedFormatException(
                            "Conversion from CSV to "
                                    + targetFormat.getExtension()
                                    + " is not supported");
        };
    }
}
