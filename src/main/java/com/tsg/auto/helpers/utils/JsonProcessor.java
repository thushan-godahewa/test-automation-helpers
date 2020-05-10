package com.tsg.auto.helpers.utils;

import com.github.wnameless.json.flattener.JsonFlattener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class JsonProcessor {

    @Autowired
    private FileProcessor fileProcessor;

    public List<String> getAllJsonPaths(String name) throws IOException {
        String jsonString = fileProcessor.readFile(name);
        Map<String, Object> flattenJson = JsonFlattener.flattenAsMap(jsonString);
        return flattenJson.keySet().stream().collect(Collectors.toList());
    }

    public List<String> getNormalizedJsonPaths(String name)throws Exception {
        String jsonString = fileProcessor.readFile(name);
        Map<String, Object> flattenJson = JsonFlattener.flattenAsMap(jsonString);
        Set<String> setOfAllKeys = new LinkedHashSet<>();
        flattenJson.keySet().stream().forEach(key -> {
            if(isArrayPath(key)){
                setOfAllKeys.add(key.replaceAll("\\[\\d+\\]", "[*]"));
            }
            else {
                setOfAllKeys.add(key);
            }
        });
        return setOfAllKeys.stream().collect(Collectors.toList());
    }

    private boolean isArrayPath(String originalJsonPath){
        if(Pattern.compile("\\[\\d+\\]").matcher(originalJsonPath).find()){
            return  true;
        }
        else {
            return false;
        }
    }
}
