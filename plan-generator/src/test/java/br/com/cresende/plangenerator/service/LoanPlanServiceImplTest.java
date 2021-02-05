package br.com.cresende.plangenerator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.cresende.plangenerator.dto.LoanPlanRequest;
import br.com.cresende.plangenerator.exception.InvalidInputForInstallmentCalculationException;
import br.com.cresende.plangenerator.model.LoanPlan;
import br.com.cresende.plangenerator.model.LoanPlanInstallment;
import br.com.cresende.plangenerator.service.impl.LoanPlanServiceImpl;
import br.com.cresende.plangenerator.util.LoanPlanCalculator;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
public class LoanPlanServiceImplTest {

  public static final LoanPlanRequest CORRECT_REQUEST_OBJECT = new LoanPlanRequest(
      BigDecimal.valueOf(5000), BigDecimal.valueOf(5.0),
      24,
      ZonedDateTime.parse("2018-01-01T00:00:01Z"));

  @Autowired
  private LoanPlanServiceImpl service;

  @SpyBean
  private LoanPlanCalculator calculator;

  @Test
  public void generatePlan_validScenario_ReturnLoanPlan() {

    LoanPlan plan = service.generateLoanPlan(CORRECT_REQUEST_OBJECT);

    assertNotNull(plan);
    assertNotNull(plan.getInstallments());
    assertTrue(plan.getInstallments().size() == 24);
    assertEquals(CORRECT_REQUEST_OBJECT.getDuration(), plan.getDuration());
    assertEquals(CORRECT_REQUEST_OBJECT.getLoanAmount(), plan.getLoanAmount());
    assertEquals(CORRECT_REQUEST_OBJECT.getNominalRate(), plan.getNominalRate());
    assertEquals(CORRECT_REQUEST_OBJECT.getStartDate(), plan.getStartDate());
  }

  @Test
  public void generateInstalments_validScenario_ReturnLoanInstalments() {

    List<LoanPlanInstallment> installments = service
        .generateInstallments(CORRECT_REQUEST_OBJECT.getLoanAmount(),
            CORRECT_REQUEST_OBJECT.getNominalRate(),
            CORRECT_REQUEST_OBJECT.getDuration(), CORRECT_REQUEST_OBJECT.getStartDate());

    Mockito.verify(calculator, Mockito.times(1))
        .calcAnnuity(Mockito.anyInt(), Mockito.any(), Mockito.any());

    Mockito.verify(calculator, Mockito.times(1))
        .calcMonthlyRateFromAnnualRate(Mockito.any(), Mockito.anyInt(), Mockito.anyInt());

    Mockito.verify(calculator, Mockito.times(24))
        .calcInterest(Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any());

    Mockito.verify(calculator, Mockito.times(24)).calcPrincipal(Mockito.any(), Mockito.any());

    assertNotNull(installments);
    assertTrue(installments.size() == 24);

    // first instalment
    assertEquals(new BigDecimal("4801.47"),
        installments.get(0).getRemainingOutstandingPrincipal());
    assertEquals(new BigDecimal("219.36"), installments.get(0).getBorrowerPaymentAmount());
    assertEquals(ZonedDateTime.parse("2018-01-01T00:00:00Z"), installments.get(0).getDate());
    assertEquals(new BigDecimal("5000"), installments.get(0).getInitialOutstandingPrincipal());
    assertEquals(new BigDecimal("20.83"), installments.get(0).getInterest());
    assertEquals(new BigDecimal("198.53"), installments.get(0).getPrincipal());

    // Last instalment

    assertEquals(new BigDecimal("0.00"),
        installments.get(23).getRemainingOutstandingPrincipal());
    assertEquals(new BigDecimal("219.28"), installments.get(23).getBorrowerPaymentAmount());
    assertEquals(ZonedDateTime.parse("2018-01-01T00:00:00Z"), installments.get(0).getDate());
    assertEquals(new BigDecimal("218.37"),
        installments.get(23).getInitialOutstandingPrincipal());
    assertEquals(new BigDecimal("0.91"), installments.get(23).getInterest());
    assertEquals(new BigDecimal("218.37"), installments.get(23).getPrincipal());

  }

  @Test
  public void generateInstalments_loanAmountNull_ThrowsException() {

    assertThrows(InvalidInputForInstallmentCalculationException.class, () -> {
      service
          .generateInstallments(null,
              CORRECT_REQUEST_OBJECT.getNominalRate(),
              CORRECT_REQUEST_OBJECT.getDuration(), CORRECT_REQUEST_OBJECT.getStartDate());
    });


  }

  @Test
  public void generateInstalments_nominalRateNull_ThrowsException() {

    assertThrows(InvalidInputForInstallmentCalculationException.class, () -> {
      service
          .generateInstallments(CORRECT_REQUEST_OBJECT.getLoanAmount(),
              null,
              CORRECT_REQUEST_OBJECT.getDuration(), CORRECT_REQUEST_OBJECT.getStartDate());
    });


  }

  @Test
  public void generateInstalments_durationNull_ThrowsException() {

    assertThrows(InvalidInputForInstallmentCalculationException.class, () -> {
      service
          .generateInstallments(CORRECT_REQUEST_OBJECT.getLoanAmount(),
              CORRECT_REQUEST_OBJECT.getNominalRate(), null, CORRECT_REQUEST_OBJECT.getStartDate());
    });

  }

  @Test
  public void generateInstalments_startDateNull_ThrowsException() {

    assertThrows(InvalidInputForInstallmentCalculationException.class, () -> {
      service
          .generateInstallments(CORRECT_REQUEST_OBJECT.getLoanAmount(),
              CORRECT_REQUEST_OBJECT.getNominalRate(),
              CORRECT_REQUEST_OBJECT.getDuration(), null);
    });


  }

}
