package com.example.employee_restapi;

import com.example.employee_restapi.entity.Employee;
import com.example.employee_restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;

// This is an integration test that tests the full stack, including the controller, service, and repository layers.
// It simulates HTTP requests and verifies the responses, with real database interactions (rolled back after tests).
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
        employee = employeeRepository.save
                (new Employee(null, "Samira", "samira@gmail.com"));
    }
    @Test
    void testCreateEmployee() throws Exception {
        // Arrange
        Employee newEmployee = new Employee
                (null, "Samira", "samira@gmail.com");
        // Act
        mockMvc.perform(post("/emp/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Samira"))
                .andExpect(jsonPath("$.email").value("samira@gmail.com"))
                .andDo(print());
    }
    @Test
    void testGetAllEmployees() throws Exception {

        mockMvc.perform(get("/emp/getall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andDo(print());
    }
    @Test
    void testGetEmployeeById() throws Exception {

        mockMvc.perform(get("/emp/get/" + employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Samira"))
                .andDo(print());
    }

    @Test
    void testUpdateEmployee() throws Exception {
        // Arrange
        Employee updatedEmployee = new Employee(null, "samira", "samira3@gmail.com");
        //Act & Assert
        mockMvc.perform(put("/emp/update/" + employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("samira"))
                .andDo(print());
    }
    @Test
    void testDeleteEmployee() throws Exception{

        mockMvc.perform(delete("/emp/delete/" + employee.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

}