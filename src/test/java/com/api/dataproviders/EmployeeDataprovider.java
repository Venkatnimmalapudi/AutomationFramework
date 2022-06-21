package com.api.dataproviders;

import com.api.dto.employee.Employee;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmployeeDataprovider {

    private static Logger logger = LoggerFactory.getLogger(EmployeeDataprovider.class);

    @DataProvider(name = "fetchEmployee")
    public Object[] fetchEmployee() throws IOException {
        CSVReader csvReader = null;
            csvReader = new CSVReader(new FileReader( getClass().getClassLoader()
                    .getResource("testdata/employee/employee.csv").getFile()));
        final List<Employee> employeeList = new ArrayList<>();
        //read line by line
        String[] record = null;
        //skip header row
        csvReader.readNext();
        final Random random = new Random();
        while((record = csvReader.readNext()) != null){
            final Employee employee = Employee.builder().employeeName(record[0] + "_" + random.nextInt())
                    .employeeSalary(Integer.valueOf(record[1]))
                    .employeeAge(Integer.valueOf(record[2])).build();
            employeeList.add(employee);
        }

        csvReader.close();

        logger.debug("Employee list is : " + employeeList);

        return employeeList.toArray();
    }

}
