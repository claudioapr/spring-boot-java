package br.com.cresende.plangenerator.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public final class LoanPlanRequest {

  @Positive(message = "Loan amount must be a positive value.")
  @NotNull
  private final BigDecimal loanAmount;

  @Positive(message = "Nominal rate must be a positive value.")
  @NotNull
  private final BigDecimal nominalRate;

  @Min(value = 1, message = "Duration must be greater than 0.")
  @NotNull
  private final Integer duration;

  @NotNull
  private final ZonedDateTime startDate;

  public LoanPlanRequest(BigDecimal loanAmount, BigDecimal nominalRate, Integer duration,
      ZonedDateTime startDate) {
    this.loanAmount = loanAmount;
    this.nominalRate = nominalRate;
    this.duration = duration;
    this.startDate = startDate;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoanPlanRequest that = (LoanPlanRequest) o;
    return Objects.equals(getLoanAmount(), that.getLoanAmount()) &&
        Objects.equals(getNominalRate(), that.getNominalRate()) &&
        Objects.equals(getDuration(), that.getDuration()) &&
        Objects.equals(getStartDate(), that.getStartDate());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getLoanAmount(), getNominalRate(), getDuration(), getStartDate());
  }

  @Override
  public String toString() {
    return "LoanPlanRequest{" +
        "loanAmount=" + loanAmount +
        ", nominalRate=" + nominalRate +
        ", duration=" + duration +
        ", startDate=" + startDate +
        '}';
  }
}
