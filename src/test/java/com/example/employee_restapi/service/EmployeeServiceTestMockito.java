package com.example.employee_restapi.service;
import com.example.employee_restapi.entity.Employee;
import com.example.employee_restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTestMockito {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void save() {
        // Arrange
        Employee employee = Employee.builder().name("Samira").email("samira@gmail.com").build();
        when(employeeRepository.save(employee)).thenReturn(employee);
        // Act
        Employee savedEmployee = employeeService.saveEmployee(employee);
        // Assert
        assertEquals(employee.getName(), savedEmployee.getName());
        assertEquals(employee.getEmail(), savedEmployee.getEmail());
        verify(employeeRepository, Mockito.times(1)).save(employee);
    }
    @Test
    void findAll() {
        // Arrange
        List<Employee> employees = Arrays.asList(
                Employee.builder().id(1).name("Samira").email("samira@gmail.com").build());
        when(employeeRepository.findAll()).thenReturn(employees);
        // Act
        List<Employee> result = employeeService.getAllEmployees();
        // Assert
        assertEquals(employees.size(), result.size());
        verify(employeeRepository, Mockito.times(1)).findAll();
    }
    @Test
    void findById() {
        // Arrange
        int id = 1;
        Employee employee = Employee.builder().id(id).name("Samira").email("samira@gmail.com").build();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        // Act
        Employee found = employeeService.getEmployeeById(id);
        // Assert
        assertEquals(employee.getName(), found.getName());
        assertEquals(employee.getEmail(), found.getEmail());
        verify(employeeRepository, Mockito.times(1)).findById(id);
    }
    @Test
    void update(){
        // Arrange
        int id = 1;
        Employee existing = Employee.builder().id(id).name("Samira").email("samira@gmail.com").build();
        Employee updated = Employee.builder().name("Roz").email("roz@gmail.com").build();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(existing));
        when(employeeRepository.save(existing)).thenReturn(existing);
        // Act
        Employee result = employeeService.updateEmployee(id, updated);
        // Assert
        assertEquals(updated.getName(), result.getName());
        assertEquals(updated.getEmail(), result.getEmail());
        verify(employeeRepository, Mockito.times(1)).findById(id);
        verify(employeeRepository, Mockito.times(1)).save(existing);
    }
    @Test
    void delete() {
        // Arrange
        int id = 1;
        Employee employee = Employee.builder().id(id).name("Samira").email("samira@gmail.com").build();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        // Act
        employeeService.deleteEmployee(id);
        // Assert
        verify(employeeRepository, Mockito.times(1)).deleteById(id);
    }
    @Test
    void findById_WhenEmployeeNotFound_ShouldThrowRuntimeException() {
        //arrange
        int nonExistentId = 999;
        when(employeeRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        //act
        Exception exception = assertThrows(RuntimeException.class,
                () -> employeeService.getEmployeeById(nonExistentId));
        //assert
        assertEquals("Employee Not Found", exception.getMessage());
        verify(employeeRepository, times(1)).findById(nonExistentId);
    }
    @Test
    void update_WhenEmployeeNotFound_ShouldThrowRuntimeException() {
        //arrange
        int nonExistentId = 999;
        when(employeeRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Employee updateData = Employee.builder().name("joe").build();
        //act
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> employeeService.updateEmployee(nonExistentId, updateData));
        //assert
        assertEquals("Employee Not Found With Id : " + nonExistentId, exception.getMessage());
        verify(employeeRepository, times(1)).findById(nonExistentId);
        verify(employeeRepository, never()).save(any());
    }
    @Test
    void update_WithNullEmployee_ShouldThrowNullPointerException() {
        //arrange
        int existingId = 1;
        when(employeeRepository.findById(existingId))
                .thenReturn(Optional.of(Employee.builder().id(existingId).build()));
        //act
        assertThrows(NullPointerException.class,
                () -> employeeService.updateEmployee(existingId, null));
        //assert
        verify(employeeRepository, times(1)).findById(existingId);
    }
    @Test
    void delete_WhenEmployeeNotFound_ShouldThrowRuntimeException() {
        //arrange
        int nonExistentId = 999;
        when(employeeRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        //act
        Exception exception = assertThrows(RuntimeException.class,
                () -> employeeService.deleteEmployee(nonExistentId));
        //assert
        assertEquals("Employee Not Found With Id : " + nonExistentId, exception.getMessage());
        verify(employeeRepository, times(1)).findById(nonExistentId);
        verify(employeeRepository, never()).deleteById(any());
    }
    @Test
    void findAll_WhenNoEmployees_ShouldReturnEmptyList() {
        //arrange
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());
        //act
        List<Employee> result = employeeService.getAllEmployees();
        //assert
        assertTrue(result.isEmpty());
        verify(employeeRepository, times(1)).findAll();
    }
}
