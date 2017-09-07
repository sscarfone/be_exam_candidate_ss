package com.example.csvtojson.transform.impl;

import com.example.csvtojson.model.CsvRecord;
import com.example.csvtojson.model.Person;
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

import static org.junit.Assert.assertEquals;
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
    final private long RECORD_NUMBER = 2;


    @Test
    public void testToPerson() throws Exception {
        final CsvRecord csvRecord = context.mock(CsvRecord.class);
        context.checking(new Expectations() {{
            allowing(csvRecord).getFirstName();
            will(returnValue(LOCAL_FIRST_NAME));

            allowing(csvRecord).getLastName();
            will(returnValue(LOCAL_LAST_NAME));

            allowing(csvRecord).getMiddleName();
            will(returnValue(LOCAL_MIDDLE_NAME));

            allowing(csvRecord).getPhoneNum();
            will(returnValue(LOCAL_PHONE_NUM));

            allowing(csvRecord).getInternalId();
            will(returnValue(LOCAL_INTERNAL_ID));

            allowing(csvRecord).getRecordNumber();
            will(returnValue(RECORD_NUMBER));


        }});
        Person person = weld.select(CsvRecordToPersonImpl.class).get().toPerson(csvRecord);
        assertEquals(LOCAL_FIRST_NAME, person.getFirstName());
        assertEquals(LOCAL_LAST_NAME, person.getLastName());
        assertEquals(LOCAL_MIDDLE_NAME, person.getMiddleName());
        assertEquals(LOCAL_PHONE_NUM, person.getPhoneNum());
        assertEquals(LOCAL_INTERNAL_ID, person.getInternalId());


        context.assertIsSatisfied();
    }

}

