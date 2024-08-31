package com.javidproject.example;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class EmployeeService {

    private static final int MAX_EMPLOYEES = 100;
    private final Set<Employee> employees = new HashSet<>();

    public Employee addEmployee(String firstName, String lastName) {
        if (employees.size() >= MAX_EMPLOYEES) {
            throw new EmployeeStorageIsFullException("Maximum number of employees reached.");
        }
        Employee employee = new Employee(firstName, lastName);
        if (!employees.add(employee)) {
            throw new EmployeeAlreadyAddedException("Employee already exists.");
        }
        return employee;
    }

    public Employee removeEmployee(String firstName, String lastName) {
        Employee employee = findEmployee(firstName, lastName);
        if (!employees.remove(employee)) {
            throw new EmployeeNotFoundException("Employee not found.");
        }
        return employee;
    }

    public Employee findEmployee(String firstName, String lastName) {
        return employees.stream()
                .filter(e -> e.getFirstName().equals(firstName) && e.getLastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found."));
    }

    public Collection<Employee> getAllEmployees() {
        return employees;
    }
}
