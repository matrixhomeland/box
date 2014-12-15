package com.qyf404.box.core.env;

import com.qyf404.box.core.cmd.Command;

/** exposes configured process engine scope objects and 
 * transaction scope objects {@link Command to command implementations}.
 * 
 * 环境，一个命令执行的环境
 * @author qyf404
 */
public interface Environment {

  /**
   * searches a named object in all the contexts in the default search order. 
   * @return the object if it exists in the environment, <code>null</code> if there is no object with the given name in the environment.
   */
  public abstract Object get(String name);

  /** searches an object based on type.  The search doesn take superclasses of the context elements 
   * into account.
   * @return the first object of the given type or null in case no such element was found.  
   */
  public abstract <T> T get(Class<T> type);

}