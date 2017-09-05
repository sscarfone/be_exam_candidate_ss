package com.example.csvtojson;


import com.example.csvtojson.model.Person;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.IOException;

public class ParserTest {

    @Test
    public void testParser() throws IOException {

        String csvData = "INTERNAL_ID,FIRST_NAME,MIDDLE_NAME,LAST_NAME,PHONE_NUM\n" +
                "12345678,Bobby,,Tables,555-555-5555";
        CSVParser parser = CSVParser.parse(csvData, CSVFormat.RFC4180.withHeader(Person.HeaderFields.class));
        for (CSVRecord csvRecord : parser) {
            String firstName = csvRecord.get(Person.HeaderFields.FIRST_NAME);
            System.out.println(csvRecord);
        }

    }
}
