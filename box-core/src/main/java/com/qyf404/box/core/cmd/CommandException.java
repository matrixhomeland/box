package com.qyf404.box.core.cmd;

import com.qyf404.box.core.BoxException;

/**
 * @author qyf404
 */
public class CommandException extends BoxException {

  private static final long serialVersionUID = 1L;

  public CommandException() {
    super();
  }

  public CommandException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public CommandException(String msg) {
    super(msg);
  }

  public CommandException(Throwable cause) {
    super(cause);
  }
}
