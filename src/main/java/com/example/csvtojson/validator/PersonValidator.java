package com.example.csvtojson.validator;

import com.example.csvtojson.model.Person;

public interface PersonValidator {

    void validate( Person person) throws IllegalRecordException;

}
