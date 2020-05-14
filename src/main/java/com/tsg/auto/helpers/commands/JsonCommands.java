package com.tsg.auto.helpers.commands;

import com.tsg.auto.helpers.utils.JsonProcessor;
import com.tsg.auto.helpers.utils.JsonSchemaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class JsonCommands {

    @Autowired
    private JsonProcessor jsonProcessor;
    @Autowired
    private JsonSchemaProcessor jsonSchemaProcessor;

    @ShellMethod("List All JSON Paths on a file.")
    public String listJsonPaths(String name) {
        try {
            List<String> list = jsonProcessor.getAllJsonPaths(name);
            return String.join("\n", list);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    @ShellMethod("List Normalized JSON Paths on a file.")
    public String listMinJsonPaths(String name) {
        try {
            List<String> list = jsonProcessor.getNormalizedJsonPaths(name);
            return String.join("\n", list);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    @ShellMethod("Build a sample JSON from Schema JSON file.")
    public String buildSampleJson(String name) {
        try {
            return jsonSchemaProcessor.parseSchema(name);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
}
