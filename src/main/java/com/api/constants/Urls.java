package com.api.constants;

import java.lang.String;

import lombok.Getter;

public enum Urls {
    BASE_URL("http://dummy.restapiexample.com/"),
    GET_ALL_EMPLOYEES(BASE_URL.getUrl() + "api/v1/employees"),
    CREATE_EMPLOYEE(BASE_URL.getUrl() + "api/v1/create" ),
    GET_EMPLOYEE(BASE_URL.getUrl() + "api/v1/employee/%s"),
    UPDATE_EMPLOYEE(BASE_URL.getUrl() + "api/v1/update/%s"),
    DELETE_EMPLOYEE(BASE_URL.getUrl() + "api/v1/delete/%s");




    @Getter
    private String url;

    Urls(String url) {
        this.url = url;
    }
}
