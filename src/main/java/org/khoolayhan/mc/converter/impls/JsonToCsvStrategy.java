package org.khoolayhan.mc.converter.impls;

import java.io.File;
import java.util.List;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.converter.models.User;
import org.khoolayhan.mc.engine.exceptions.ConversionException;

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
    @Override
    public void convert(File inputFile, File outputFile) throws ConversionException {
        // 1. Reads and deserialize JSON into a list of User POJOs
        ObjectMapper jsonMapper = new ObjectMapper();
        CollectionType listType =
                jsonMapper.getTypeFactory().constructCollectionType(List.class, User.class);

        List<User> users = jsonMapper.readValue(inputFile, listType);

        // 2. Write and serialize the list of User POJOs to CSV
        CsvMapper csvMapper = new CsvMapper();

        // Builds a schema from the POJO properties and include a header row
        CsvSchema schema = csvMapper.schemaFor(User.class).withHeader();

        ObjectWriter writer = csvMapper.writer(schema);
        writer.writeValue(outputFile, users);
    }
}
