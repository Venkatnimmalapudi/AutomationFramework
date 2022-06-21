package com.api.restassured.entity;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ISerialization {
    <T> T deserialize(Class<T> cls) throws JsonProcessingException;
}
