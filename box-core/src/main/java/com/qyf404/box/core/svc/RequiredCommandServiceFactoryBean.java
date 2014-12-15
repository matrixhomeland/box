/**
 * 
 */
package com.qyf404.box.core.svc;

import javax.annotation.Resource;

import org.springframework.beans.factory.FactoryBean;

import com.qyf404.box.core.env.EnvironmentFactory;
import com.qyf404.box.core.tx.SpringTransactionInterceptor;

/**
 * @author qyf404@gmail.com
 * @since 2014年12月6日
 */
public abstract class RequiredCommandServiceFactoryBean implements FactoryBean<CommandService> {
   
	@Resource(name="defaultEnvironmentFactory")
	protected EnvironmentFactory defaultEnvironmentFactory;
	
	protected RetryInterceptor getRetryInterceptor(){
		RetryInterceptor retryInterceptor = defaultEnvironmentFactory.get(RetryInterceptor.class);
		if(retryInterceptor == null) retryInterceptor = new RetryInterceptor();
		return retryInterceptor;
	}
	
	protected EnvironmentInterceptor getEnvironmentInterceptor(){
		EnvironmentInterceptor environmentInterceptor = defaultEnvironmentFactory.get(EnvironmentInterceptor.class);
		if(environmentInterceptor == null) environmentInterceptor = new EnvironmentInterceptor();
		
		environmentInterceptor.setEnvironmentFactory(defaultEnvironmentFactory);
		
		return environmentInterceptor;
	}
	
	protected SpringTransactionInterceptor getSpringTransactionInterceptor(){
		SpringTransactionInterceptor springTransactionInterceptor = defaultEnvironmentFactory.get(SpringTransactionInterceptor.class);
		if(springTransactionInterceptor == null) springTransactionInterceptor = new SpringTransactionInterceptor();
		
		return springTransactionInterceptor;
	}
	
	protected DefaultCommandService getDefaultCommandService(){
		DefaultCommandService defaultCommandService = defaultEnvironmentFactory.get(DefaultCommandService.class);
		if(defaultCommandService == null) defaultCommandService = new DefaultCommandService();
		
		return defaultCommandService;
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class<CommandService> getObjectType() {
		return CommandService.class;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	@Override
	public boolean isSingleton() {
		return true;
	}

	public void setDefaultEnvironmentFactory(
			EnvironmentFactory defaultEnvironmentFactory) {
		this.defaultEnvironmentFactory = defaultEnvironmentFactory;
	}

}
