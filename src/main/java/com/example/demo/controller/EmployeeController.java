package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/employees")
@RestController
public class EmployeeController {
    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping
    public void createEmployee(@RequestBody Employee employee) {
        employeeRepository.save(employee);
    }

    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable("id") Integer id, @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid employee ID: " + id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setJobTitle(employeeDetails.getJobTitle());

        employeeRepository.save(employee);
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid employee ID: " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable("id") Integer id) {
        employeeRepository.deleteById(id);
    }

    @GetMapping
    public Iterable<Employee> listAllEmployees() {
        return employeeRepository.findAllByOrderByIdAsc();
    }
}
