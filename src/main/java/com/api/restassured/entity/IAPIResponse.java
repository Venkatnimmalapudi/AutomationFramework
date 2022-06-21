package com.api.restassured.entity;

import com.api.constants.HttpStatuses;

import java.util.Map;


public interface IAPIResponse extends ISerialization, IJsonSchemaValidator {
    String getResponseText();

    HttpStatuses getStatusCode();

    String getHeaderValue(String key);

    Map<String, String> getAllHeaders();
}
