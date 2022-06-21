package com.api.restassured.utility;

import com.api.restassured.entity.IAPIResponse;
import com.api.restassured.entity.RestAssuredResponse;
import com.api.restassured.template.HttpMethod;
import com.api.restassured.template.IServiceEndpoint;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RequestHandler {

    public IAPIResponse processAPIRequest(IServiceEndpoint iServiceEndpoint) {
        return new RestAssuredResponse(submitRequest(iServiceEndpoint));
    }

    private Response submitRequest(IServiceEndpoint iServiceEndpoint) {
        Response response = processIServiceEndpoint(iServiceEndpoint);
        final String noOfRetries = System.getProperty("noOfRetries");
        int retries = (noOfRetries == null || noOfRetries.isEmpty()) ? 0 : Integer.parseInt(noOfRetries);
        for (int i = 0; i < retries && isResponse5xx(response); i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            response = processIServiceEndpoint(iServiceEndpoint);
        }
        return response;
    }


    private Response processIServiceEndpoint(IServiceEndpoint iServiceEndpoint) {
        RestAssured.registerParser("text/plain", Parser.JSON);
        RestAssured.registerParser("application/grpc", Parser.JSON);
        RestAssured.registerParser("text/html", Parser.JSON);

        final String url = iServiceEndpoint.url();
        final HttpMethod httpMethod = iServiceEndpoint.httpMethod();

        final RequestSpecification requestSpecification = buildRequestSpecification(iServiceEndpoint);
        final Response response = makeAPIRequestAsPerHTTPMethod(url, httpMethod, requestSpecification);
        return response;
    }

    private Response makeAPIRequestAsPerHTTPMethod(String url, HttpMethod httpMethod,
                                                   RequestSpecification requestSpecification) {
        Response response = null;
        switch (httpMethod) {
            case GET:
                response = requestSpecification.get(url);
                break;
            case POST:
                response = requestSpecification.post(url);
                break;
            case PUT:
                response = requestSpecification.put(url);
                break;
            case PATCH:
                response = requestSpecification.patch(url);
                break;
            case DELETE:
                response = requestSpecification.delete(url);
        }
        return response;
    }

    private RequestSpecification buildRequestSpecification(IServiceEndpoint iServiceEndpoint) {
        final RestAssuredConfig config = RestAssured.config()
                .encoderConfig(new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        RestAssured.useRelaxedHTTPSValidation();

        final RequestSpecification request = given().config(config);

        if (iServiceEndpoint.headers() != null) {
            iServiceEndpoint.headers().forEach(h -> request.header(h.getKey(), h.getValue()));
        }

        if (iServiceEndpoint.queryParameters() != null) {
            iServiceEndpoint.queryParameters().forEach(q -> request.queryParam(q.getKey(), q.getValue()));
        }

        if (iServiceEndpoint.pathParameters() != null) {
            iServiceEndpoint.pathParameters().forEach(p -> request.pathParam(p.getKey(), p.getValue()));
        }

        if (iServiceEndpoint.body() != null)
            request.body(iServiceEndpoint.body().getBodyAsString());

        if (iServiceEndpoint.logEnabled()) {
            request.filter(new ResponseLoggingFilter());
            request.log().all(true);
        }


        return request;
    }

    private boolean isResponse5xx(Response response) {
        return (response.getStatusCode() >= 500) && (response.getStatusCode() < 505);
    }

}
