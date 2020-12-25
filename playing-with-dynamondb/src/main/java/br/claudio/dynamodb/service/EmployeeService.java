package br.claudio.dynamodb.service;

import br.claudio.dynamodb.dto.EmployeeDTO;
import br.claudio.dynamodb.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(EmployeeDTO employeeDTO);
    List<Employee> findAllEmployees();
    List<Employee> findByCompanyName(String companyName);
    Employee updateEmployee(String companyDocumentNumber, EmployeeDTO employeeDTO);
    Employee disableEmployee(String companyDocumentNumber);
}
