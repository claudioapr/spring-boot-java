package br.com.cresende.plangenerator.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.cresende.plangenerator.dto.LoanPlanRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoanPlanControllerTest {

  public static final String GENERATE_PLAN_PATH = "/generate-plan";
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper mapper;

  @Test
  public void generatePlan_validScenario_http200Array24Entries()
      throws Exception {
    LoanPlanRequest request = new LoanPlanRequest(BigDecimal.valueOf(5000), BigDecimal.valueOf(5.0),
        24,
        ZonedDateTime.parse("2018-01-01T00:00:01Z"));
    String requestJson = mapper.writeValueAsString(request);

    mockMvc.perform(post(GENERATE_PLAN_PATH)
        .content(requestJson)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        // first instalment
        .andExpect(jsonPath("$", hasSize(24)))

        .andExpect(jsonPath("$[0].borrowerPaymentAmount").value(219.36))
        .andExpect(jsonPath("$[0].date").value("2018-01-01T00:00:00Z"))
        .andExpect(jsonPath("$[0].initialOutstandingPrincipal").value(5000))
        .andExpect(jsonPath("$[0].interest").value(20.83))
        .andExpect(jsonPath("$[0].principal").value(198.53))
        .andExpect(jsonPath( "$[0].remainingOutstandingPrincipal").value(4801.47))

        // Last instalment
        .andExpect(jsonPath("$[23].borrowerPaymentAmount").value(219.28))
        .andExpect(jsonPath("$[23].date").value("2019-12-01T00:00:00Z"))
        .andExpect(jsonPath("$[23].initialOutstandingPrincipal").value(218.37))
        .andExpect(jsonPath("$[23].interest").value(0.91))
        .andExpect(jsonPath("$[23].principal").value(218.37))
        .andExpect(jsonPath("$[23].remainingOutstandingPrincipal").value(0));
  }


  @Test
  public void generatePlan_loanAmountNegative_http400()
      throws Exception {
    LoanPlanRequest request = new LoanPlanRequest(BigDecimal.valueOf(-5000), BigDecimal.valueOf(5.0),
        24,
        ZonedDateTime.parse("2018-01-01T00:00:01Z"));

    String requestJson = mapper.writeValueAsString(request);

    mockMvc.perform(post(GENERATE_PLAN_PATH)
        .content(requestJson)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.when", is(notNullValue())))
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(jsonPath("$.errors").isArray())
        .andExpect(jsonPath("$.errors", hasSize(1)))
        .andExpect(jsonPath("$.errors", hasItem("Loan amount must be a positive value.")));
  }

  @Test
  public void generatePlan_loanAmountZero_http400()
      throws Exception {
    LoanPlanRequest request = new LoanPlanRequest(BigDecimal.valueOf(0), BigDecimal.valueOf(5.0),
        24,
        ZonedDateTime.parse("2018-01-01T00:00:01Z"));

    String requestJson = mapper.writeValueAsString(request);

    mockMvc.perform(post(GENERATE_PLAN_PATH)
        .content(requestJson)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.when", is(notNullValue())))
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(jsonPath("$.errors").isArray())
        .andExpect(jsonPath("$.errors", hasSize(1)))
        .andExpect(jsonPath("$.errors", hasItem("Loan amount must be a positive value.")));
  }


  @Test
  public void generatePlan_loanAmountNull_http400()
      throws Exception {
    LoanPlanRequest request = new LoanPlanRequest(null, BigDecimal.valueOf(5.0),
        24,
        ZonedDateTime.parse("2018-01-01T00:00:01Z"));

    String requestJson = mapper.writeValueAsString(request);

    mockMvc.perform(post(GENERATE_PLAN_PATH)
        .content(requestJson)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.when", is(notNullValue())))
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(jsonPath("$.errors").isArray())
        .andExpect(jsonPath("$.errors", hasSize(1)))
        .andExpect(jsonPath("$.errors", hasItem("must not be null")));
  }




  @Test
  public void generatePlan_nominalRateNegative_http400()
      throws Exception {
    LoanPlanRequest request = new LoanPlanRequest(BigDecimal.valueOf(5000), BigDecimal.valueOf(-5.0),
        24,
        ZonedDateTime.parse("2018-01-01T00:00:01Z"));

    String requestJson = mapper.writeValueAsString(request);

    mockMvc.perform(post(GENERATE_PLAN_PATH)
        .content(requestJson)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.when", is(notNullValue())))
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(jsonPath("$.errors").isArray())
        .andExpect(jsonPath("$.errors", hasSize(1)))
        .andExpect(jsonPath("$.errors", hasItem("Nominal rate must be a positive value.")));
  }

  @Test
  public void generatePlan_nominalRateZero_http400()
      throws Exception {
    LoanPlanRequest request = new LoanPlanRequest(BigDecimal.valueOf(5000), BigDecimal.valueOf(0),
        24,
        ZonedDateTime.parse("2018-01-01T00:00:01Z"));

    String requestJson = mapper.writeValueAsString(request);

    mockMvc.perform(post(GENERATE_PLAN_PATH)
        .content(requestJson)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.when", is(notNullValue())))
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(jsonPath("$.errors").isArray())
        .andExpect(jsonPath("$.errors", hasSize(1)))
        .andExpect(jsonPath("$.errors", hasItem("Nominal rate must be a positive value.")));
  }

  @Test
  public void generatePlan_nominalRateNull_http400()
      throws Exception {
    LoanPlanRequest request = new LoanPlanRequest(BigDecimal.valueOf(5000), null,
        24,
        ZonedDateTime.parse("2018-01-01T00:00:01Z"));

    String requestJson = mapper.writeValueAsString(request);

    mockMvc.perform(post(GENERATE_PLAN_PATH)
        .content(requestJson)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.when", is(notNullValue())))
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(jsonPath("$.errors").isArray())
        .andExpect(jsonPath("$.errors", hasSize(1)))
        .andExpect(jsonPath("$.errors", hasItem("must not be null")));
  }

  @Test
  public void generatePlan_durationNegative_http400()
      throws Exception {
    LoanPlanRequest request = new LoanPlanRequest(BigDecimal.valueOf(5000), BigDecimal.valueOf(5.0),
        -1,
        ZonedDateTime.parse("2018-01-01T00:00:01Z"));

    String requestJson = mapper.writeValueAsString(request);

    mockMvc.perform(post(GENERATE_PLAN_PATH)
        .content(requestJson)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.when", is(notNullValue())))
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(jsonPath("$.errors").isArray())
        .andExpect(jsonPath("$.errors", hasSize(1)))
        .andExpect(jsonPath("$.errors", hasItem("Duration must be greater than 0.")));
  }

  @Test
  public void generatePlan_durationZero_http400()
      throws Exception {
    LoanPlanRequest request = new LoanPlanRequest(BigDecimal.valueOf(5000), BigDecimal.valueOf(5.0),
        0,
        ZonedDateTime.parse("2018-01-01T00:00:01Z"));

    String requestJson = mapper.writeValueAsString(request);

    mockMvc.perform(post(GENERATE_PLAN_PATH)
        .content(requestJson)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.when", is(notNullValue())))
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(jsonPath("$.errors").isArray())
        .andExpect(jsonPath("$.errors", hasSize(1)))
        .andExpect(jsonPath("$.errors", hasItem("Duration must be greater than 0.")));
  }

  @Test
  public void generatePlan_durationNull_http400()
      throws Exception {
    LoanPlanRequest request = new LoanPlanRequest(BigDecimal.valueOf(5000), BigDecimal.valueOf(5.0),
        null,
        ZonedDateTime.parse("2018-01-01T00:00:01Z"));

    String requestJson = mapper.writeValueAsString(request);

    mockMvc.perform(post(GENERATE_PLAN_PATH)
        .content(requestJson)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.when", is(notNullValue())))
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(jsonPath("$.errors").isArray())
        .andExpect(jsonPath("$.errors", hasSize(1)))
        .andExpect(jsonPath("$.errors", hasItem("must not be null")));
  }


  @Test
  public void generatePlan_startDateNull_http400()
      throws Exception {
    LoanPlanRequest request = new LoanPlanRequest(BigDecimal.valueOf(5000), BigDecimal.valueOf(5.0),
        24,
        null);

    String requestJson = mapper.writeValueAsString(request);

    mockMvc.perform(post(GENERATE_PLAN_PATH)
        .content(requestJson)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.when", is(notNullValue())))
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(jsonPath("$.errors").isArray())
        .andExpect(jsonPath("$.errors", hasSize(1)))
        .andExpect(jsonPath("$.errors", hasItem("must not be null")));
  }

}
