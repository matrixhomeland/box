package com.qyf404.box.core;

/** 
 * Aquarius的核心异常
 * 
 * */
public class BoxException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public BoxException() {
    super();
  }
  public BoxException(String msg, Throwable cause) {
    super(msg);
    super.initCause(cause);
  }
  public BoxException(String msg) {
    super(msg);
  }
  public BoxException(Throwable cause) {
    super();
    super.initCause(cause);
  }
}
