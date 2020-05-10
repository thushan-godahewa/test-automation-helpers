package com.tsg.auto.helpers.commands;

import com.tsg.auto.helpers.utils.JsonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class JsonCommands {

    @Autowired
    private JsonProcessor jsonProcessor;

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
}
