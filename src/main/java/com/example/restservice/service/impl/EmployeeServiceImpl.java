package com.example.restservice.service.impl;

import com.example.restservice.dao.EmployeeRepository;
import com.example.restservice.entity.Employee;
import com.example.restservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    // quick and dirty: inject employee dao (use constructor injection)
    private EmployeeRepository employeeDAO;

    @Autowired
    EmployeeServiceImpl(EmployeeRepository employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    @Override
    public Employee findById(int theId) {

        Optional<Employee> employee = employeeDAO.findById(theId);
        if (employee.isPresent()) {
           return employee.get();
        } else
            throw new RuntimeException("Did not find employee");
    }

    @Override
    public void save(Employee employee) {
        employeeDAO.save(employee);
    }

    @Override
    public void deleteById(int theId) {
        employeeDAO.deleteById(theId);
    }
}
