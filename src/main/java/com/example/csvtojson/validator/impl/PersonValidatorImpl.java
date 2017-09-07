package com.example.csvtojson.validator.impl;

import com.example.csvtojson.model.Person;
import com.example.csvtojson.validator.IllegalRecordException;
import com.example.csvtojson.validator.PersonValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.interceptor.Interceptor;

@Any
public class PersonValidatorImpl implements PersonValidator {


    private static final Logger LOGGER = LogManager.getLogger();

    public PersonValidatorImpl() {

    }

    public void onStartUp(@Observes @Priority(Interceptor.Priority.APPLICATION - 400)
                          @Initialized(ApplicationScoped.class) Object obj) throws Exception{
        LOGGER.info("PersonValidatorImpl Container started with " + obj);
    }

    @Override
    public void validate(Person person) throws IllegalRecordException {

        long recordNumber = person.getRecordNumber();

        String internalId = person.getInternalId();
        validateNotNull(Person.HeaderFields.INTERNAL_ID, internalId, recordNumber);
        validateNumeric(Person.HeaderFields.INTERNAL_ID, internalId, recordNumber);

        String firstName = person.getFirstName();
        validateNotNull(Person.HeaderFields.FIRST_NAME, firstName, recordNumber);
        validateMaxLength(Person.HeaderFields.FIRST_NAME, 15, firstName, recordNumber); //todo: magic numbers

        String phoneNum = person.getPhoneNum();
        validateNotNull( Person.HeaderFields.PHONE_NUM, phoneNum, recordNumber);
        validatePhoneNumber( Person.HeaderFields.PHONE_NUM, phoneNum, recordNumber);

        String lastName = person.getLastName();
        validateNotNull( Person.HeaderFields.LAST_NAME, lastName, recordNumber);
        validateMaxLength(Person.HeaderFields.LAST_NAME, 15, lastName, recordNumber);

        String middleName = person.getMiddleName();
        validateMaxLength(Person.HeaderFields.MIDDLE_NAME, 15, middleName, recordNumber);

    }

    private void validatePhoneNumber(Person.HeaderFields field, String value, long recordNumber) throws IllegalRecordException{
        if (!value.matches("(?:\\d{3}-){2}\\d{4}")) {
            throw new IllegalRecordException("Phone numbers must match ###-###-####.", recordNumber, field.toString(), value);
        }
    }

    private void validateNumeric(Person.HeaderFields field, String value, long recordNumber) throws IllegalRecordException  {
       if (!value.matches("\\d{8}")) {
            throw new IllegalRecordException("Numeric value must be an 8 digit positive number.", recordNumber, field.toString(), value);
        }
    }

    private void validateMaxLength(Person.HeaderFields field, int max, String value, long recordNumber) throws IllegalRecordException {
        if (value == null) {
            return; // if we got past the null checks, this is okay.
        }
        if (value.length() == 0) {
            return;
        }
        if (value.length() <=  max) {
            return;
        }
        throw new IllegalRecordException("Field exceeds maximum length of " + max + " characters.", recordNumber, field.toString(), value);
    }

    private void validateNotNull(Person.HeaderFields field, String value, long recordNumber) throws IllegalRecordException {
        if (value != null && value.length() > 0)  {
            return;
        }
        throw new IllegalRecordException("Field must not be null.", recordNumber, field.toString(), value);

    }
}
