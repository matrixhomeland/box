/**
 * 
 */
package com.qyf404.box.core.tx;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import com.qyf404.box.core.cmd.Command;
import com.qyf404.box.core.env.EnvironmentImpl;
import com.qyf404.box.core.svc.Interceptor;
import com.qyf404.box.core.svc.Policy;

/**
 * calls setRollbackOnly on the transaction in the environment in case an
 * exception occurs during execution of the command.
 * 
* @author qyf404@gmail.com
 * @since 2014年12月6日
 */
public class SpringTransactionInterceptor extends Interceptor {

  protected int springPropagationBehaviour = TransactionDefinition.PROPAGATION_REQUIRED;
  private String transactionManagerName;

  @SuppressWarnings("unchecked")
  public <T> T execute(Command<T> command) {
    PlatformTransactionManager platformTransactionManager = resolveTransactionManager();
    TransactionTemplate template = new TransactionTemplate(platformTransactionManager);
    template.setPropagationBehavior(springPropagationBehaviour);
    return (T) template.execute(new SpringCommandCallback(next, command));
  }

  /**
   * Resolves the transaction manager from the environment.
   * @param environment
   * @return the transaction manager
   */
  private PlatformTransactionManager resolveTransactionManager() {
    if (transactionManagerName != null && transactionManagerName.length() > 0) {
      return (PlatformTransactionManager) EnvironmentImpl.getFromCurrent(transactionManagerName);
    }

    return EnvironmentImpl.getFromCurrent(PlatformTransactionManager.class);
  }

  public void setPolicy(Policy policy) {
    if (policy==Policy.REQUIRES_NEW) {
      springPropagationBehaviour = TransactionDefinition.PROPAGATION_REQUIRES_NEW;
    } else {
      springPropagationBehaviour = TransactionDefinition.PROPAGATION_REQUIRED;
    }
  }
  
  public void setTransactionManagerName(String transactionManagerName) {
    this.transactionManagerName = transactionManagerName;
  }
}