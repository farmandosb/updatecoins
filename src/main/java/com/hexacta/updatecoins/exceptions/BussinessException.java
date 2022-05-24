package com.hexacta.updatecoins.exceptions;

public class BussinessException extends Exception {
  public BussinessException(String message) {
    super(message);
  }

  public BussinessException(org.hibernate.exception.ConstraintViolationException cve) {
    super(cve);
  }
}
