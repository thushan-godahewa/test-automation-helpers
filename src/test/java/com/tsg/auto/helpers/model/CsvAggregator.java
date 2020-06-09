package com.tsg.auto.helpers.model;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CsvAggregator implements ArgumentsAggregator {
    @Override
    public CsvData aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
        CsvData csvData = new CsvData();
        Object[] arguments = argumentsAccessor.toArray();
        //Set Request data
        csvData.setRequestData(Arrays.stream(arguments[0].toString().split(","))
                .map(eachNvp -> eachNvp.split(":"))
                .collect(Collectors.toMap(
                        item -> item[0],
                        item -> item.length > 1 ? item[1] : ""
                )));
        //Set Response data
        for(int i=1; i<arguments.length; i++) {
            csvData.setResponseData(Arrays.stream(arguments[i].toString().split(","))
                    .map(eachNvp -> eachNvp.split(":"))
                    .collect(Collectors.toMap(
                            item -> item[0],
                            item -> item.length > 1 ? item[1] : ""
                    )));
        }
        return csvData;
    }
}