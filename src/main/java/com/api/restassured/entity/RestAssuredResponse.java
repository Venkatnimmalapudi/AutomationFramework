package com.api.restassured.entity;

import com.api.constants.HttpStatuses;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.Matcher;


public class RestAssuredResponse implements IAPIResponse {
    private Response response;
    private HttpStatuses httpStatusCode;
    private String responseAsText;
    private Map<String, String> responseHeaders;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RestAssuredResponse(Response response) {
        this.response = response;
        this.httpStatusCode = HttpStatuses.fromStatusCode(response.getStatusCode());
        this.responseAsText = response.asString();
        setResponseHeaders(response);
    }

    private void setResponseHeaders(Response response) {
        final Map<String, String> headers = new HashMap<String, String>();
        for (Header header : response.headers().asList()) {
            headers.put(header.getName(), header.getValue());
        }
        this.responseHeaders = headers;
    }


    public String getResponseText() {
        return responseAsText;
    }

    public HttpStatuses getStatusCode() {
        return httpStatusCode;
    }

    public String getHeaderValue(String key) {
        if (responseHeaders.containsKey(key))
            return responseHeaders.get(key);
        return null;
    }

    public Map<String, String> getAllHeaders() {
        return responseHeaders;
    }

    public void validateJsonSchema(String schemaFilePath) {
        final InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(schemaFilePath);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(inputStream), new Matcher[0]);
    }

    public <T> T deserialize(Class<T> cls) throws JsonProcessingException {
        return objectMapper.readValue(this.responseAsText, cls);
    }
}
