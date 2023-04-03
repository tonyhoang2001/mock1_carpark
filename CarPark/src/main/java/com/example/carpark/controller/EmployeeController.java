package com.example.carpark.controller;

import com.example.carpark.entity.Employee;
import com.example.carpark.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping()
    public List<Employee> getAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return employeeService.getAll(page, size);
    }

    @GetMapping("/search")
    public List<Employee> searchByName(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                       @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        return employeeService.searchByName(page, size, name);
    }

    @GetMapping("/{id}")
    public Employee getEmpById(@PathVariable Long id) {
        return employeeService.getEmpById(id);
    }

    @PostMapping()
    public Employee insertEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.insertEmployee(employee);
    }

    @PutMapping
    public Employee updateEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmpById(@PathVariable Long id) {
        employeeService.deleteEmpById(id);
    }
}
