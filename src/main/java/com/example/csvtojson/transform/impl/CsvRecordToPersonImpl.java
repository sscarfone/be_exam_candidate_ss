package com.example.csvtojson.transform.impl;

import com.example.csvtojson.model.Person;
import com.example.csvtojson.transform.CsvRecordToPerson;
import org.apache.commons.csv.CSVRecord;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.interceptor.Interceptor;

public class CsvRecordToPersonImpl implements CsvRecordToPerson {


    public void onStartUp(@Observes @Priority(Interceptor.Priority.APPLICATION - 400)
                          @Initialized(ApplicationScoped.class) Object obj) throws Exception{
        System.out.println("CsvRecordToPersonImpl Container started with "+obj);
    }


    @Override
    public Person toPerson(CSVRecord csvRecord) {

        String internalId = csvRecord.get(Person.HeaderFields.INTERNAL_ID);

        String firstName = csvRecord.get(Person.HeaderFields.FIRST_NAME);

        String middleName = csvRecord.get(Person.HeaderFields.MIDDLE_NAME);

        String lastName = csvRecord.get(Person.HeaderFields.LAST_NAME);

        String phoneNum = csvRecord.get(Person.HeaderFields.PHONE_NUM);

        long recordNumber = csvRecord.getRecordNumber();

        return new Person(internalId, firstName, middleName, lastName, phoneNum, recordNumber);

    }
}
