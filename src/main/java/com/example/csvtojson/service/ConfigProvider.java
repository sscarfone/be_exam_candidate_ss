package com.example.csvtojson.service;

import java.nio.file.Path;

public interface ConfigProvider {

    Path getSourceDir();

    Path getDestDir();

    Path getErrorDir();
}
