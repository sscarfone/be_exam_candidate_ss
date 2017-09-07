package com.example.csvtojson.service.impl;

import com.example.csvtojson.model.CsvRecord;
import com.example.csvtojson.model.Person;
import com.example.csvtojson.service.ConfigProvider;
import com.example.csvtojson.service.CsvFileHandler;
import com.example.csvtojson.transform.CsvRecordToPerson;
import com.example.csvtojson.validator.IllegalRecordException;
import com.example.csvtojson.validator.PersonValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CsvFileHandlerImpl implements CsvFileHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    @Inject
    private ConfigProvider configProvider;

    @Inject
    private CsvRecordToPerson toPersonTransformer;

    @Inject
    private PersonValidator personValidator;

    private ObjectMapper jsonObjectMapper;

    public void onStartUp(@Observes @Priority(Interceptor.Priority.PLATFORM_BEFORE + 1)
                          @Initialized(ApplicationScoped.class) Object obj) throws Exception {
        LOGGER.info("CsvFileHandlerImpl Container started with " + obj);

        jsonObjectMapper = new ObjectMapper();
        jsonObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }


    @Override
    public void handleFile(Path filename) {

        System.out.println("Handing file " + filename);
        String filenameAsString = filename.toString().toLowerCase();
        if (!filenameAsString.endsWith(".csv")) {
            System.out.println(filenameAsString + " is not a csv file.");
            return;
        }

        Path absolutePath = Paths.get(configProvider.getSourceDir().toString(), filename.toString());

        try (Reader in = new FileReader(absolutePath.toString())) {

            List<CsvRecord> csvRecords = new CsvToBeanBuilder(in).
                    withType(CsvRecord.class).
                    build().
                    parse();

            handleCsvRecords(filename, csvRecords);
        } catch (IOException ex) {
            System.err.println("Error parsing csv file " + absolutePath + "." + ex);
        }

        try {
            Files.delete(absolutePath);
        } catch (IOException ex) {
            System.out.println("Unable to delete " + absolutePath + " : " + ex);
        }

    }

    private void handleCsvRecords(Path filename, List<CsvRecord> csvRecords) {

        List<Person> validRecords = new ArrayList<>();
        List<IllegalRecordException> errorRecords = new ArrayList<>();

        for (CsvRecord csvRecord : csvRecords) {
            Person person = toPersonTransformer.toPerson(csvRecord);
            try {
                personValidator.validate(person);
                validRecords.add(person);
            } catch (IllegalRecordException ex) {
                errorRecords.add(ex);
            }
        }

        handleValidRecords(filename, validRecords);
        handleErrorRecords(filename, errorRecords);
    }

    private void handleErrorRecords(Path filename, List<IllegalRecordException> errorRecords) {

        if (errorRecords.isEmpty()) {
            return;
        }

        Path errorPath = Paths.get(configProvider.getErrorDir().toString(), filename.toString());
        File errorPathAsFile = errorPath.toFile();

        try (FileWriter fileWriter = new FileWriter(errorPathAsFile, false)) {
            StatefulBeanToCsvBuilder beanToCsvBuilder = new StatefulBeanToCsvBuilder(fileWriter);
            StatefulBeanToCsv beanToCsv = beanToCsvBuilder.build();

            for (IllegalRecordException error : errorRecords) {
                try {
                    beanToCsv.write(error);
                } catch (CsvDataTypeMismatchException e) {
                    e.printStackTrace();
                } catch (CsvRequiredFieldEmptyException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            System.err.println("Unable to open error file " + errorPathAsFile + " for writing :" + ex);
        }

    }

    private void handleValidRecords(Path filename, List<Person> validRecords) {

        if (validRecords.isEmpty()) {
            return;
        }

        int fileNameLen = filename.toString().length();
        String jsonRelativeFile = filename.toString().substring(0, fileNameLen - ".csv".length()) + ".json";
        Path jsonPath = Paths.get(configProvider.getDestDir().toString(), jsonRelativeFile);
        File jsonPathAsFile = jsonPath.toFile();

        try (FileWriter fileWriter = new FileWriter(jsonPathAsFile, false)) {
            jsonObjectMapper.writeValue(fileWriter, validRecords);
        } catch (IOException ex) {
            System.err.println("Unable to open json file " + jsonPathAsFile + " for writing :" + ex);
        }

    }
}
