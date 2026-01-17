package org.khoolayhan.mc.converter.impls;

import java.io.File;
import java.util.List;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.converter.facade.JacksonFacade;
import org.khoolayhan.mc.converter.models.User;
import org.khoolayhan.mc.engine.exceptions.ConversionException;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.MappingIterator;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.dataformat.csv.CsvMapper;
import tools.jackson.dataformat.csv.CsvSchema;

public class CsvToJsonStrategy implements ConversionStrategy {
    private final JacksonFacade jacksonFacade;

	public CsvToJsonStrategy(JacksonFacade jacksonFacade) {
		this.jacksonFacade = jacksonFacade;
	}

	@Override
    public void convert(File inputFile, File outputFile) throws ConversionException {
        try {
            List<User> users = jacksonFacade.readCsv(inputFile, User.class);
			jacksonFacade.writeCsv(outputFile, users);
        } catch (JacksonException e) {
            throw new ConversionException("Failed to convert CSV to JSON", e);
        }
    }
}
