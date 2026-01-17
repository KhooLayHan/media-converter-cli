package org.khoolayhan.mc.engine.factory;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.converter.impls.JsonToCsvStrategy;
import org.khoolayhan.mc.converter.impls.JsonToXmlStrategy;
import org.khoolayhan.mc.engine.exceptions.UnsupportedFormatException;
import org.khoolayhan.mc.utils.FileType;

public class JsonFactory implements ConverterFactory {
    @Override
    public ConversionStrategy createStrategy(FileType targetFormat)
            throws UnsupportedFormatException {
        if (targetFormat == null) {
            throw new UnsupportedFormatException("Target format cannot be null");
        }

        return switch (targetFormat) {
            case FileType.CSV -> new JsonToCsvStrategy();
            case FileType.XML -> new JsonToXmlStrategy();
            default ->
                    throw new UnsupportedFormatException(
                            "Conversion from JSON to "
                                    + targetFormat.getExtension()
                                    + " is not supported");
        };
    }
}
