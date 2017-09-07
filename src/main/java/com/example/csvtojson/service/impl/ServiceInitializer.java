package com.example.csvtojson.service.impl;


import com.example.csvtojson.service.CsvFileWatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.interceptor.Interceptor;

@ApplicationScoped
public class ServiceInitializer {

    private static final Logger LOGGER = LogManager.getLogger();

    @Inject
    private CsvFileWatcher watchService;

    public void onStartUp(@Observes @Priority(Interceptor.Priority.APPLICATION - 400)
                          @Initialized(ApplicationScoped.class) Object obj) throws Exception{
        LOGGER.info("ServiceInitializer Container started with " + obj);

        watchService.watchLoop();
    }
}
