package br.com.cresende.plangenerator.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public final class LoanPlanInstallment {

  private final BigDecimal borrowerPaymentAmount;
  private final BigDecimal initialOutstandingPrincipal;
  private final BigDecimal interest;
  private final BigDecimal principal;
  private final BigDecimal remainingOutstandingPrincipal;
  private final ZonedDateTime date;

  public LoanPlanInstallment(BigDecimal borrowerPaymentAmount,
      BigDecimal initialOutstandingPrincipal, BigDecimal interest, BigDecimal principal,
      BigDecimal remainingOutstandingPrincipal, ZonedDateTime date) {
    this.borrowerPaymentAmount = borrowerPaymentAmount;
    this.initialOutstandingPrincipal = initialOutstandingPrincipal;
    this.interest = interest;
    this.principal = principal;
    this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
    this.date = date;
  }

  public BigDecimal getBorrowerPaymentAmount() {
    return borrowerPaymentAmount;
  }

  public BigDecimal getInitialOutstandingPrincipal() {
    return initialOutstandingPrincipal;
  }

  public BigDecimal getInterest() {
    return interest;
  }

  public BigDecimal getPrincipal() {
    return principal;
  }

  public BigDecimal getRemainingOutstandingPrincipal() {
    return remainingOutstandingPrincipal;
  }

  public ZonedDateTime getDate() {
    return date;
  }
}
