package com.tsg.auto.helpers.utils;

import com.tsg.auto.helpers.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
public class JsonSchemaProcessorTest {

    @Autowired
    private JsonSchemaProcessor jsonSchemaProcessor;

    @Test
    public void testParseSchema() throws Exception{
        String fileName = "C:\\Users\\thush\\my-office\\my-projects\\git-projects\\via-spring-init\\samples\\SampleSchema1.json";
        String sampleJson = jsonSchemaProcessor.parseSchema(fileName);
        System.out.println(sampleJson);
    }
}
