package com.qyf404.box.core.query;

public enum QueryOperator {

  EQUALS("="),

  NOT_EQUALS("!="),

  GREATER_THAN(">"),

  GREATER_THAN_OR_EQUAL(">="),

  LESS_THAN("<"),

  LESS_THAN_OR_EQUAL("<="),

  LIKE("like"),

  IN("in"),

  NOT_IN("not in"),

  IS_NULL("is null"),

  NOT_NULL("is not null");

  private final String value;

  QueryOperator(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }

}
