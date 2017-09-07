package com.example.csvtojson.model;

import com.opencsv.bean.CsvBindByName;


public class CsvRecord {


    /**
     * INTERNAL_ID 8 digit positive integer. Cannot be empty.
     */
    @CsvBindByName(column = "INTERNAL_ID", required = true)
    private String internalId;

    /**
     * PHONE_NUM string that matches this pattern ###-###-####. Cannot be empty.
     */
    @CsvBindByName(column = "PHONE_NUM", required = true)
    private String phoneNum;

    /**
     * FIRST_NAME 15 character max string. Cannot be empty.
     */
    @CsvBindByName(column = "FIRST_NAME", required = true)
    private String firstName;

    /**
     * MIDDLE_NAME 15 character max string. Can be empty.
     */
    @CsvBindByName(column = "MIDDLE_NAME", required = false)
    private String middleName;

    /**
     * LAST_NAME 15 character max string. Can be empty.
     */
    @CsvBindByName(column = "LAST_NAME", required = true)
    private String lastName;

    private long recordNumber;

    public CsvRecord() {
    }

    public String getInternalId() {
        return internalId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getRecordNumber() {
        return recordNumber;
    }
}