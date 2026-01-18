package org.khoolayhan.mc.converter.impls;

import java.io.File;
import java.util.List;

import org.khoolayhan.mc.converter.models.User;

public class CsvToJsonStrategy extends AbstractConversionStrategy {
    @Override
    protected void convertInternal(File inputFile, File outputFile) {
        List<User> users = jacksonFacade.readCsv(inputFile, User.class);
        jacksonFacade.writeJson(outputFile, users);
    }
}
