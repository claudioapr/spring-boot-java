package br.com.cresende.plangenerator.service.impl;

import br.com.cresende.plangenerator.configuration.LoanPlanConfiguration;
import br.com.cresende.plangenerator.dto.LoanPlanRequest;
import br.com.cresende.plangenerator.exception.InvalidInputForInstallmentCalculationException;
import br.com.cresende.plangenerator.model.LoanPlan;
import br.com.cresende.plangenerator.model.LoanPlanInstallment;
import br.com.cresende.plangenerator.service.LoanPlanService;
import br.com.cresende.plangenerator.util.LoanPlanCalculator;
import br.com.cresende.plangenerator.util.Utils;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cresende
 */
@Service
public class LoanPlanServiceImpl implements LoanPlanService {

  private static final Logger LOG = LoggerFactory.getLogger(LoanPlanServiceImpl.class);
  private final LoanPlanConfiguration configuration;
  private final LoanPlanCalculator calculator;

  @Autowired
  public LoanPlanServiceImpl(
      LoanPlanConfiguration configuration, LoanPlanCalculator calculator) {
    this.configuration = configuration;
    this.calculator = calculator;
  }

  @Override
  public LoanPlan generateLoanPlan(LoanPlanRequest request) {

    LOG.info("Generating Loan Plan with configuration {} and information ", configuration, request);
    List<LoanPlanInstallment> installments = generateInstallments(request.getLoanAmount(),
        request.getNominalRate(),
        request.getDuration(), request.getStartDate());

    return new LoanPlan(request.getLoanAmount(), request.getNominalRate(),
        request.getDuration(), request.getStartDate(), installments);
  }

  public List<LoanPlanInstallment> generateInstallments(BigDecimal loanAmount,
      BigDecimal nominalRate, Integer duration, ZonedDateTime startDate) {

    validateGenerateInstallmentsInputs(loanAmount,
        nominalRate, duration, startDate);

    final List<LoanPlanInstallment> installments = new ArrayList<>();

    final BigDecimal interestRate = calculator
        .calcMonthlyRateFromAnnualRate(nominalRate, configuration.getDaysInMonth(),
            configuration.getDaysInYear());

    final BigDecimal annuity = calculator.calcAnnuity(duration, interestRate, loanAmount);

    BigDecimal remainingOutstandingPrincipal = loanAmount;
    for (int i = 0; i < duration; i++) {

      BigDecimal initialOutstandingPrincipal = remainingOutstandingPrincipal;

      BigDecimal interest = calculator
          .calcInterest(nominalRate, configuration.getDaysInMonth(), configuration.getDaysInYear(),
              initialOutstandingPrincipal);

      BigDecimal principal = calculator.calcPrincipal(annuity, interest);

      if (principal.compareTo(initialOutstandingPrincipal) > 0) {
        principal = initialOutstandingPrincipal;
      }
      remainingOutstandingPrincipal = remainingOutstandingPrincipal.subtract(principal);

      installments.add(
          new LoanPlanInstallment(principal.add(interest), initialOutstandingPrincipal, interest,
              principal,
              remainingOutstandingPrincipal, startDate.truncatedTo(ChronoUnit.DAYS).plusMonths(i)));

    }
    return installments;

  }

  private void validateGenerateInstallmentsInputs(BigDecimal loanAmount, BigDecimal nominalRate,
      Integer duration, ZonedDateTime startDate)
      throws InvalidInputForInstallmentCalculationException {

    try {
      Utils.checkForNull(loanAmount, nominalRate, startDate, duration);
    } catch (IllegalArgumentException ex) {
      throw new InvalidInputForInstallmentCalculationException(
          "One of the provided field is null, and for the installment calculation is necessary all of them to have value");
    }
  }
}
