package com.example.csvtojson.transform;

import com.example.csvtojson.model.CsvRecord;
import com.example.csvtojson.model.Person;

public interface CsvRecordToPerson {

    Person toPerson(CsvRecord csvRecord);
}
