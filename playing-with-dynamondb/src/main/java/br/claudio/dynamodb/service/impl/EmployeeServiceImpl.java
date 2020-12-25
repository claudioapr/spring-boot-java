package br.claudio.dynamodb.service.impl;

import br.claudio.dynamodb.dto.EmployeeDTO;
import br.claudio.dynamodb.model.Employee;
import br.claudio.dynamodb.repository.EmployeeRepository;
import br.claudio.dynamodb.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(EmployeeDTO employeeDTO) {
        if(employeeRepository.findByCompanyDocumentNumber(employeeDTO.getCompanyDocumentNumber()).isPresent()) {
            throw new RuntimeException("Employee already registered");
        }
        return employeeRepository.save(employeeDTO.employeeDTOToEmployee());
    }

    @Override
    public List<Employee> findAllEmployees() {
        return (List<Employee>) employeeRepository.findAll();
    }

    @Override
    public List<Employee> findByCompanyName(String companyName) {
        return employeeRepository.findByCompanyName(companyName);
    }

    @Override
    public Employee updateEmployee(String companyDocumentNumber, EmployeeDTO employeeDTO) {
        Optional<Employee> employee =
                employeeRepository.findByCompanyDocumentNumber(companyDocumentNumber);

        if(employee.isEmpty()) {
            throw new RuntimeException("There is no employee");
        }

        BeanUtils.copyProperties(employeeDTO, employee.get(), "active", "id");

        return employeeRepository.save(employee.get());
    }

    @Override
    public Employee disableEmployee(String companyDocumentNumber) {
        Optional<Employee> employee =
                employeeRepository.findByCompanyDocumentNumber(companyDocumentNumber);

        if(employee.isEmpty()) {
            throw new RuntimeException("There is no employee");
        }

        employee.get().setActive(false);

        return employeeRepository.save(employee.get());
    }

}
