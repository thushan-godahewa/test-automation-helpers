package com.tsg.auto.helpers.model;

import java.util.HashMap;
import java.util.Map;

public class CsvData {

    private Map<String, Object> requestData = new HashMap<>();
    private Map<String, Object> responseData = new HashMap<>();

    public Map<String, Object> getRequestData() {
        return requestData;
    }

    public void setRequestData(Map<String, Object> requestData) {
        this.requestData = requestData;
    }

    public Map<String, Object> getResponseData() {
        return responseData;
    }

    public void setResponseData(Map<String, Object> responseData) {
        this.responseData = responseData;
    }
}
