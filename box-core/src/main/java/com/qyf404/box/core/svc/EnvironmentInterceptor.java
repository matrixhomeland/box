package com.qyf404.box.core.svc;

import com.qyf404.box.core.cmd.Command;
import com.qyf404.box.core.env.EnvironmentFactory;
import com.qyf404.box.core.env.EnvironmentImpl;



/** 
 * 通过设置策略，判断命令在哪个环境中执行
 * sets up an environment around the execution of the command.
 * 
 * @author Tom Baeyens
 */
public class EnvironmentInterceptor extends Interceptor {
  
  protected EnvironmentFactory environmentFactory;
  protected Policy policy = Policy.REQUIRES;

  public <T> T execute(Command<T> command) {
    if ( isEnvironmentCreationNecessary() ) {
      return executeInNewEnvironment(command);
    } else {
      return executeInExistingEnvironment(command);
    }
  }

  protected <T> T executeInExistingEnvironment(Command<T> command) {
    return next.execute(command);
  }

  protected <T> T executeInNewEnvironment(Command<T> command) {
    EnvironmentImpl environment = environmentFactory.openEnvironment();
    try {
      return next.execute(command);
      
    } finally {
      environment.close();
    }
  }

  protected boolean isEnvironmentCreationNecessary() {
    return policy==Policy.REQUIRES_NEW 
           || (EnvironmentImpl.getCurrent()==null);
  }

  public EnvironmentFactory getEnvironmentFactory() {
    return environmentFactory;
  }
  public void setEnvironmentFactory(EnvironmentFactory environmentFactory) {
    this.environmentFactory = environmentFactory;
  }
  public Policy getPolicy() {
    return policy;
  }
  public void setPolicy(Policy policy) {
    this.policy = policy;
  }
}
