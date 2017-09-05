package com.example.csvtojson.service;

import java.nio.file.Path;

public interface CsvFileHandler {
    void handleFile(Path filename);
}
