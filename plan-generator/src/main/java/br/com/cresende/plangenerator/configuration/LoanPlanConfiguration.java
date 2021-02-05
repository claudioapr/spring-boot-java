package br.com.cresende.plangenerator.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LoanPlanConfiguration {

  private final Integer daysInMonth;
  private final Integer daysInYear;

  public LoanPlanConfiguration(@Value("${loan.plan.config.month.days}") Integer daysInMonth,
      @Value("${loan.plan.config.year.days}") Integer daysInYear) {
    this.daysInMonth = daysInMonth;
    this.daysInYear = daysInYear;
  }

  public Integer getDaysInMonth() {
    return daysInMonth;
  }

  public Integer getDaysInYear() {
    return daysInYear;
  }

  @Override
  public String toString() {
    return "LoanPlanConfiguration{" +
        "daysInMonth=" + daysInMonth +
        ", daysInYear=" + daysInYear +
        '}';
  }
}
