package com.example.csvtojson.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.opencsv.bean.CsvBindByName;


@JsonPropertyOrder({"id", "name", "phone"})

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
    @CsvBindByName(column = "INTERNAL_ID", required = true)

    private String internalId;

    /**  PHONE_NUM string that matches this pattern ###-###-####. Cannot be empty. */
    @CsvBindByName(column = "PHONE_NUM", required = true)
    private String phoneNum;

    @JsonIgnore
    private long recordNumber;
    private PersonName personName;

    public Person(String internalId, String firstName, String middleName, String lastName, String phoneNum, long recordNumber) {
        this.internalId = internalId;
        this.personName = new PersonName(firstName, middleName, lastName);
        this.phoneNum = phoneNum;
        this.recordNumber = recordNumber;
    }

    public Person() {
    }

    @JsonGetter("id")
    public String getInternalId() {
        return internalId;
    }

    @JsonIgnore
    public String getFirstName() {
        return personName.first;
    }

    @JsonIgnore
    public String getMiddleName() {
        return personName.middle;
    }

    @JsonIgnore
    public String getLastName() {
        return personName.last;
    }

    @JsonGetter("name")
    public PersonName getPersonName() {
        return personName;
    }

    @JsonGetter("phone")
    public String getPhoneNum() {
        return phoneNum;
    }

    public class PersonName {
        /**
         * FIRST_NAME 15 character max string. Cannot be empty.
         */
        private final String first;
        /**
         * MIDDLE_NAME 15 character max string. Can be empty.
         */
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private final String middle;
        /**
         * LAST_NAME 15 character max string. Can be empty.
         */
        private final String last;

        PersonName(String first, String middle, String last) {
            this.first = first;
            this.middle = middle;
            this.last = last;
        }

        public String getFirst() {
            return first;
        }

        public String getMiddle() {
            return middle;
        }

        public String getLast() {
            return last;
        }
    }
}