package org.khoolayhan.mc.legacy;

import java.io.File;
import java.util.List;
import java.util.Locale;

import org.khoolayhan.mc.converter.models.User;

import tools.jackson.databind.MappingIterator;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.type.CollectionType;
import tools.jackson.dataformat.csv.CsvMapper;
import tools.jackson.dataformat.csv.CsvSchema;
import tools.jackson.dataformat.xml.XmlMapper;

/**
 * THE "SIMPLER" SOLUTION (No Design Patterns).
 *
 * <p>Core Characteristics: 1. High Coupling: Direct dependencies on Jackson classes inside the main
 * logic. 2. High Complexity: Nested if-else statements (High Cyclomatic Complexity). 3. Low
 * Cohesion: One class handles args parsing, file checking, and conversion logic. 4. Violation of
 * OCP: To add a new format, you must modify this file.
 */
@SuppressWarnings("CatchAndPrintStackTrace")
public final class LegacyMediaConverter {
    private LegacyMediaConverter() {
        // Utility class
    }

    public static void main(String[] args) {
        // Rudimentary Argument Parsing (No Picocli)
        String inputFileParam = null;
        String outputFileParam = null;

        for (int i = 0; i < args.length; i++) {
            if ("-i".equals(args[i]) && i + 1 < args.length) {
                inputFileParam = args[i + 1];
            } else if ("-o".equals(args[i]) && i + 1 < args.length) {
                outputFileParam = args[i + 1];
            }
        }

        if (inputFileParam == null || outputFileParam == null) {
            System.err.println(
                    "Usage: java org.khoolayhan.mc.legacy.LegacyMediaConverter -i <input> -o <output>");
            return;
        }

        File inputFile = new File(inputFileParam);
        File outputFile = new File(outputFileParam);

        if (!inputFile.exists()) {
            System.err.println("Input file not found.");
            return;
        }

        // Determine Extensions (No FileUtils, No Type Safety)
        String inName = inputFile.getName().toLowerCase(Locale.ROOT);
        String outName = outputFile.getName().toLowerCase(Locale.ROOT);
        String inExt = inName.substring(inName.lastIndexOf(".") + 1);
        String outExt = outName.substring(outName.lastIndexOf(".") + 1);

        System.out.println("Legacy Converter: " + inExt + " -> " + outExt);

        try {
            // THE GIANT IF-ELSE BLOCK (The "Anti-Pattern")

            // --- Json Input ---
            if ("json".equals(inExt)) {
                // Read JSON
                JsonMapper jsonMapper =
                        JsonMapper.builder().enable(SerializationFeature.INDENT_OUTPUT).build();
                CollectionType listType =
                        jsonMapper.getTypeFactory().constructCollectionType(List.class, User.class);
                List<User> users = jsonMapper.readValue(inputFile, listType);

                if ("csv".equals(outExt)) {
                    // Write CSV
                    CsvMapper csvMapper = CsvMapper.builder().build();
                    CsvSchema schema = csvMapper.schemaFor(User.class).withHeader();
                    csvMapper.writer(schema).writeValue(outputFile, users);
                    System.out.println("Success: JSON to CSV");

                } else if ("xml".equals(outExt)) {
                    // Write XML
                    XmlMapper xmlMapper =
                            XmlMapper.builder().enable(SerializationFeature.INDENT_OUTPUT).build();
                    xmlMapper.writeValue(outputFile, users);
                    System.out.println("Success: JSON to XML");

                } else {
                    System.err.println("Unsupported output format for JSON");
                }
                // --- Csv Input ---
            } else if ("csv".equals(inExt)) {
                // Read CSV
                CsvMapper csvMapper = CsvMapper.builder().build();
                CsvSchema schema = csvMapper.schemaFor(User.class).withHeader();

                try (MappingIterator<User> it =
                        csvMapper.readerFor(User.class).with(schema).readValues(inputFile)) {
                    List<User> users = it.readAll();

                    if ("json".equals(outExt)) {
                        // Write JSON
                        JsonMapper jsonMapper =
                                JsonMapper.builder()
                                        .enable(SerializationFeature.INDENT_OUTPUT)
                                        .build();
                        jsonMapper.writeValue(outputFile, users);
                        System.out.println("Success: CSV to JSON");

                    } else if ("xml".equals(outExt)) {
                        // Write XML
                        XmlMapper xmlMapper =
                                XmlMapper.builder()
                                        .enable(SerializationFeature.INDENT_OUTPUT)
                                        .build();
                        xmlMapper.writeValue(outputFile, users);
                        System.out.println("Success: CSV to XML");

                    } else {
                        System.err.println("Unsupported output format for CSV");
                    }
                }
                // --- Xml Input ---
            } else if ("xml".equals(inExt)) {
                // Read XML
                XmlMapper xmlMapper =
                        XmlMapper.builder().enable(SerializationFeature.INDENT_OUTPUT).build();
                CollectionType listType =
                        xmlMapper.getTypeFactory().constructCollectionType(List.class, User.class);
                List<User> users = xmlMapper.readValue(inputFile, listType);

                if ("json".equals(outExt)) {
                    // Write JSON
                    JsonMapper jsonMapper =
                            JsonMapper.builder().enable(SerializationFeature.INDENT_OUTPUT).build();
                    jsonMapper.writeValue(outputFile, users);
                    System.out.println("Success: XML to JSON");

                } else if ("csv".equals(outExt)) {
                    // Write CSV
                    CsvMapper csvMapper = CsvMapper.builder().build();
                    CsvSchema schema = csvMapper.schemaFor(User.class).withHeader();
                    csvMapper.writer(schema).writeValue(outputFile, users);
                    System.out.println("Success: XML to CSV");

                } else {
                    System.err.println("Unsupported output format for XML");
                }

            } else {
                System.err.println("Unsupported input format: " + inExt);
            }
        } catch (Exception e) {
            // Poor error handling
            e.printStackTrace();
        }
    }
}
