package com.api.endpoints.employee;

import com.api.constants.Urls;
import com.api.restassured.entity.Param;
import com.api.restassured.entity.RequestBody;
import com.api.restassured.template.HttpMethod;
import com.api.restassured.template.IServiceEndpoint;

import java.util.List;

public class GetEmployeeEndpoint implements IServiceEndpoint {
    final String employeeId;

    public GetEmployeeEndpoint(final String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String url() {
        return String.format(Urls.GET_EMPLOYEE.getUrl(), employeeId);
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
