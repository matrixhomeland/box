package com.qyf404.box.core.cmd;

import com.qyf404.box.core.env.Environment;

/** convenience for commands without return value.
 * 
 * @author qyf404
 */
public abstract class VoidCommand implements Command<Void> {

  private static final long serialVersionUID = 1L;

  public Void execute(Environment environment) throws Exception {
    executeVoid(environment);
    return null;
  }

  protected abstract void executeVoid(Environment environment) throws Exception;

}
