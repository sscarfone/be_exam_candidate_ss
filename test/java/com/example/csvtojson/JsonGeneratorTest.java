package com.example.csvtojson;


import com.example.csvtojson.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JsonGeneratorTest {

    @Test
    public void testJson() throws IOException {

        Person person = new Person("12345678", "first", "middle", "last", "123-345-7890", 0);

        ObjectMapper mapper = new ObjectMapper();
        //mapper.registerSubtypes(Person.PersonName.class);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String jsonString = mapper.writeValueAsString(person);

        assertEquals("{\n" +
                "  \"id\" : \"12345678\",\n" +
                "  \"name\" : {\n" +
                "    \"first\" : \"first\",\n" +
                "    \"middle\" : \"middle\",\n" +
                "    \"last\" : \"last\"\n" +
                "  },\n" +
                "  \"phone\" : \"123-345-7890\"\n" +
                "}", jsonString);
    }


}
