package com.example.employee_restapi.controller;
import com.example.employee_restapi.entity.Employee;
import com.example.employee_restapi.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmployeeControllerTestMockito {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    Employee emp1 = new Employee(1, "Ali", "ali@example.com");
    Employee emp2 = new Employee(2, "Sara", "sara@example.com");
    Employee emp3 = new Employee(3, "Reza", "reza@example.com");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void saveEmployee() throws Exception {
        Employee emp = new Employee(4, "Mina", "mina@example.com");
        Mockito.when(employeeService.saveEmployee(emp)).thenReturn(emp);

        String content = objectMapper.writeValueAsString(emp);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/emp/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Mina")))
                .andExpect(jsonPath("$.email", is("mina@example.com")));
    }

    @Test
    void getAllEmployees() throws Exception {
        List<Employee> employees = Arrays.asList(emp1, emp2, emp3);
        Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(MockMvcRequestBuilders.get("/emp/getall")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].name", is("Sara")));
    }

    @Test
    void getEmployeeById() throws Exception {
        Mockito.when(employeeService.getEmployeeById(2)).thenReturn(emp2);

        mockMvc.perform(MockMvcRequestBuilders.get("/emp/get/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("sara@example.com")));
    }

    @Test
    void updateEmployee() throws Exception {
        Employee updatedEmp = new Employee(1, "Ali Updated", "ali_updated@example.com");
        Mockito.when(employeeService.updateEmployee(1, updatedEmp)).thenReturn(updatedEmp);

        String content = objectMapper.writeValueAsString(updatedEmp);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/emp/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Ali Updated")))
                .andExpect(jsonPath("$.email", is("ali_updated@example.com")));
    }

    @Test
    void deleteEmployee() throws Exception {
        Mockito.doNothing().when(employeeService).deleteEmployee(2);

        mockMvc.perform(MockMvcRequestBuilders.delete("/emp/delete/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee deleted successfully."));
    }
}
