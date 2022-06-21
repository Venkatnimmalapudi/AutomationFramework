package com.api.endpoints.employee;

import com.api.constants.Urls;
import com.api.dto.employee.Employee;
import com.api.restassured.entity.Param;
import com.api.restassured.entity.RequestBody;
import com.api.restassured.template.HttpMethod;
import com.api.restassured.template.IServiceEndpoint;

import java.util.List;

public class UpdateEmployeeEndpoint implements IServiceEndpoint {
    final Employee employee;

    public UpdateEmployeeEndpoint(final Employee employee) {
        this.employee = employee;
    }

    @Override
    public String url() {
        return String.format(Urls.UPDATE_EMPLOYEE.getUrl(), employee.getId());
    }

    @Override
    public HttpMethod httpMethod() {
        return HttpMethod.PUT;
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
        return new RequestBody(employee.getClass(), employee);
    }

    @Override
    public boolean logEnabled() {
        return true;
    }
}
