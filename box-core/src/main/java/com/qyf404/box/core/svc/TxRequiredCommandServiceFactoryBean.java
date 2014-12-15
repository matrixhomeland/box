/**
 * 
 */
package com.qyf404.box.core.svc;

import javax.inject.Named;

import org.springframework.beans.factory.FactoryBean;

import com.qyf404.box.core.tx.SpringTransactionInterceptor;

/**
 * @author qyf404@gmail.com
 * @since 2014年12月6日
 */
@Named("txRequiredCommandService")
public class TxRequiredCommandServiceFactoryBean extends RequiredCommandServiceFactoryBean implements FactoryBean<CommandService> {
   
	
	/* 
	 * <command-service name="newTxRequiredCommandService">
     * <retry-interceptor />
     * <environment-interceptor policy="requiresNew" />
     * <spring-transaction-interceptor policy="requiresNew" />
     * </command-service>
	 * 
	 *  <!-- Default command service has a Spring transaction interceptor-->
     * <command-service name="txRequiredCommandService">
     * <retry-interceptor />
     * <environment-interceptor />
     * <spring-transaction-interceptor />
     * </command-service>
	 * 
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	@Override
	public CommandService getObject() throws Exception {
		RetryInterceptor retryInterceptor = getRetryInterceptor();
		
		EnvironmentInterceptor environmentInterceptor = getEnvironmentInterceptor();
		
		SpringTransactionInterceptor springTransactionInterceptor = getSpringTransactionInterceptor();
		
		DefaultCommandService defaultCommandService = getDefaultCommandService();
		
		retryInterceptor.setNext(environmentInterceptor);

		environmentInterceptor.setNext(springTransactionInterceptor);
		
		springTransactionInterceptor.setNext(defaultCommandService);

		return retryInterceptor;
	}
	

}
