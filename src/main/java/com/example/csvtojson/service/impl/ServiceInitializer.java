package com.example.csvtojson.service.impl;


import com.example.csvtojson.service.CsvFileWatcher;
import com.example.csvtojson.transform.CsvRecordToPerson;
import com.example.csvtojson.validator.PersonValidator;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.interceptor.Interceptor;

@ApplicationScoped
public class ServiceInitializer {

    @Inject
    private PersonValidator personValidator;

    @Inject
    private CsvRecordToPerson csvRecordToPerson;

    @Inject
    private CsvFileWatcher watchService;

    public void onStartUp(@Observes @Priority(Interceptor.Priority.APPLICATION - 400)
                          @Initialized(ApplicationScoped.class) Object obj) throws Exception{
        System.out.println("ServiceInitializer Container started with "+obj);

        watchService.watchLoop();
    }
}
