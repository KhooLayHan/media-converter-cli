package org.khoolayhan.mc.converter.facade;

import java.io.File;
import java.util.List;

import tools.jackson.databind.MappingIterator;
import tools.jackson.databind.ObjectWriter;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.type.CollectionType;
import tools.jackson.dataformat.csv.CsvMapper;
import tools.jackson.dataformat.csv.CsvSchema;
import tools.jackson.dataformat.xml.XmlMapper;

/**
 * This class hides the complexity of the Jackson library (ObjectMappers, Schemas, CollectionTypes,
 * etc.) behind a clean, simple API.
 */
public class JacksonFacade {
    private final JsonMapper jsonMapper;
    private final XmlMapper xmlMapper;
    private final CsvMapper csvMapper;

    public JacksonFacade() {
        this.jsonMapper =
                JsonMapper.builder()
                        .enable(SerializationFeature.INDENT_OUTPUT)
                        .build(); // Pretty print by default

        this.xmlMapper = XmlMapper.builder().enable(SerializationFeature.INDENT_OUTPUT).build();

        this.csvMapper = CsvMapper.builder().build();
    }

    // --- JSON Operations ---
    public <T> List<T> readJson(File source, Class<T> type) {
        CollectionType listType =
                jsonMapper.getTypeFactory().constructCollectionType(List.class, type);
        return jsonMapper.readValue(source, listType);
    }

    public <T> void writeJson(File destination, List<T> data) {
        jsonMapper.writeValue(destination, data);
    }

    // --- XML Operations ---
    public <T> List<T> readXml(File source, Class<T> type) {
        CollectionType listType =
                xmlMapper.getTypeFactory().constructCollectionType(List.class, type);
        return xmlMapper.readValue(source, listType);
    }

    public <T> void writeXml(File destination, List<T> data) {
        xmlMapper.writeValue(destination, data);
    }

    // --- CSV Operations ---
    public <T> List<T> readCsv(File source, Class<T> type) {
        CsvSchema csvSchema = csvMapper.schemaFor(type).withHeader();

        try (MappingIterator<T> iterator =
                csvMapper.readerFor(type).with(csvSchema).readValues(source)) {
            return iterator.readAll();
        }
    }

    public <T> void writeCsv(File destination, List<T> data) {
        if (data == null || data.isEmpty()) {
            return;
        }

        // Creates schema based on the User POJO class
        CsvSchema csvSchema = csvMapper.schemaFor(data.getFirst().getClass()).withHeader();
        ObjectWriter writer = csvMapper.writer(csvSchema);
        writer.writeValue(destination, data);
    }
}
