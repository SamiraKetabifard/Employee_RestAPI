package com.example.employee_restapi.service;

import com.example.employee_restapi.entity.Employee;
import com.example.employee_restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//Unit Test
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1)
                .name("Samira")
                .email("samira@gmail.com")
                .build();
    }

    @Test
    void testSaveEmployee() {
        // Arrange
        when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);

        // Act
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // Assert
        assertNotNull(savedEmployee);
        assertEquals("Samira", savedEmployee.getName());
        assertEquals("samira@gmail.com", savedEmployee.getEmail());
    }

    @Test
    void testGetAllEmployees() {
        // Arrange
        List<Employee> employees = Arrays.asList(employee);
        when(employeeRepository.findAll()).thenReturn(employees);

        // Act
        List<Employee> result = employeeService.getAllEmployees();

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void testGetEmployeeById() {
        // Arrange
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        // Act
        Employee foundEmployee = employeeService.getEmployeeById(1);

        // Assert
        assertNotNull(foundEmployee);
        assertEquals(1, foundEmployee.getId());
    }

    @Test
    void testUpdateEmployee() {
        // Arrange
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);

        // Act
        Employee updatedEmployee = employeeService.updateEmployee(1, employee);

        // Assert
        assertNotNull(updatedEmployee);
        assertEquals("Samira", updatedEmployee.getName());
    }

    @Test
    void testDeleteEmployee() {
        // Arrange: Mock the repository to return an employee when searching by ID
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        // Arrange: Mock the repository to do nothing when deleting an employee
        doNothing().when(employeeRepository).deleteById(1);

        // Act: Call the deleteEmployee() method in the service layer, which is supposed to delete the employee
        assertDoesNotThrow(() -> employeeService.deleteEmployee(1));

        // Assert: Verify that deleteById() method was called exactly once
        verify(employeeRepository, times(1)).deleteById(1);
    }
}
