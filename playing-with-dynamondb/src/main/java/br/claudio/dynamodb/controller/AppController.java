package br.claudio.dynamodb.controller;

import br.claudio.dynamodb.dto.EmployeeDTO;
import br.claudio.dynamodb.model.Employee;
import br.claudio.dynamodb.service.EmployeeService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class AppController {

    private final EmployeeService costumerService;

    public AppController(EmployeeService costumerService) {
        this.costumerService = costumerService;
    }

    @PostMapping("costumer")
    public ResponseEntity<Employee> newemployee(@Valid @RequestBody EmployeeDTO costumerDTO) {
        return new ResponseEntity(costumerService.saveEmployee(costumerDTO), HttpStatus.OK);
    }

    @GetMapping("costumer")
    public ResponseEntity<List<Employee>> findemployeeByName(@Param("companyName") String companyName) {
        return ResponseEntity.ok(costumerService.findByCompanyName(companyName));
    }

    @GetMapping("costumer/all")
    public ResponseEntity<List<Employee>> allemployees() {
        return ResponseEntity.ok(costumerService.findAllEmployees());
    }

    @PutMapping("costumer/{companyDocumentNumber}")
    public ResponseEntity<Employee> updateemployee(
            @PathVariable("companyDocumentNumber") String companyDocumentNumber,
            @Valid @RequestBody EmployeeDTO costumerDTO
    ) {
        return ResponseEntity.ok(costumerService.updateEmployee(companyDocumentNumber, costumerDTO));
    }

    @DeleteMapping("costumer/{companyDocumentNumber}")
    public ResponseEntity<Employee> disableemployee(@PathVariable("companyDocumentNumber") String companyDocumentNumber) {
        return ResponseEntity.ok(costumerService.disableEmployee(companyDocumentNumber));
    }

}