package com.api.dto.employee;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "employee_name",
        "employee_salary",
        "employee_age",
        "profile_image"
})

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("employee_name")
    private String employeeName;
    @JsonProperty("employee_salary")
    private Integer employeeSalary;
    @JsonProperty("employee_age")
    private Integer employeeAge;
    @JsonProperty("profile_image")
    private String profileImage;
}
