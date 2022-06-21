package com.api.endpoints.employee;

import com.api.constants.Urls;
import com.api.restassured.entity.Param;
import com.api.restassured.entity.RequestBody;
import com.api.restassured.template.HttpMethod;
import com.api.restassured.template.IServiceEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GetEmployeesEndpoint implements IServiceEndpoint {
    private static Logger logger = LoggerFactory.getLogger(GetEmployeesEndpoint.class);

    @Override
    public String url() {
        logger.debug("URL is : " + Urls.GET_ALL_EMPLOYEES.getUrl());
        return Urls.GET_ALL_EMPLOYEES.getUrl();
    }

    @Override
    public HttpMethod httpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public List<Param> queryParameters() {
        return null;
    }

    @Override
    public List<Param> pathParameters() {
        return null;
    }

    @Override
    public List<Param> headers() {
        return null;
    }

    @Override
    public RequestBody body() {
        return null;
    }

    @Override
    public boolean logEnabled() {
        return true;
    }
}
