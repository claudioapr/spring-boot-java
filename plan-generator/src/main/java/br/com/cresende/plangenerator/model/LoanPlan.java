package br.com.cresende.plangenerator.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

public final class LoanPlan {

  private final BigDecimal loanAmount;
  private final BigDecimal nominalRate;
  private final Integer duration;
  private final ZonedDateTime startDate;

  private final List<LoanPlanInstallment> installments;

  public LoanPlan(BigDecimal loanAmount, BigDecimal nominalRate, Integer duration,
      ZonedDateTime startDate,
      List<LoanPlanInstallment> installments) {
    this.loanAmount = loanAmount;
    this.nominalRate = nominalRate;
    this.duration = duration;
    this.startDate = startDate;
    this.installments = Collections.unmodifiableList(installments);

  }

  public BigDecimal getLoanAmount() {
    return loanAmount;
  }

  public BigDecimal getNominalRate() {
    return nominalRate;
  }

  public Integer getDuration() {
    return duration;
  }

  public ZonedDateTime getStartDate() {
    return startDate;
  }

  public List<LoanPlanInstallment> getInstallments() {
    return installments;
  }
}
