package com.api.tests.employee;

import com.api.dataproviders.EmployeeDataprovider;
import com.api.dto.employee.Employee;
import com.api.dto.employee.EmployeeResponse;
import com.api.endpoints.employee.*;
import com.api.restassured.entity.IAPIResponse;
import com.api.restassured.utility.RequestHandler;
import com.api.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EmployeeTest {
    private static Logger logger = LoggerFactory.getLogger(EmployeeTest.class);

    public static final String GET_EMPLOYEES_SCHEMA = "schema/get_employees_schema.json";
    private final EmployeeHelper employeeHelper = new EmployeeHelper();

    //Get all employees and validate the schema returned in the response
    @Test(groups = "api")
    public void getAllEmployeesValidation() {
        //contruct the Endpoint
        final GetEmployeesEndpoint getEmployeesEndpoint = new GetEmployeesEndpoint();
        logger.debug("Inside Test");
        //hit the api
        final IAPIResponse response = new RequestHandler().processAPIRequest(getEmployeesEndpoint);
        logger.debug("Status code is : " + response.getStatusCode().getCode() +
                " message is : " + response.getStatusCode().getMessage());
        //validate status code
        Assert.assertEquals(response.getStatusCode().getCode(), 200);
        response.validateJsonSchema(GET_EMPLOYEES_SCHEMA);
    }


    @Test(groups = "api")
    public void getNewlyCreatedEmployeeAndValidate() throws JsonProcessingException {
        final Employee employee = Employee.builder().employeeName("abc").employeeAge(29)
                .employeeSalary(3600000).build();
        final EmployeeResponse createEmployeeResponse = employeeHelper.createEmployee(employee);
        final EmployeeResponse getEmployeeResponse =
                employeeHelper.getEmployeeDetailFromId(createEmployeeResponse.getData().getId());
        Assert.assertEquals(getEmployeeResponse.getStatus(), "success");
        employeeHelper.validateEmployee(getEmployeeResponse.getData(), employee);
    }

    @Test(dataProvider = "fetchEmployee", dataProviderClass = EmployeeDataprovider.class, groups = "api")
    public void createAndValidateEmployee(final Employee employee) throws JsonProcessingException {
        EmployeeResponse actualResponse = employeeHelper.createEmployee(employee);
        Assert.assertEquals(actualResponse.getStatus(), "success");
        Assert.assertEquals(actualResponse.getMessage(), "Successfully! Record has been added.");
        Assert.assertNotNull(actualResponse.getData().getId());
    }

    @Test(groups = "api")
    public void updateNewlyCreatedEmployeeAndValidate() throws JsonProcessingException {
        final Employee employee = Employee.builder().employeeName("abc").employeeAge(29)
                .employeeSalary(3600000).build();

        final EmployeeResponse createEmployeeResponse =  employeeHelper.createEmployee(employee);

        //update salary
        employee.setEmployeeSalary(240000);
        employee.setId(createEmployeeResponse.getData().getId());

        final EmployeeResponse updateEmployeeResponse = employeeHelper.updateEmployee(employee);
        logger.debug("Updated Employee : {}", updateEmployeeResponse);
        final EmployeeResponse getEmployeeResponse =
                employeeHelper.getEmployeeDetailFromId(updateEmployeeResponse.getData().getId());
        logger.debug("Created Employee Response with modification : {}", employee);
        final boolean flag = JsonUtils.areEqual(new JSONObject(getEmployeeResponse), new JSONObject(employee));
        Assert.assertTrue(flag, "Update Employee Details failed");
    }

    @Test(groups = "api")
    public void deleteNewlyCreateAndValidate() throws JsonProcessingException {
        final Employee employee = Employee.builder().employeeName("abc").employeeAge(29)
                .employeeSalary(3600000).build();

        final EmployeeResponse createEmployeeResponse = employeeHelper.createEmployee(employee);
        employeeHelper.deleteEmployee(createEmployeeResponse.getData().getId());
        //make a get call and validate it response with the expected data
        final GetEmployeeEndpoint getEmployeeEndpoint =
                new GetEmployeeEndpoint(String.valueOf(createEmployeeResponse.getData().getId()));
        final IAPIResponse getEmpResponse = new RequestHandler().processAPIRequest(getEmployeeEndpoint);
        logger.debug("Status code is : " + getEmpResponse.getStatusCode().getCode() +
                " message is : " + getEmpResponse.getStatusCode().getMessage());
        //validate status code
        Assert.assertEquals(getEmpResponse.getStatusCode().getCode(), 404);
    }




}
