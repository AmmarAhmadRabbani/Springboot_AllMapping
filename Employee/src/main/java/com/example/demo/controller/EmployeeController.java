package com.example.demo.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.response.SuccessResponse;
import com.example.demo.service.EmployeeService;

@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/addEmployee")
	public ResponseEntity<SuccessResponse> addEmployee(@RequestBody EmployeeDto employeeDto) {
		EmployeeDto employee = employeeService.addEmployee(employeeDto);
		if (employee != null) {
			return new ResponseEntity<>(new SuccessResponse(false, "Added Successfully", employee), HttpStatus.OK);
		}
		return new ResponseEntity<>(new SuccessResponse(true, "Adding Failed", null), HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/getEmployees")
	public ResponseEntity<SuccessResponse> getEmployees() {
		List<EmployeeDto> employees = employeeService.getEmployees();
		if (employees.isEmpty()) {
			return new ResponseEntity<>(new SuccessResponse(true, "Getting failed", null), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new SuccessResponse(false, "Successful", employees), HttpStatus.OK);
	}

	@GetMapping("/getEmployee/{employeeId}")
	public ResponseEntity<SuccessResponse> getById(@PathVariable long employeeId) {
		EmployeeDto byId = employeeService.getById(employeeId);
		if (byId != null) {
			return new ResponseEntity<>(new SuccessResponse(false, "Successful", byId), HttpStatus.OK);
		}
		return new ResponseEntity<>(new SuccessResponse(true, "Un Sucessful", null), HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/deleteEmployee/{employeeId}")
	public ResponseEntity<SuccessResponse> deleteById(@PathVariable long employeeId) {
		String deleteById = employeeService.deleteById(employeeId);
		if (deleteById.isEmpty()) {
			return new ResponseEntity<>(new SuccessResponse(true, "Unable to delete", null), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new SuccessResponse(false, "Deleted", deleteById), HttpStatus.OK);
	}

	@DeleteMapping("/DeleteEmployee/{employeeId}")
	public ResponseEntity<SuccessResponse> deleteEmployeeById(@PathVariable long employeeId) {
		EmployeeDto deleteEmployeeById = employeeService.deleteEmployeeById(employeeId);
		if (deleteEmployeeById != null) {
			return new ResponseEntity<>(new SuccessResponse(false, "Deleted", deleteEmployeeById), HttpStatus.OK);
		}
		return new ResponseEntity<>(new SuccessResponse(true, "Not Deleted", null), HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/Delete/{employeeId}")
	public ResponseEntity<SuccessResponse> delete(@PathVariable long employeeId) {
		employeeService.delete(employeeId);
		return new ResponseEntity<>(new SuccessResponse(false, "Deleted", null), HttpStatus.OK);

	}

	@PutMapping("/UpdateEmployee/{employeeId}")
	public ResponseEntity<SuccessResponse> updateEmployee(@RequestBody EmployeeDto employeeDto) {
		EmployeeDto updateEmployee = employeeService.updateEmployee(employeeDto);
		if (updateEmployee != null) {
			return new ResponseEntity<>(new SuccessResponse(false, "Upadted", updateEmployee), HttpStatus.OK);
		}
		return new ResponseEntity<>(new SuccessResponse(true, "Not Updated", null), HttpStatus.BAD_REQUEST);
	}

}
