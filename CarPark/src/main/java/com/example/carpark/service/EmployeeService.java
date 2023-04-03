package com.example.carpark.service;

import com.example.carpark.entity.Employee;
import com.example.carpark.repository.EmployeeRepo;
import com.example.carpark.validation.ValidateInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ValidateInput validateInput;

    public List<Employee> getAll(Integer page, Integer size) {

        if (page <= 0 || size <= 0) {
            throw new RuntimeException("Invalid page!");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        List<Employee> list = employeeRepo.getAll(pageable).getContent();

        if (list.isEmpty() || list == null) {
            throw new NoSuchElementException("There's no employee!");
        }
        return list;
    }

    public List<Employee> searchByName(Integer page, Integer size, String name) {

        if (page <= 0 || size <= 0) {
            throw new RuntimeException("Invalid page!");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        List<Employee> list = employeeRepo.searchByName(name, pageable).getContent();

        if (list.isEmpty() || list == null) {
            throw new NoSuchElementException("There's no employee!");
        }
        return list;
    }

    public Employee getEmpById(Long id) {
        Employee employee = employeeRepo.getEmpById(id);
        if (employee == null) {
            throw new NullPointerException("There's no employee!");
        }
        return employee;
    }

    public Employee insertEmployee(Employee employee) {
        validateInput.validatePassword(employee.getPassword());
        validateInput.validateDOB(employee.getEmployeeBirthdate());
        validateInput.validatePhone(employee.getEmployeePhone());

        List<Employee> list = employeeRepo.findAll();
        Long countAccount = list.stream().filter(e -> employee.getAccount().equalsIgnoreCase(e.getAccount())).count();
        if (countAccount != 0) {
            throw new RuntimeException("There's existed employee with account: " + employee.getAccount());
        }

        Long countEmail = list.stream().filter(e -> employee.getEmployeeEmail().equalsIgnoreCase(e.getEmployeeEmail())).count();
        if (countEmail != 0) {
            throw new RuntimeException("There's existed employee with email: " + employee.getEmployeeEmail());
        }

        try {
            employeeRepo.insertEmployee(employee.getAccount(), employee.getDepartment(), employee.getEmployeeAddress(), employee.getEmployeeBirthdate(),
                    employee.getEmployeeEmail(), employee.getEmployeeName(), employee.getEmployeePhone(), employee.getPassword(), employee.getSex());
            return employeeRepo.getInsertedEmployee();
        } catch (RuntimeException e) {
            throw new RuntimeException("Inserting fail!");
        }
    }

    public Employee updateEmployee(Employee employee) {
        if (validateInput.validatePassword(employee.getPassword()) && validateInput.validateDOB(employee.getEmployeeBirthdate())
                && validateInput.validatePhone(employee.getEmployeePhone())) {
            Optional<Employee> e = employeeRepo.findById(employee.getEmployeeId());
            if (e.isPresent()) {
                try {
                    employeeRepo.updateEmployee(employee.getEmployeeName(), employee.getEmployeePhone(), employee.getEmployeeBirthdate(), employee.getSex(), employee.getEmployeeAddress(),
                            employee.getEmployeeEmail(), employee.getAccount(), employee.getPassword(), employee.getDepartment(), employee.getEmployeeId());
                } catch (RuntimeException ex) {
                    throw new RuntimeException("Updating fail!");
                }
            } else {
                throw new NullPointerException("Not exist employee with ID: " + employee.getEmployeeId() + " for updating!");
            }
        }
        return employeeRepo.findById(employee.getEmployeeId()).get();
    }

    public void deleteEmpById(Long id) {
        Optional<Employee> e = employeeRepo.findById(id);
        if (e.isPresent()) {
            try {
                employeeRepo.deleteEmpById(id);
                return;
            } catch (RuntimeException ex) {
                throw new RuntimeException("Deleting fail!");
            }
        }
        throw new NullPointerException("Not exist employee with ID: " + id + " for deleting!");
    }

}
