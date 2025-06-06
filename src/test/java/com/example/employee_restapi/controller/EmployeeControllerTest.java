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

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1)
                .name("zahra")
                .email("zahra@gmail.com")
                .build();
    }
    @Test
    void saveEmployee_ShouldReturnCreatedEmployee() {
        // Arrange
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employee);
        // Act
        ResponseEntity<Employee> response = employeeController.saveEmployee(employee);
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employee, response.getBody());
        verify(employeeService, times(1)).saveEmployee(any(Employee.class));
    }
    @Test
    void getAllEmployees_ShouldReturnAllEmployees() {
        // Arrange
        Employee employee2 = Employee.builder()
                .id(2)
                .name("roza")
                .email("roza@gmail.com")
                .build();

        List<Employee> employees = Arrays.asList(employee, employee2);
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
        when(employeeService.getEmployeeById(1)).thenReturn(employee);
        // Act
        ResponseEntity<Employee> response = employeeController.getEmployeeById(1);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());
        verify(employeeService, times(1)).getEmployeeById(1);
    }
    @Test
    void getEmployeeById_WhenEmployeeNotFound_ShouldThrowException() {
        // Arrange
        when(employeeService.getEmployeeById(999)).thenThrow(new RuntimeException("Employee not found"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeeController.getEmployeeById(999);
        });

        assertEquals("Employee not found", exception.getMessage());
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
    void updateEmployee_WhenEmployeeNotFound_ShouldThrowException() {
        Employee updatedEmployee = Employee.builder()
                .id(999)
                .name("t")
                .email("t@gmail.com")
                .build();

        when(employeeService.updateEmployee(eq(999), any(Employee.class)))
                .thenThrow(new RuntimeException("Employee not found"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeeController.updateEmployee(999, updatedEmployee);
        });

        assertEquals("Employee not found", exception.getMessage());
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
    @Test
    void deleteEmployee_WhenEmployeeNotFound_ShouldThrowException() {
        doThrow(new RuntimeException("Employee not found")).when(employeeService).deleteEmployee(999);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeeController.deleteEmployee(999);
        });
        assertEquals("Employee not found", exception.getMessage());
    }
}
