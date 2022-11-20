package com.employee.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.employee.model.Employee;
import com.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private EmployeeService service;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

	}

	ObjectMapper om = new ObjectMapper();

	@Test
	public void getEmployeeDetails() throws Exception {
		System.out.println(service.findAllEmployeeDetails());
		MvcResult result = mockMvc
				.perform(get("/employee/getAllEmployeeDetails").content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}

	@Test
	public void findEmployeeById_Found() throws Exception {
		int id = 5;
		System.out.println(service.findAllEmployeeDetails());
		MvcResult result = mockMvc
				.perform(get("/employee/getEmployeeDetails/{id}", id).content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}

	@Test
	public void findEmployeeById_Not_Present() throws Exception {
		int id = 10;
		System.out.println(service.findAllEmployeeDetails());
		MvcResult result = mockMvc
				.perform(get("/employee/getEmployeeDetails/{id}", id).content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn();
		assertEquals(404, result.getResponse().getStatus());
	}

	@Test
	public void createEmployee() throws Exception {
		List<Employee> employeeList = Stream
				.of(new Employee("chirag", 10000L, "Dept1"), new Employee("employee2", 10000L, "Dept2"),
						new Employee("employee3", 10000L, "Dept3"), new Employee("employee4", 10000L, "Dept2"))
				.collect(Collectors.toList());
		String inputJson = om.writeValueAsString(employeeList);
		System.out.println(service.findAllEmployeeDetails());
		MvcResult result = mockMvc.perform(post("/employee/createEmployee").content(inputJson) // change applied
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}

	@Test
	public void deleteEmployee() throws Exception {
		int id = 1;
		MvcResult result = mockMvc
				.perform(delete("/employee/deleteEmployee/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andReturn();
		assertEquals(204, result.getResponse().getStatus());
	}

	@Test
	public void updateEmployees() throws Exception {
		int id = 3;
		Employee employee = new Employee("chirag", 10000L, "Dept1");
		String inputJson = om.writeValueAsString(employee);
		MvcResult result = mockMvc.perform(put("/employee/updateEmployeeDetails/{id}", id).content(inputJson) // change
																												// applied
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}

}
