package com.example.employee_restapi.controller;

import com.example.employee_restapi.entity.Employee;
import com.example.employee_restapi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class EmployeeController {

        @Autowired
        private EmployeeService employeeService;

        @PostMapping("/emp/create")
        public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
            return new ResponseEntity<>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
        }

        @GetMapping("/emp/getall")
        public ResponseEntity<List<Employee>> getAllEmployees() {
            return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
        }

        @GetMapping("/emp/get/{id}")
        public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Integer id) {
            return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
        }

        @PutMapping("/emp/update/{id}")
        public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Integer id,
                                                       @RequestBody Employee employee) {
            return new ResponseEntity<>(employeeService.updateEmployee(id, employee), HttpStatus.OK);
        }

        @DeleteMapping("/emp/delete/{id}")
        public ResponseEntity<String> deleteEmployee(@PathVariable("id") Integer id) {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>("Employee deleted successfully.",HttpStatus.OK);
        }
}
