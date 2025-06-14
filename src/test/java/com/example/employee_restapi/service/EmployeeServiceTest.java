package com.example.employee_restapi.service;

import com.example.employee_restapi.entity.Employee;
import com.example.employee_restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Collections;
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
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
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
        when(employeeRepository.save(any(Employee.class))).thenAnswer
                (invocation -> invocation.getArgument(0));

        Employee updatedInfo = Employee.builder()
                .name("roz")
                .email("roz@gmail.com")
                .build();
        // Act
        Employee result = employeeService.updateEmployee(1, updatedInfo);
        // Assert
        assertEquals("roz", result.getName());
        assertEquals("roz@gmail.com", result.getEmail());
        verify(employeeRepository).save(any(Employee.class));
    }
    @Test
    void testDeleteEmployee() {
        // Arrange
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        //Act
        doNothing().when(employeeRepository).deleteById(1);
        // Assert
        assertDoesNotThrow(() -> employeeService.deleteEmployee(1));
        verify(employeeRepository, times(1)).deleteById(1);
    }
    @Test
    void testGetEmployeeById_WhenNotFound_ShouldThrowRuntimeException() {
        //arrange
        when(employeeRepository.findById(999)).thenReturn(Optional.empty());
        //act
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> employeeService.getEmployeeById(999));
        //assert
        assertEquals("Employee Not Found", exception.getMessage());
    }
    @Test
    void testUpdateEmployee_WhenNotFound_ShouldThrowRuntimeException() {
        //arrange
        when(employeeRepository.findById(999)).thenReturn(Optional.empty());
        //act
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> employeeService.updateEmployee(999, employee));
        //assert
        assertEquals("Employee Not Found With Id : 999", exception.getMessage());
    }
    @Test
    void testDeleteEmployee_WhenNotFound_ShouldThrowRuntimeException() {
        //arrange
        when(employeeRepository.findById(999)).thenReturn(Optional.empty());
        //act
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> employeeService.deleteEmployee(999));
        //assert
        assertEquals("Employee Not Found With Id : 999", exception.getMessage());
    }
    @Test
    void testGetAllEmployees_WhenEmpty_ShouldReturnEmptyList() {
        //arrange
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());
        //act
        List<Employee> result = employeeService.getAllEmployees();
        //assert
        assertTrue(result.isEmpty());
    }
}
