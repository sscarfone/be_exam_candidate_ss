package com.example.csvtojson.model;


public class Person {

    public long getRecordNumber() {
        return recordNumber;
    }

    public enum HeaderFields {
        INTERNAL_ID,
        FIRST_NAME,
        LAST_NAME,
        MIDDLE_NAME,
        PHONE_NUM
    }

    /** INTERNAL_ID 8 digit positive integer. Cannot be empty. */
    private final String internalId;

    /** FIRST_NAME 15 character max string. Cannot be empty. */
    private final String firstName;

    /** MIDDLE_NAME 15 character max string. Can be empty. */
    private String middleName;

    /** LAST_NAME 15 character max string. Can be empty. */
    private final String lastName;

    /**  PHONE_NUM string that matches this pattern ###-###-####. Cannot be empty. */
    private final String phoneNum;

    private final long recordNumber;

    public String getInternalId() {
        return internalId;
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public Person(String internalId, String firstName, String middleName, String lastName, String phoneNum, long recordNumber) {
        this.internalId = internalId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.recordNumber = recordNumber;
    }
}