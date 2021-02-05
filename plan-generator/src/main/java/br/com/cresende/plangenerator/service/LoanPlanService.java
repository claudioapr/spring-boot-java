package br.com.cresende.plangenerator.service;

import br.com.cresende.plangenerator.dto.LoanPlanRequest;
import br.com.cresende.plangenerator.exception.InvalidInputForInstallmentCalculationException;
import br.com.cresende.plangenerator.model.LoanPlan;

public interface LoanPlanService {

  LoanPlan generateLoanPlan(LoanPlanRequest request)
      throws InvalidInputForInstallmentCalculationException;
}
