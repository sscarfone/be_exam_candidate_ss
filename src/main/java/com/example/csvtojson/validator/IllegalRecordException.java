package com.example.csvtojson.validator;

public class IllegalRecordException extends Exception {

    private final long recordNumber;
    private final String field;
    private final String value;


    public IllegalRecordException(String message, long recordNumber, String field, String value) {
        super(message);
        this.recordNumber = recordNumber;
        this.field = field;
        this.value = value;
    }
}
