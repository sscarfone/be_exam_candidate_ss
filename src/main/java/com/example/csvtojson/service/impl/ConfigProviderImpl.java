package com.example.csvtojson.service.impl;


import com.example.csvtojson.service.ConfigProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.interceptor.Interceptor;
import java.nio.file.Path;
import java.nio.file.Paths;

@ApplicationScoped

public class ConfigProviderImpl implements ConfigProvider {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String DEFAULT_SOURCE_DIR = "/tmp/source";
    private static final String SOURCE_DIR_PROPERTY = "com.example.csvtojson.sourceDir";

    private static final String DEFAULT_DEST_DIR = "/tmp/dest";
    private static final String DEST_DIR_PROPERTY = "com.example.csvtojson.destDir";

    private static final String DEFAULT_ERROR_DIR = "/tmp/error";
    private static final String ERROR_DIR_PROPERTY = "com.example.csvtojson.errorDir";


    private Path sourceDir;
    private Path destDir;
    private Path errorDir;

    @Override
    public Path getSourceDir() {
        return sourceDir;
    }

    @Override
    public Path getDestDir() {
        return destDir;
    }

    @Override
    public Path getErrorDir() {
        return errorDir;
    }

    private Path setProperty(String propertyName, String defaultValue) {
        final String value = System.getProperty(propertyName, defaultValue);
        System.out.println("Using property " + propertyName + " value " + value + ". Default is " + defaultValue + "." );
        return Paths.get(value);
    }

    public void onStartUp(@Observes @Priority(Interceptor.Priority.PLATFORM_BEFORE)
                          @Initialized(ApplicationScoped.class) Object obj) throws Exception{
        LOGGER.info("ConfigProviderImpl Container started with " + obj);

        sourceDir = setProperty(SOURCE_DIR_PROPERTY, DEFAULT_SOURCE_DIR);
        destDir = setProperty(DEST_DIR_PROPERTY, DEFAULT_DEST_DIR);
        errorDir = setProperty(ERROR_DIR_PROPERTY, DEFAULT_ERROR_DIR);

    }
}
