
package com.employee.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.employee.model.Employee;

@RunWith(SpringRunner.class)

@SpringBootTest

@ActiveProfiles("test")
public class EmployeeServiceTest {

	@Autowired
	private EmployeeService employeeService;

	@Test
	public void findAllEmployeeDetails() {
		assertNotEquals(0, employeeService.findAllEmployeeDetails().size());
	}

	@Test
	public void findEmployeeDetailsById() {
		List<Employee> employeeList=employeeService.findAllEmployeeDetails();
		int id=1;
		String name="";
		if(employeeList != null)
		{
			 id=employeeList.stream().findFirst().get().getId();
			 name=employeeList.stream().findFirst().get().getName();
		}
		Employee employee = employeeService.findEmployeeDetailsById(id).orElse(null);
		assertEquals(name, employee.getName());
	}

	@Test
	public void deleteEmployeeDetails() {
		int id = 2;
		employeeService.deleteEmployeeDetails(id);
		assertNull(employeeService.findEmployeeDetailsById(id).orElse(null));
	}

	@Test
	public void saveEmployeeDetails() {
		List<Employee> employeeList = Stream
				.of(new Employee("chirag", 10000L, "Dept1"), new Employee("employee2", 10000L, "Dept2"))
				.collect(Collectors.toList());

		assertEquals(2, (employeeService.saveEmployeeDetails(employeeList).size()));
	}

	@Test
	public void updateEmployeeDetails() {
		Employee employee = new Employee(5, "chirag", 100000L, "CS");
		assertEquals(employee, employeeService.updateEmployeeDetails(employee));
	}

}
