package com.example.csvtojson.service.impl;

import com.example.csvtojson.service.ConfigProvider;
import com.example.csvtojson.service.CsvFileHandler;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ApplicationScoped
public class CsvFileHandlerImpl implements CsvFileHandler {

    @Inject
    private ConfigProvider configProvider;

    public void onStartUp(@Observes @Priority(Interceptor.Priority.PLATFORM_BEFORE + 1)
                          @Initialized(ApplicationScoped.class) Object obj) throws Exception {
        System.out.println("CsvFileHandlerImpl Container started with " + obj);
    }


    @Override
    public void handleFile(Path filename) {

        System.out.println("Handing file " + filename);
        String filenameAsString = filename.toString().toLowerCase();
        if (!filenameAsString.endsWith(".csv")) {
            System.out.println(filenameAsString + " is not a csv file.");
            return;
        }

        Path fullPath = Paths.get(configProvider.getSourceDir().toString(), filename.toString());
        try {
            Files.delete(fullPath);
        } catch (IOException ex) {
            System.out.println("Unable to delete " + fullPath + " : " + ex);
        }

    }
}
