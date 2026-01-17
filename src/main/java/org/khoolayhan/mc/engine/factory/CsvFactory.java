package org.khoolayhan.mc.engine.factory;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.converter.impls.CsvToJsonStrategy;
import org.khoolayhan.mc.converter.impls.CsvToXmlStrategy;
import org.khoolayhan.mc.converter.impls.JsonToCsvStrategy;
import org.khoolayhan.mc.converter.impls.JsonToXmlStrategy;
import org.khoolayhan.mc.engine.exceptions.UnsupportedFormatException;
import org.khoolayhan.mc.engine.factory.ConverterFactory;

public class CsvFactory implements ConverterFactory {
	@Override
	public ConversionStrategy createStrategy(String targetFormat) throws UnsupportedFormatException {
		return switch (targetFormat.toLowerCase()) {
			case "json" -> new CsvToJsonStrategy();
			case "xml" -> new CsvToXmlStrategy();
			default -> throw new UnsupportedFormatException("Conversion from CSV to " + targetFormat + " is not supported");
		};
	}
}
