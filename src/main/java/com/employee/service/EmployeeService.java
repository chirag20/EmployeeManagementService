
package com.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.dao.EmployeeRepository;
import com.employee.model.Employee;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	public List<Employee> saveEmployeeDetails(List<Employee> employeeObj) {
		return employeeRepository.saveAll(employeeObj);
	}

	public Employee updateEmployeeDetails(Employee employeeObj) {
		return employeeRepository.save(employeeObj);
	}

	public List<Employee> findAllEmployeeDetails() {
		return employeeRepository.findAll();
	}

	public Optional<Employee> findEmployeeDetailsById(int id) {
		return employeeRepository.findById(id);
	}

	public void deleteEmployeeDetails(int id) {
		employeeRepository.deleteById(id);
	}

}
