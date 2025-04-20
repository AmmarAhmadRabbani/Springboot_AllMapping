package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.entity.Employees;
import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public EmployeeDto addEmployee(EmployeeDto employeeDto) {
		if (employeeDto != null) {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			String encode = bCryptPasswordEncoder.encode(employeeDto.getPassword());
			employeeDto.setPassword(encode);

			Employees employees = new Employees();
			BeanUtils.copyProperties(employeeDto, employees);

			Employees saveEmployees = employeeRepository.save(employees);
			BeanUtils.copyProperties(saveEmployees, employeeDto);
			return employeeDto;
		}
		throw new EmployeeNotFoundException("Employee Unavailable");
	}

	@Override
	public List<EmployeeDto> getEmployees() {
		List<EmployeeDto> employeeDtosList = new ArrayList<>();
		List<Employees> emploList = employeeRepository.findAll();
		if (emploList.isEmpty()) {
			throw new EmployeeNotFoundException("Employees Not Available");
		}
		emploList.forEach(i -> {
			EmployeeDto employeeDto = new EmployeeDto();
			BeanUtils.copyProperties(i, employeeDto);
			employeeDtosList.add(employeeDto);
		});
		return employeeDtosList;
	}

	@Override
	public EmployeeDto getById(long employeeId) {
		Employees employees = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException("Invalid Employee Id"));
		EmployeeDto employeeDto = new EmployeeDto();
		BeanUtils.copyProperties(employees, employeeDto);
		return employeeDto;
	}

	@Override
	public String deleteById(long employeeId) {
		employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("In Valid Id"));
		employeeRepository.deleteById(employeeId);
		return "Deleted Successfully";
	}

	@Override
	public EmployeeDto deleteEmployeeById(long employeeId) {
		employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Invalid Id"));
		employeeRepository.deleteById(employeeId);
		return new EmployeeDto();
	}

	@Override
	public void delete(long employeeId) {
		employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Not Deleted"));
		employeeRepository.deleteById(employeeId);
	}

	@Override
	public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
		Employees employees = employeeRepository.findById(employeeDto.getEmployeeId())
				.orElseThrow(() -> new EmployeeNotFoundException("Employee Not Available"));
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encode = bCryptPasswordEncoder.encode(employeeDto.getPassword());
		employeeDto.setPassword(encode);
		BeanUtils.copyProperties(employeeDto, employees);
		employeeRepository.save(employees);
		BeanUtils.copyProperties(employees, employeeDto);
		return employeeDto;
	}

	@Override
	public EmployeeDto updateEmployeeByPassword(EmployeeDto employeeDto) {
		Employees employee = employeeRepository.findById(employeeDto.getEmployeeId())
				.orElseThrow(() -> new EmployeeNotFoundException("Employee Not Available"));
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		if (bCryptPasswordEncoder.matches(employeeDto.getPassword(), employee.getPassword())) {
			String encode = bCryptPasswordEncoder.encode(employeeDto.getPassword());
			employeeDto.setPassword(encode);
			BeanUtils.copyProperties(employeeDto, employee);
			employeeRepository.save(employee);
			BeanUtils.copyProperties(employee, employeeDto);
			return employeeDto;
		}

		throw new EmployeeNotFoundException("Password Mismatch");
	}

}
