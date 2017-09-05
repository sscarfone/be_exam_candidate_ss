package com.example.csvtojson.validator.impl;

import com.example.csvtojson.model.Person;
import com.example.csvtojson.validator.IllegalRecordException;
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

import static org.junit.Assert.assertNotNull;


public class PersonValidatorTest {

    private static final String EMPTY_NAME = "";
    private final String GOOD_ID = "12345678";
    private final String SHORT_ID = "123";
    private final String LONG_ID = "123456789";
    private final String ALPHA_ID = "A2345678";

    private final String GOOD_NAME = "123456789012345";
    private final String LONG_NAME = "1234567890123456";
    private final String GOOD_PHONE = "123-456-7890";


    private final Mockery context = new Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};




    private final long RECORD_NUMBER = 3;

    @Rule
    public final WeldInitiator weld = WeldInitiator.
            from(PersonValidatorTest.class, PersonValidatorImpl.class).
            build();

    public void onStartUp(@Observes @Priority(Interceptor.Priority.APPLICATION - 400)
                          @Initialized(ApplicationScoped.class) Object obj) throws Exception{
        System.out.println("PersonValidatorTest Container started with "+obj);
    }

    @Test
    public void testInjection() throws Exception {
        assertNotNull(weld.select(PersonValidatorImpl.class).get());
    }

    @Test
    public void testHappyPath() throws Exception {
        final Person person = context.mock(Person.class);
        context.checking(new Expectations() {{
            allowing(person).getFirstName();
            will(returnValue(GOOD_NAME));

            allowing(person).getLastName();
            will(returnValue(GOOD_NAME));

            allowing(person).getMiddleName();
            will(returnValue(GOOD_NAME));

            allowing(person).getPhoneNum();
            will(returnValue(GOOD_PHONE));

            allowing(person).getInternalId();
            will(returnValue(GOOD_ID));

            allowing(person).getRecordNumber();
            will(returnValue(RECORD_NUMBER));

        }});
        weld.select(PersonValidatorImpl.class).get().validate(person);

        context.assertIsSatisfied();
    }

    @Test(expected = IllegalRecordException.class)
    public void testMissingFirstNameFailure() throws Exception {
        final Person person = context.mock(Person.class);
        context.checking(new Expectations() {{
            allowing(person).getFirstName();
            will(returnValue(EMPTY_NAME));

            allowing(person).getLastName();
            will(returnValue(GOOD_NAME));

            allowing(person).getMiddleName();
            will(returnValue(GOOD_NAME));

            allowing(person).getPhoneNum();
            will(returnValue(GOOD_PHONE));

            allowing(person).getInternalId();
            will(returnValue(GOOD_ID));

            allowing(person).getRecordNumber();
            will(returnValue(RECORD_NUMBER));

        }});
        weld.select(PersonValidatorImpl.class).get().validate(person);

        context.assertIsSatisfied();
    }


    @Test(expected = IllegalRecordException.class)
    public void testLongFirstNameFailure() throws Exception {
        final Person person = context.mock(Person.class);
        context.checking(new Expectations() {{
            allowing(person).getFirstName();
            will(returnValue(LONG_NAME));

            allowing(person).getLastName();
            will(returnValue(GOOD_NAME));

            allowing(person).getMiddleName();
            will(returnValue(GOOD_NAME));

            allowing(person).getPhoneNum();
            will(returnValue(GOOD_PHONE));

            allowing(person).getInternalId();
            will(returnValue(GOOD_ID));

            allowing(person).getRecordNumber();
            will(returnValue(RECORD_NUMBER));

        }});
        weld.select(PersonValidatorImpl.class).get().validate(person);

        context.assertIsSatisfied();
    }


    @Test(expected = IllegalRecordException.class)
    public void testEmptyLastNameFailure() throws Exception {
        final Person person = context.mock(Person.class);
        context.checking(new Expectations() {{
            allowing(person).getFirstName();
            will(returnValue(GOOD_NAME));

            allowing(person).getLastName();
            will(returnValue(EMPTY_NAME));

            allowing(person).getMiddleName();
            will(returnValue(GOOD_NAME));

            allowing(person).getPhoneNum();
            will(returnValue(GOOD_PHONE));

            allowing(person).getInternalId();
            will(returnValue(GOOD_ID));

            allowing(person).getRecordNumber();
            will(returnValue(RECORD_NUMBER));

        }});
        weld.select(PersonValidatorImpl.class).get().validate(person);

        context.assertIsSatisfied();
    }

    @Test(expected = IllegalRecordException.class)
    public void tesLongLastNameFailure() throws Exception {
        final Person person = context.mock(Person.class);
        context.checking(new Expectations() {{
            allowing(person).getFirstName();
            will(returnValue(GOOD_NAME));

            allowing(person).getLastName();
            will(returnValue(LONG_NAME));

            allowing(person).getMiddleName();
            will(returnValue(GOOD_NAME));

            allowing(person).getPhoneNum();
            will(returnValue(GOOD_PHONE));

            allowing(person).getInternalId();
            will(returnValue(GOOD_ID));

            allowing(person).getRecordNumber();
            will(returnValue(RECORD_NUMBER));

        }});
        weld.select(PersonValidatorImpl.class).get().validate(person);

        context.assertIsSatisfied();
    }
    @Test
    public void testLegalEmptyMiddleNameSuccess() throws Exception {
        final Person person = context.mock(Person.class);
        context.checking(new Expectations() {{
            allowing(person).getFirstName();
            will(returnValue(GOOD_NAME));

            allowing(person).getLastName();
            will(returnValue(GOOD_NAME));

            allowing(person).getMiddleName();
            will(returnValue(EMPTY_NAME));

            allowing(person).getPhoneNum();
            will(returnValue(GOOD_PHONE));

            allowing(person).getInternalId();
            will(returnValue(GOOD_ID));

            allowing(person).getRecordNumber();
            will(returnValue(RECORD_NUMBER));

        }});
        weld.select(PersonValidatorImpl.class).get().validate(person);

        context.assertIsSatisfied();
    }


    @Test(expected = IllegalRecordException.class)
    public void testLongMiddleNameFailure() throws Exception {
        final Person person = context.mock(Person.class);
        context.checking(new Expectations() {{
            allowing(person).getFirstName();
            will(returnValue(GOOD_NAME));

            allowing(person).getLastName();
            will(returnValue(GOOD_NAME));

            allowing(person).getMiddleName();
            will(returnValue(LONG_NAME));

            allowing(person).getPhoneNum();
            will(returnValue(GOOD_PHONE));

            allowing(person).getInternalId();
            will(returnValue(GOOD_ID));

            allowing(person).getRecordNumber();
            will(returnValue(RECORD_NUMBER));

        }});
        weld.select(PersonValidatorImpl.class).get().validate(person);

        context.assertIsSatisfied();
    }


    @Test(expected = IllegalRecordException.class)
    public void testEmptyPhoneFailure() throws Exception {
        final Person person = context.mock(Person.class);
        context.checking(new Expectations() {{
            allowing(person).getFirstName();
            will(returnValue(GOOD_NAME));

            allowing(person).getLastName();
            will(returnValue(GOOD_NAME));

            allowing(person).getMiddleName();
            will(returnValue(GOOD_NAME));

            allowing(person).getPhoneNum();
            will(returnValue(EMPTY_NAME));

            allowing(person).getInternalId();
            will(returnValue(GOOD_ID));

            allowing(person).getRecordNumber();
            will(returnValue(RECORD_NUMBER));

        }});
        weld.select(PersonValidatorImpl.class).get().validate(person);

        context.assertIsSatisfied();
    }


    @Test(expected = IllegalRecordException.class)
    public void testEmptyIdFailure() throws Exception {
        final Person person = context.mock(Person.class);
        context.checking(new Expectations() {{
            allowing(person).getFirstName();
            will(returnValue(GOOD_NAME));

            allowing(person).getLastName();
            will(returnValue(GOOD_NAME));

            allowing(person).getMiddleName();
            will(returnValue(GOOD_NAME));

            allowing(person).getPhoneNum();
            will(returnValue(GOOD_PHONE));

            allowing(person).getInternalId();
            will(returnValue(EMPTY_NAME));

            allowing(person).getRecordNumber();
            will(returnValue(RECORD_NUMBER));

        }});
        weld.select(PersonValidatorImpl.class).get().validate(person);

        context.assertIsSatisfied();
    }

    @Test(expected = IllegalRecordException.class)
    public void testAlphaIdFailure() throws Exception {
        final Person person = context.mock(Person.class);
        context.checking(new Expectations() {{
            allowing(person).getFirstName();
            will(returnValue(GOOD_NAME));

            allowing(person).getLastName();
            will(returnValue(GOOD_NAME));

            allowing(person).getMiddleName();
            will(returnValue(GOOD_NAME));

            allowing(person).getPhoneNum();
            will(returnValue(GOOD_PHONE));

            allowing(person).getInternalId();
            will(returnValue(ALPHA_ID));

            allowing(person).getRecordNumber();
            will(returnValue(RECORD_NUMBER));

        }});
        weld.select(PersonValidatorImpl.class).get().validate(person);

        context.assertIsSatisfied();
    }

    @Test(expected = IllegalRecordException.class)
    public void testShortIdFailure() throws Exception {
        final Person person = context.mock(Person.class);
        context.checking(new Expectations() {{
            allowing(person).getFirstName();
            will(returnValue(GOOD_NAME));

            allowing(person).getLastName();
            will(returnValue(GOOD_NAME));

            allowing(person).getMiddleName();
            will(returnValue(GOOD_NAME));

            allowing(person).getPhoneNum();
            will(returnValue(GOOD_PHONE));

            allowing(person).getInternalId();
            will(returnValue(SHORT_ID));

            allowing(person).getRecordNumber();
            will(returnValue(RECORD_NUMBER));

        }});
        weld.select(PersonValidatorImpl.class).get().validate(person);

        context.assertIsSatisfied();
    }

    @Test(expected = IllegalRecordException.class)
    public void testLongIdFailure() throws Exception {
        final Person person = context.mock(Person.class);
        context.checking(new Expectations() {{
            allowing(person).getFirstName();
            will(returnValue(GOOD_NAME));

            allowing(person).getLastName();
            will(returnValue(GOOD_NAME));

            allowing(person).getMiddleName();
            will(returnValue(GOOD_NAME));

            allowing(person).getPhoneNum();
            will(returnValue(GOOD_PHONE));

            allowing(person).getInternalId();
            will(returnValue(LONG_ID));

            allowing(person).getRecordNumber();
            will(returnValue(RECORD_NUMBER));

        }});
        weld.select(PersonValidatorImpl.class).get().validate(person);

        context.assertIsSatisfied();
    }





}

