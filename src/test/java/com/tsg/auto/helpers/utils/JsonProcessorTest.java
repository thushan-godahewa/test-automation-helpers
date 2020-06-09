package com.tsg.auto.helpers.utils;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.tsg.auto.helpers.TestConfig;
import com.tsg.auto.helpers.model.CsvAggregator;
import com.tsg.auto.helpers.model.CsvData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
public class JsonProcessorTest {

    @Autowired
    private JsonProcessor jsonProcessor;
    @Autowired
    private FileProcessor fileProcessor;

    private static final String JSON_SAMPLE_1 = "classpath:samples/Sample_1.json";
    String PATH_ID = "$.id";
    String PATH_LAST_NAME = "$.name_details.last_name";
    String PATH_ADDRESS0_POSTCOSE = "$.addresses[0].postcode";
    String PATH_ADDRESS1_ADDRESS_LINE1 = "$.addresses[1].address_line1";
    String PATH_BUSINESS_DETAILS_PRODUCT1 = "$.business_details.products[1]";

    @Test
    public void testListOfAllJsonPaths() throws Exception{
        List<String> listOfAllJsonPaths = jsonProcessor.getAllJsonPaths(JSON_SAMPLE_1);
        assertThat(listOfAllJsonPaths).hasSize(28);
    }

    @Test
    public void readAndValidateAllJsonPaths() throws Exception{
        String jsonString = fileProcessor.readFile(JSON_SAMPLE_1);
        DocumentContext documentContext = JsonPath.parse(jsonString);
        jsonProcessor.getAllJsonPaths(JSON_SAMPLE_1).stream().forEach(jsonPath -> {
           assertThat(documentContext.read(jsonPath, String.class)).isNotNull();
        });
    }

    @Test
    public void readAndValidateSelectedJsonPaths() throws Exception{
        String jsonString = fileProcessor.readFile(JSON_SAMPLE_1);
        DocumentContext documentContext = JsonPath.parse(jsonString);
        assertThat(documentContext.read(PATH_ID, String.class)).isEqualTo("E00123658");
        assertThat(documentContext.read(PATH_LAST_NAME, String.class)).isEqualTo("Running");
        assertThat(documentContext.read(PATH_ADDRESS0_POSTCOSE, Integer.class)).isEqualTo(3103);
        assertThat(documentContext.read(PATH_ADDRESS1_ADDRESS_LINE1, String.class)).isEqualTo("Lot. 02");
        assertThat(documentContext.read(PATH_BUSINESS_DETAILS_PRODUCT1, String.class)).isEqualTo("pharmacy");
    }

    @Test
    public void updateJsonUsingJsonPath() throws Exception{
        String jsonString = fileProcessor.readFile(JSON_SAMPLE_1);
        DocumentContext documentContext = JsonPath.parse(jsonString);
        documentContext.set(PATH_ID, "1234567890");
        documentContext.set(PATH_LAST_NAME, "Coming");
        documentContext.set(PATH_ADDRESS0_POSTCOSE, Integer.valueOf(4214));
        documentContext.set(PATH_ADDRESS1_ADDRESS_LINE1, "Lot. 03");
        documentContext.set(PATH_BUSINESS_DETAILS_PRODUCT1, "pharmaceutical");
        assertThat(documentContext.read(PATH_ID, String.class)).isEqualTo("1234567890");
        assertThat(documentContext.read(PATH_LAST_NAME, String.class)).isEqualTo("Coming");
        assertThat(documentContext.read(PATH_ADDRESS0_POSTCOSE, Integer.class)).isEqualTo(4214);
        assertThat(documentContext.read(PATH_ADDRESS1_ADDRESS_LINE1, String.class)).isEqualTo("Lot. 03");
        assertThat(documentContext.read(PATH_BUSINESS_DETAILS_PRODUCT1, String.class)).isEqualTo("pharmaceutical");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/sample_data_1.csv", numLinesToSkip = 1)
    public void updateJsonUsingJsonPathsFromCsv(String jsonPath, Object valueToSet) throws Exception{
        String jsonString = fileProcessor.readFile(JSON_SAMPLE_1);
        DocumentContext documentContext = JsonPath.parse(jsonString);
        documentContext.set(jsonPath, valueToSet);
        assertThat(documentContext.read(jsonPath, Object.class)).isEqualTo(valueToSet);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/sample_data_2.csv", numLinesToSkip = 1)
    public void updateJsonUsingJsonPathsFromCsvCustomAggregator(@AggregateWith(CsvAggregator.class) CsvData csvData) throws Exception{
        String jsonString = fileProcessor.readFile(JSON_SAMPLE_1);
        DocumentContext documentContext = JsonPath.parse(jsonString);
        //documentContext.set(jsonPath, valueToSet);
        //assertThat(documentContext.read(jsonPath, Object.class)).isEqualTo(valueToSet);
    }
}
