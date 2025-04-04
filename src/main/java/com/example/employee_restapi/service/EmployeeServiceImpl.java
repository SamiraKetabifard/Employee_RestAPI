package com.example.employee_restapi.service;

import com.example.employee_restapi.entity.Employee;
import com.example.employee_restapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        } else {
              throw new RuntimeException("Employee Not Found");
        }
    }
    @Override
    public Employee updateEmployee(Integer id, Employee employee) {
        Employee existedEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee Not Found With Id : " + id));
        existedEmployee.setEmail(employee.getEmail());
        existedEmployee.setName(employee.getName());
        return employeeRepository.save(existedEmployee);
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee Not Found With Id : " + id));
        employeeRepository.deleteById(id);
    }
}
