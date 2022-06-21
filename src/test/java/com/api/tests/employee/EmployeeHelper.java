package com.api.tests.employee;

import com.api.dto.employee.DeleteEmployeeResponse;
import com.api.dto.employee.Employee;
import com.api.dto.employee.EmployeeResponse;
import com.api.endpoints.employee.CreateEmployeeEndpoint;
import com.api.endpoints.employee.DeleteEmployeeEndpoint;
import com.api.endpoints.employee.GetEmployeeEndpoint;
import com.api.endpoints.employee.UpdateEmployeeEndpoint;
import com.api.restassured.entity.IAPIResponse;
import com.api.restassured.utility.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class EmployeeHelper {
    private static Logger logger = LoggerFactory.getLogger(EmployeeHelper.class);

    public void validateEmployee(Employee actualResponse, Employee expectedResponse) {
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(actualResponse.getId());
        softAssert.assertEquals(actualResponse.getEmployeeAge(), expectedResponse.getEmployeeAge());
        softAssert.assertEquals(actualResponse.getEmployeeSalary(), expectedResponse.getEmployeeSalary());
        softAssert.assertTrue(actualResponse.getEmployeeName().contains(expectedResponse.getEmployeeName()));
        softAssert.assertAll();
    }

    public EmployeeResponse createEmployee(Employee employee) throws JsonProcessingException {
        final CreateEmployeeEndpoint createEmployeeEndpoint = new CreateEmployeeEndpoint(employee);
        final IAPIResponse response = new RequestHandler().processAPIRequest(createEmployeeEndpoint);
        Assert.assertEquals(response.getStatusCode().getCode(), 200);
        return response.deserialize(EmployeeResponse.class);
    }

    public EmployeeResponse getEmployeeDetailFromId(Integer id) throws JsonProcessingException {
        //make a get call and validate it response with the expected data
        final GetEmployeeEndpoint getEmployeeEndpoint =
                new GetEmployeeEndpoint(String.valueOf(id));
        final IAPIResponse getEmpResponse = new RequestHandler().processAPIRequest(getEmployeeEndpoint);
        logger.debug("Status code is : " + getEmpResponse.getStatusCode().getCode() +
                " message is : " + getEmpResponse.getStatusCode().getMessage());
        //validate status code
        Assert.assertEquals(getEmpResponse.getStatusCode().getCode(), 200);
        return getEmpResponse.deserialize(EmployeeResponse.class);
    }

    public EmployeeResponse updateEmployee(Employee employee) throws JsonProcessingException {
        final UpdateEmployeeEndpoint updateEmployeeEndpoint = new UpdateEmployeeEndpoint(employee);
        final IAPIResponse response1 = new RequestHandler().processAPIRequest(updateEmployeeEndpoint);
        Assert.assertEquals(response1.getStatusCode().getCode(), 200);
        EmployeeResponse updateEmployeeResponse = response1.deserialize(EmployeeResponse.class);
        Assert.assertNotNull(updateEmployeeResponse.getData().getId());
        return updateEmployeeResponse;
    }

    public void deleteEmployee(Integer id) throws JsonProcessingException {
        final DeleteEmployeeEndpoint deleteEmployeeEndpoint =
                new DeleteEmployeeEndpoint(String.valueOf(id));
        final IAPIResponse response1 = new RequestHandler().processAPIRequest(deleteEmployeeEndpoint);
        Assert.assertEquals(response1.getStatusCode().getCode(), 200);
        DeleteEmployeeResponse deleteEmployee = response1.deserialize(DeleteEmployeeResponse.class);
        Assert.assertEquals(deleteEmployee.getStatus(), "success");
        Assert.assertEquals(deleteEmployee.getMessage(), "Successfully! Record has been deleted");
        Assert.assertEquals(deleteEmployee.getData(), id);
    }
}
