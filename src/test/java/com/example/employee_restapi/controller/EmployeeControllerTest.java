package com.example.employee_restapi.controller;

import com.example.employee_restapi.entity.Employee;
import com.example.employee_restapi.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

//Unit Test
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp() {
        employee1 = Employee.builder()
                .id(1)
                .name("zahra")
                .email("zahra@example.com")
                .build();

        employee2 = Employee.builder()
                .id(2)
                .name("roza")
                .email("roza@example.com")
                .build();
    }

    @Test
    void saveEmployee_ShouldReturnCreatedEmployee() {
        // Arrange
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employee1);

        // Act
        ResponseEntity<Employee> response = employeeController.saveEmployee(employee1);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employee1, response.getBody());
        verify(employeeService, times(1)).saveEmployee(any(Employee.class));
    }

    @Test
    void getAllEmployees_ShouldReturnAllEmployees() {
        // Arrange
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeService.getAllEmployees()).thenReturn(employees);

        // Act
        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(employees, response.getBody());
        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void getEmployeeById_WithValidId_ShouldReturnEmployee() {
        // Arrange
        when(employeeService.getEmployeeById(1)).thenReturn(employee1);

        // Act
        ResponseEntity<Employee> response = employeeController.getEmployeeById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee1, response.getBody());
        verify(employeeService, times(1)).getEmployeeById(1);
    }

    @Test
    void updateEmployee_WithValidId_ShouldReturnUpdatedEmployee() {
        // Arrange
        Employee updatedEmployee = Employee.builder()
                .id(1)
                .name("samira")
                .email("samira@gmail.com")
                .build();

        when(employeeService.updateEmployee(eq(1), any(Employee.class))).thenReturn(updatedEmployee);

        // Act
        ResponseEntity<Employee> response = employeeController.updateEmployee(1, updatedEmployee);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedEmployee, response.getBody());
        assertEquals("samira", response.getBody().getName());
        verify(employeeService, times(1)).updateEmployee(eq(1), any(Employee.class));
    }

    @Test
    void deleteEmployee_WithValidId_ShouldReturnSuccessMessage() {
        // Arrange
        doNothing().when(employeeService).deleteEmployee(1);

        // Act
        ResponseEntity<String> response = employeeController.deleteEmployee(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee deleted successfully.", response.getBody());
        verify(employeeService, times(1)).deleteEmployee(1);
    }
}
