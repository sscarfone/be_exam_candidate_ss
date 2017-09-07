package com.example.csvtojson.validator;

import com.opencsv.bean.CsvBindByName;

public class IllegalRecordException extends Exception {

    @CsvBindByName(column = "LINE_NUM", required = true)
    private final long recordNumber;
    @CsvBindByName(column = "ERROR_MSG", required = true)
    private final String localizedMessage;

    public IllegalRecordException(String message, long recordNumber, String field, String value) {
        super(message);
        this.recordNumber = recordNumber;
        this.localizedMessage = message + ":" + " Field: " + field + ", Value:" + value;
    }

    public long getRecordNumber() {
        return recordNumber;
    }

    @Override
    public String getLocalizedMessage() {
        return this.localizedMessage;
    }
}
