package com.example.employee_restapi.repository;

import com.example.employee_restapi.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void testSaveEmployee() {
        // Arrange: Create an employee object
        Employee employee = Employee.builder()
                .name("Samira")
                .email("samira@gmail.com")
                .build();

        // Act: Save the employee and retrieve the saved entity
        Employee savedEmployee = employeeRepository.save(employee);

        // Assert: Verify that the saved employee is not null and has a valid ID
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }
    @Test
    void testFindById() {
        // Arrange: Create and save an employee object
        Employee employee = Employee.builder()
                .name("Zahra")
                .email("zahra@gmail.com")
                .build();
        Employee savedEmployee = employeeRepository.save(employee);

        // Act: Retrieve the employee by its ID
        Optional<Employee> foundEmployee = employeeRepository.findById(savedEmployee.getId());

        // Assert: Verify that the employee is found and has the expected name
        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getName()).isEqualTo("Zahra");
    }
    @Test
    void testFindAllEmployees() {
        // Arrange: Create and save two employees
        Employee emp1 = Employee.builder().name("Samira").email("samira@gmail.com").build();
        Employee emp2 = Employee.builder().name("Zahra").email("zahra@gmail.com").build();
        employeeRepository.save(emp1);
        employeeRepository.save(emp2);

        // Act: Retrieve all employees from the repository
        List<Employee> employees = employeeRepository.findAll();

        // Assert: Verify that the list contains exactly 2 employees
        assertThat(employees).hasSize(2);
    }
    @Test
    void testDeleteEmployee() {
        // Arrange: Create and save a new employee
        Employee employee = Employee.builder()
                .name("Samira")
                .email("samira@gmail.com")
                .build();
        Employee savedEmployee = employeeRepository.save(employee);

        // Act: Delete the employee
        employeeRepository.deleteById(savedEmployee.getId());

        // Assert: Check that the employee is deleted
        Optional<Employee> deletedEmployee = employeeRepository.findById(savedEmployee.getId());
        assertThat(deletedEmployee).isEmpty();
    }

}
