package br.com.cresende.plangenerator.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoanPlanCalculatorTest {

  @Autowired
  private LoanPlanCalculator calculator;

  @Test
  public void calcMonthlyRateFromAnnualRate_rateNull_ReturnVale() {
    BigDecimal val = calculator.calcMonthlyRateFromAnnualRate(new BigDecimal("30.0"), 30, 260);
    assertEquals(new BigDecimal("0.03333333333333333333"), val);
  }

  @Test
  public void calcInterest_rateNull_ReturnVale() {
    BigDecimal val = calculator
        .calcInterest(new BigDecimal("30.0"), 30, 260, new BigDecimal("30.0"));
    assertEquals(new BigDecimal("1.04"), val);
  }

  @Test
  public void calcPrincipal_rateNull_ReturnVale() {
    BigDecimal val = calculator.calcPrincipal(new BigDecimal("30.0"), new BigDecimal("30.0"));
    assertEquals(new BigDecimal("0.0"), val);
  }

  @Test
  public void calcAnnuity_rateNull_ReturnVale() {
    BigDecimal val = calculator.calcAnnuity(1, new BigDecimal("30.0"), new BigDecimal("30.0"));
    assertEquals(new BigDecimal("930.00"), val);
  }


  @Test
  public void calcMonthlyRateFromAnnualRate_rateNull_ThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> {
      calculator.calcMonthlyRateFromAnnualRate(null, 1, 2);
    });
  }

  @Test
  public void calcInterest_rateNull_ThrowsException() {

    assertThrows(IllegalArgumentException.class, () -> {
      calculator.calcInterest(null, 1, 2, new BigDecimal(0));
    });
  }

  @Test
  public void calcPrincipal_rateNull_ThrowsException() {

    assertThrows(IllegalArgumentException.class, () -> {
      calculator.calcPrincipal(null, new BigDecimal(0));
    });
  }

  @Test
  public void calcAnnuity_rateNull_ThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> {
      calculator.calcAnnuity(1, null, new BigDecimal(0));
    });
  }


}
