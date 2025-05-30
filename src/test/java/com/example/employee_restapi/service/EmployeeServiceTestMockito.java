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
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        // Act
        Employee savedEmployee = employeeService.saveEmployee(employee);
        // Assert
        assertEquals(employee.getName(), savedEmployee.getName());
        assertEquals(employee.getEmail(), savedEmployee.getEmail());
        Mockito.verify(employeeRepository, Mockito.times(1)).save(employee);
    }
    @Test
    void findAll() {
        // Arrange
        List<Employee> employees = Arrays.asList(
                Employee.builder().id(1).name("Samira").email("samira@gmail.com").build());
        Mockito.when(employeeRepository.findAll()).thenReturn(employees);
        // Act
        List<Employee> result = employeeService.getAllEmployees();
        // Assert
        assertEquals(employees.size(), result.size());
        Mockito.verify(employeeRepository, Mockito.times(1)).findAll();
    }
    @Test
    void findById() {
        // Arrange
        int id = 1;
        Employee employee = Employee.builder().id(id).name("Samira").email("samira@gmail.com").build();
        Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        // Act
        Employee found = employeeService.getEmployeeById(id);
        // Assert
        assertEquals(employee.getName(), found.getName());
        assertEquals(employee.getEmail(), found.getEmail());
        Mockito.verify(employeeRepository, Mockito.times(1)).findById(id);
    }
    @Test
    void update(){
        // Arrange
        int id = 1;
        Employee existing = Employee.builder().id(id).name("Samira").email("samira@gmail.com").build();
        Employee updated = Employee.builder().name("Roz").email("roz@gmail.com").build();
        Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.of(existing));
        Mockito.when(employeeRepository.save(existing)).thenReturn(existing);
        // Act
        Employee result = employeeService.updateEmployee(id, updated);
        // Assert
        assertEquals(updated.getName(), result.getName());
        assertEquals(updated.getEmail(), result.getEmail());
        Mockito.verify(employeeRepository, Mockito.times(1)).findById(id);
        Mockito.verify(employeeRepository, Mockito.times(1)).save(existing);
    }
    @Test
    void delete() {
        // Arrange
        int id = 1;
        Employee employee = Employee.builder().id(id).name("Samira").email("samira@gmail.com").build();
        Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        // Act
        employeeService.deleteEmployee(id);
        // Assert
        Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(id);
    }
}
