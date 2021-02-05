package br.com.cresende.plangenerator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidInputForInstallmentCalculationException extends RuntimeException {

  public InvalidInputForInstallmentCalculationException(String message) {
    super(message);
  }

}
