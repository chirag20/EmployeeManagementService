
package com.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.model.Employee;
import com.employee.service.EmployeeService;

@RestController

@RequestMapping("employee")
public class EmployeeController {

	@Autowired
	EmployeeService empService;

	@GetMapping("/getEmployeeDetails/{id}")
	public ResponseEntity<Employee> getEmployeeDetails(@PathVariable int id) {
		Employee employeeData = empService.findEmployeeDetailsById(id).orElse(null);
		if (employeeData != null) {
			return new ResponseEntity<>(employeeData, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getAllEmployeeDetails")
	public ResponseEntity<List<Employee>> getAllEmployeeDetails() {
		List<Employee> employeeList = empService.findAllEmployeeDetails();

		if (employeeList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(employeeList, HttpStatus.OK);
	}

	@PostMapping("/createEmployee")
	public ResponseEntity<List<Employee>> createEmployees(@RequestBody List<Employee> employeeObj) {
		try {
			List<Employee> employeeList = empService.saveEmployeeDetails(employeeObj);
			return new ResponseEntity<>(employeeList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/deleteEmployee/{id}")
	public ResponseEntity<String> deleteEmployees(@PathVariable int id) {
		try {
			empService.deleteEmployeeDetails(id);
			return new ResponseEntity<>("Deleted successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/updateEmployeeDetails/{id}")
	public ResponseEntity<Employee> updateEmployees(@PathVariable int id, @RequestBody Employee employeeObj) {
		Employee employee;
		try {
			employee = empService.findEmployeeDetailsById(id).orElse(null);
			if (employee == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			employee.setName(employeeObj.getName());
			employee.setSalary(employeeObj.getSalary());
			employee.setDept(employeeObj.getDept());
			return new ResponseEntity<>(empService.updateEmployeeDetails(employee), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
