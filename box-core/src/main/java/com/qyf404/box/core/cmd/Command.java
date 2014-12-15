package com.qyf404.box.core.cmd;

import java.io.Serializable;

import com.qyf404.box.core.env.Environment;


/** commands that can be {@link ProcessEngine#execute(Command) executed by the process engine}.
 * 
 * @author qyf404
 */
public interface Command<T> extends Serializable {
  
  T execute(Environment environment) throws Exception;
}
