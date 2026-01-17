package org.khoolayhan.mc.engine.factory;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.converter.impls.XmlToCsvStrategy;
import org.khoolayhan.mc.converter.impls.XmlToJsonStrategy;
import org.khoolayhan.mc.engine.exceptions.UnsupportedFormatException;
import org.khoolayhan.mc.utils.FileType;

public class XmlFactory implements ConverterFactory {
    @Override
    public ConversionStrategy createStrategy(FileType targetFormat)
            throws UnsupportedFormatException {
        if (targetFormat == null) {
            throw new UnsupportedFormatException("Target format cannot be null");
        }

        return switch (targetFormat) {
            case JSON -> new XmlToJsonStrategy();
            case CSV -> new XmlToCsvStrategy();
            default ->
                    throw new UnsupportedFormatException(
                            "Conversion from XML to "
                                    + targetFormat.getExtension()
                                    + " is not supported");
        };
    }
}
