package br.com.cresende.plangenerator.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;

/**
 * holds methods for calculation used in the loan plan installment
 *
 * @author cresende
 */
@Component
public class LoanPlanCalculator {

  private static final int SCALE_FOR_CALCULATION = 20;
  private static final int SCALE_FOR_DISPLAY = 2;

  /**
   * Monthly rate will be the annual rate divided by 100 divided by e Days in the year divided by
   * days in the month(12) (annual rate / 100)/( (days in the year / days in the month))
   *
   * @param annualRate
   * @param daysInMonth
   * @param daysInYear
   * @return the monthly rate
   */
  public BigDecimal calcMonthlyRateFromAnnualRate(BigDecimal annualRate,
      int daysInMonth, int daysInYear) {

    Utils.checkForNull(annualRate);

    BigDecimal totalInstalmentsInTheYear = BigDecimal.valueOf(daysInYear)
        .divide(BigDecimal.valueOf(daysInMonth), 0, RoundingMode.HALF_UP);

    BigDecimal monthlyRate = annualRate
        .divide(BigDecimal.valueOf(100), SCALE_FOR_CALCULATION, RoundingMode.HALF_EVEN);

    return monthlyRate
        .divide(totalInstalmentsInTheYear, SCALE_FOR_CALCULATION, RoundingMode.HALF_EVEN);
  }


  /**
   * Interest = (Rate * Days in Month * Initial Outstanding Principal) / Days in Year e.g. first
   * installment = (0.05 * 30 * 5000.00) / 360 = 20.83 € (with rounding)
   *
   * @param rate
   * @param daysInMonth
   * @param daysInYear
   * @param initialPrincipal
   * @return the interest
   */
  public BigDecimal calcInterest(BigDecimal rate, int daysInMonth, int daysInYear,
      BigDecimal initialPrincipal) {

    Utils.checkForNull(rate, initialPrincipal);
    return (rate.multiply(BigDecimal.valueOf(daysInMonth)).multiply(initialPrincipal))
        .divide(BigDecimal.valueOf(daysInYear), SCALE_FOR_CALCULATION, RoundingMode.HALF_EVEN)
        .movePointLeft(2).setScale(SCALE_FOR_DISPLAY, RoundingMode.HALF_EVEN);
  }

  /**
   * Principal = Annuity - Interest e.g. first principal = 219.36 - 20.83 = 198.53 €
   *
   * @param annuity
   * @param interest
   * @return the value principal
   */
  public BigDecimal calcPrincipal(BigDecimal annuity, BigDecimal interest) {
    Utils.checkForNull(annuity, interest);
    return annuity.subtract(interest);
  }


  /**
   * loanAmount x	rate Annuity Formula:  = ------------------------------- 1
   * -	[	1	+	rate]^-(duration)
   *
   * @param duration
   * @param rate
   * @param loanAmount
   * @return the annuity
   */
  public BigDecimal calcAnnuity(int duration, BigDecimal rate, BigDecimal loanAmount) {
    Utils.checkForNull(rate, loanAmount);
    BigDecimal denominator = BigDecimal.ONE
        .subtract(BigDecimal.ONE.add(rate).pow(-duration, new MathContext(SCALE_FOR_CALCULATION)));
    return loanAmount.multiply(rate, new MathContext(SCALE_FOR_CALCULATION))
        .divide(denominator, SCALE_FOR_DISPLAY, RoundingMode.HALF_EVEN);
  }
}
