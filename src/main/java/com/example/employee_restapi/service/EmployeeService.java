package com.example.employee_restapi.service;

import com.example.employee_restapi.entity.Employee;
import java.util.List;

public interface EmployeeService {

     Employee saveEmployee(Employee employee);
     List<Employee> getAllEmployees();
     Employee getEmployeeById(Integer id);
     Employee updateEmployee(Integer id, Employee employee);
     void deleteEmployee(Integer id);
}
