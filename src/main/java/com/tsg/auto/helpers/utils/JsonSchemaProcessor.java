package com.tsg.auto.helpers.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class JsonSchemaProcessor {

    @Autowired
    private FileProcessor fileProcessor;

    public String parseSchema(String name) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonSchema = objectMapper.readTree(fileProcessor.readFile(name));
        Map<String, JsonNode> properties = objectMapper.convertValue(jsonSchema.get("properties")
                , new TypeReference<Map<String, JsonNode>>(){});
        JsonNode jsonRoot = objectMapper.createObjectNode();
        for (Map.Entry<String, JsonNode> entry : properties.entrySet()) {
            String key = entry.getKey();
            JsonNode jsonNode = entry.getValue();
            insertData(jsonRoot, key, jsonNode);
        }
        return jsonRoot.toPrettyString();
    }

    private void insertData(JsonNode root, String key, JsonNode jsonNode){
        String dataType = jsonNode.get("type").asText();
        switch (dataType.toLowerCase()){
            case "integer":
                ((ObjectNode) root).put(key, 100);
                break;
            case "number":
                ((ObjectNode) root).put(key, 200);
                break;
            case "string":
                ((ObjectNode) root).put(key, "string");
                break;
            default:
                throw new IllegalArgumentException("Unrecognized data type - "+dataType);
        }
    }
}
