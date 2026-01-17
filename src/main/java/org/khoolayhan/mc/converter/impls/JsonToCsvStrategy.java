package org.khoolayhan.mc.converter.impls;

import java.io.File;
import java.util.List;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.converter.facade.JacksonFacade;
import org.khoolayhan.mc.converter.models.User;
import org.khoolayhan.mc.engine.exceptions.ConversionException;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;
import tools.jackson.databind.type.CollectionType;
import tools.jackson.dataformat.csv.CsvMapper;
import tools.jackson.dataformat.csv.CsvSchema;

/**
 * Strategy implementation for converting JSON files to CSV format. Uses Jackson libraries for
 * deserialization and CSV writing.
 */
public class JsonToCsvStrategy implements ConversionStrategy {
    private final JacksonFacade jacksonFacade;

	public JsonToCsvStrategy(JacksonFacade facade) {
		this.jacksonFacade = facade;
	}

	@Override
    public void convert(File inputFile, File outputFile) throws ConversionException {
        try {
			List<User> users = jacksonFacade.readJson(inputFile, User.class);
			jacksonFacade.writeJson(outputFile, users);
        } catch (JacksonException e) {
            throw new ConversionException("Failed to convert JSON to CSV", e);
        }
    }
}
