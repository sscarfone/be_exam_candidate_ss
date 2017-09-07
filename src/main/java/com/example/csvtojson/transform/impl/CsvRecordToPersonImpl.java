package com.example.csvtojson.transform.impl;

import com.example.csvtojson.model.CsvRecord;
import com.example.csvtojson.model.Person;
import com.example.csvtojson.transform.CsvRecordToPerson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.interceptor.Interceptor;

public class CsvRecordToPersonImpl implements CsvRecordToPerson {

    private static final Logger LOGGER = LogManager.getLogger();

    public void onStartUp(@Observes @Priority(Interceptor.Priority.APPLICATION - 400)
                          @Initialized(ApplicationScoped.class) Object obj) throws Exception{
        LOGGER.info("CsvRecordToPersonImpl Container started with " + obj);
    }


    @Override
    public Person toPerson(CsvRecord csvRecord) {

        String internalId = csvRecord.getInternalId();

        String firstName = csvRecord.getFirstName();

        String middleName = csvRecord.getMiddleName();

        String lastName = csvRecord.getLastName();

        String phoneNum = csvRecord.getPhoneNum();

        long recordNumber = csvRecord.getRecordNumber();

        return new Person(internalId, firstName, middleName, lastName, phoneNum, recordNumber);
    }
}
