package com.example.csvtojson;


import com.example.csvtojson.model.CsvRecord;
import com.example.csvtojson.validator.IllegalRecordException;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class OpenCsvTest {

    @Test
    public void testParser() throws IOException {

        String csvData = "INTERNAL_ID,FIRST_NAME,MIDDLE_NAME,LAST_NAME,PHONE_NUM\n" +
                "12345678,Bobby,,Tables,555-555-5555";

        List<CsvRecord> beans = new CsvToBeanBuilder(new StringReader(csvData)).
                withType(CsvRecord.class).
                build().
                parse();

        for (CsvRecord person : beans) {
            assertEquals("12345678", person.getInternalId());
            assertEquals("Bobby", person.getFirstName());
            assertEquals(null, person.getMiddleName());
            assertEquals("Tables", person.getLastName());
            assertEquals("555-555-5555", person.getPhoneNum());

        }

    }

    @Test
    public void testGenerator() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Writer stringWriter = new StringWriter();
        StatefulBeanToCsvBuilder beanToCsvBuilder = new StatefulBeanToCsvBuilder(stringWriter);
        StatefulBeanToCsv beanToCsv = beanToCsvBuilder.build();

        IllegalRecordException ile = new IllegalRecordException("message", 0, "fie,ld", "value");
        beanToCsv.write(ile);
        stringWriter.close();
        String actual = stringWriter.toString();
        assertEquals("\"ERROR_MSG\",\"LINE_NUM\"\n" +
                "\"message: Field: fie,ld, Value:value\",\"0\"", actual);
    }

}
