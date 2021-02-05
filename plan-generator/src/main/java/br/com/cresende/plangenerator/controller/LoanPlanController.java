package br.com.cresende.plangenerator.controller;

import br.com.cresende.plangenerator.dto.LoanPlanRequest;
import br.com.cresende.plangenerator.model.LoanPlanInstallment;
import br.com.cresende.plangenerator.service.LoanPlanService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cresende
 */
@RestController
public class LoanPlanController {

  private final LoanPlanService service;

  @Autowired
  public LoanPlanController(LoanPlanService service) {
    this.service = service;
  }

  @PostMapping(value = "generate-plan")
  public List<LoanPlanInstallment> generatePlan(@Valid @RequestBody final LoanPlanRequest request) {
    return service.generateLoanPlan(request).getInstallments();
  }
}
