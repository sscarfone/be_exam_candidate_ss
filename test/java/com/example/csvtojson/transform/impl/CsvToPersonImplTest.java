package com.example.csvtojson.transform.impl;

import org.apache.commons.csv.CSVRecord;
import org.jboss.weld.junit4.WeldInitiator;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Rule;
import org.junit.Test;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.interceptor.Interceptor;

import static com.example.csvtojson.model.Person.HeaderFields.*;
import static org.junit.Assert.assertNotNull;

public class CsvToPersonImplTest {

    private final Mockery context = new Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};


    @Rule
    public final WeldInitiator weld = WeldInitiator.
            from(CsvRecordToPersonImpl.class, CsvToPersonImplTest.class).
            build();

    public void onStartUp(@Observes @Priority(Interceptor.Priority.APPLICATION - 400)
                          @Initialized(ApplicationScoped.class) Object obj) throws Exception{
        System.out.println("CsvToPersonImplTest Container started with "+obj);
    }

    @Test
    public void testInjection() throws Exception {
        assertNotNull(weld.select(CsvRecordToPersonImpl.class).get());
    }

    final private String LOCAL_FIRST_NAME = "First Name";
    final private String LOCAL_LAST_NAME = "Last Name";
    final private String LOCAL_MIDDLE_NAME = "Middle Name";
    final private String LOCAL_PHONE_NUM = "123-345-7890";
    final private String LOCAL_INTERNAL_ID = "1";


    //@Test Final class cannot be mocked...
    public void testToPerson() throws Exception {
        final CSVRecord csvRecord = context.mock(CSVRecord.class);
        context.checking(new Expectations() {{
            allowing(csvRecord).get(FIRST_NAME);
            will(returnValue(LOCAL_FIRST_NAME));

            allowing(csvRecord).get(LAST_NAME);
            will(returnValue(LOCAL_LAST_NAME));

            allowing(csvRecord).get(MIDDLE_NAME);
            will(returnValue(LOCAL_MIDDLE_NAME));

            allowing(csvRecord).get(PHONE_NUM);
            will(returnValue(LOCAL_PHONE_NUM));

            allowing(csvRecord).get(INTERNAL_ID);
            will(returnValue(LOCAL_INTERNAL_ID));



        }});
        weld.select(CsvRecordToPersonImpl.class).get().toPerson(csvRecord);

        context.assertIsSatisfied();
    }

}

