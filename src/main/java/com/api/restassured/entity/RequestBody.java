package com.api.restassured.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestBody {
    private String objectClass;
    private Object objectInstance;

    public RequestBody(Class objectClass, Object objectInstance) {
        this.objectClass = objectClass.getName();
        this.objectInstance = objectInstance;
    }

    public String getBodyAsString() {
        try {
            return new ObjectMapper().writeValueAsString(objectInstance);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
