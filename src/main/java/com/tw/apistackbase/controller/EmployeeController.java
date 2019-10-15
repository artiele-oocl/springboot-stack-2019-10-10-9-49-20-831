package com.tw.apistackbase.controller;

import com.tw.apistackbase.domain.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jxzhong on 18/08/2017.
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final Logger log = Logger.getLogger(this.getClass().getName());
    private List<Employee> employeeList = new ArrayList<>();

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeList);
    }
    @GetMapping(path = "/{id}", produces = {"application/json"})
    public ResponseEntity<Stream<Employee>> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeList.stream().filter(f-> f.getId().equals(id)));
    }
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity createEmployee(@RequestBody Employee employee) {
        employeeList.add(employee);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployeeInfo(@PathVariable Long id, @RequestBody Employee employee) {
        for (Employee employeeDB : employeeList) {
            if (employeeDB.getId().equals(id)) {
                employeeDB.setName(employee.getName());
                employeeDB.setAge(employee.getAge());
                employeeDB.setGender(employee.getGender());
            }
        }
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployeeById(@PathVariable Long id) {
        for (Employee employee : employeeList) {
            if (employee.getId().equals(id)) {
                employeeList.remove(employee);
                ResponseEntity.ok().build();
            }
        }
        return "Employee successfully deleted.";
    }

}
