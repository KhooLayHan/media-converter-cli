package org.khoolayhan.mc.converter.impls;

import java.io.File;
import java.util.List;

import org.khoolayhan.mc.converter.models.User;

/**
 * Strategy implementation for converting JSON files to CSV format. Uses Jackson libraries for
 * deserialization and CSV writing.
 */
public class JsonToCsvStrategy extends AbstractConversionStrategy {
    @Override
    protected void convertInternal(File inputFile, File outputFile) {
        List<User> users = jacksonFacade.readJson(inputFile, User.class);
        jacksonFacade.writeCsv(outputFile, users);
    }
}
