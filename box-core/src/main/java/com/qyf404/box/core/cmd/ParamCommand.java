package com.qyf404.box.core.cmd;

import java.util.HashMap;
import java.util.Map;


/** command with support for parameter passing from the client to the implementation.
 * 
 * @author qyf404
 */
public abstract class ParamCommand<T> implements Command<T> {

  private static final long serialVersionUID = 1L;

  protected Map<String, Object> params = new HashMap<String, Object>();
  
  public ParamCommand<T> setParam(String name, Object value) {
    params.put(name, value);
    return this;
  }
}
