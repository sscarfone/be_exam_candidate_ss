package com.example.csvtojson.transform;

import com.example.csvtojson.model.Person;
import org.apache.commons.csv.CSVRecord;

public interface CsvRecordToPerson {

    Person toPerson( CSVRecord csvRecord );
}
