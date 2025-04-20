package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.EmployeeDto;

public interface EmployeeService {
	public EmployeeDto addEmployee(EmployeeDto employeeDto);

	public List<EmployeeDto> getEmployees();

	public EmployeeDto getById(long employeeId);

	public String deleteById(long employeeId);

	public EmployeeDto deleteEmployeeById(long employeeId);

	public void delete(long employeeId);

	public EmployeeDto updateEmployee(EmployeeDto employeeDto);

	public EmployeeDto updateEmployeeByPassword(EmployeeDto employeeDto);
}
