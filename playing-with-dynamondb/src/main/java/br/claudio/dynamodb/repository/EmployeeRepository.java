package br.claudio.dynamodb.repository;

import br.claudio.dynamodb.model.Employee;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface EmployeeRepository extends CrudRepository<Employee, String> {
    List<Employee> findByCompanyName(String companyName);
    Optional<Employee> findByCompanyDocumentNumber(String companyDocumentNumber);
}