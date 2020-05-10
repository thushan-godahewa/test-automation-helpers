package com.tsg.auto.helpers.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class FileProcessor {

    public String readFile(String name) throws IOException {
        File file = ResourceUtils.getFile(name);
        return new String(Files.readAllBytes(file.toPath()));
    }
}
