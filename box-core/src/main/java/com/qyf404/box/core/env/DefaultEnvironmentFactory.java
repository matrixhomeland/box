/**
 * 
 */
package com.qyf404.box.core.env;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.qyf404.box.core.tx.SpringTransaction;

/**
 * box框架的环境工厂，通过这个工厂可以开启一个新的环境
 * 
 * @author qyf404@gmail.com
 * @since 2014年12月3日
 */
@Named("defaultEnvironmentFactory")
public class DefaultEnvironmentFactory implements EnvironmentFactory, Context, ApplicationContextAware {
	private static final long serialVersionUID = 1L;


	private static final Logger log = LoggerFactory
			.getLogger(DefaultEnvironmentFactory.class.getName());

	
	private SimpleContext globalContext;
	private SpringContext springContext;
	
	
	private static EnvironmentFactory instance;
	public static EnvironmentFactory getInstance(){
		return instance;
	}
	
	private ApplicationContext applicationContext;
	
	@PostConstruct
	public void init(){
		instance = this;
		initSpringContext();
		initGlobalContext();
	}

	private void initSpringContext() {
		springContext = new SpringContext(applicationContext);
	}

	private void initGlobalContext() {
		//初始化全局的配置上下文
		globalContext = new SimpleContext( Context.CONTEXTNAME_GLOBAL);
	}
	

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	/* (non-Javadoc)
	 * @see com.qyf404.box.core.env.Context#getName()
	 */
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	/* (non-Javadoc)
	 * @see com.qyf404.box.core.env.Context#get(java.lang.String)
	 */
	@Override
	public Object get(String key) {
		Object obj = globalContext.get(key);
		if(obj == null){
			obj = springContext.get(key);
		}
		return obj;
	}

	/* (non-Javadoc)
	 * @see com.qyf404.box.core.env.Context#get(java.lang.Class)
	 */
	@Override
	public <T> T get(Class<T> type) {
		T t = globalContext.get(type);
		if(t == null){
			t = springContext.get(type);
		}
		return t;
	}

	/* (non-Javadoc)
	 * @see com.qyf404.box.core.env.Context#has(java.lang.String)
	 */
	@Override
	public boolean has(String key) {
		return globalContext.has(key) || springContext.has(key);
	}

	/* (non-Javadoc)
	 * @see com.qyf404.box.core.env.Context#set(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object set(String key, Object value) {
		throw new RuntimeException("Can't add to the global or spring context");
	}

	/* (non-Javadoc)
	 * @see com.qyf404.box.core.env.Context#keys()
	 */
	@Override
	public Set<String> keys() {
		Set<String> keys = new HashSet<String>();
		keys.addAll(globalContext.keys());
		keys.addAll(springContext.keys());
		return keys;
	}

	/* (non-Javadoc)
	 * @see com.qyf404.box.core.env.EnvironmentFactory#openEnvironment()
	 */
	@Override
	public EnvironmentImpl openEnvironment() {
		
		
		
		DefaultEnvironment defaultEnvironment = new DefaultEnvironment();
		
		defaultEnvironment.setContext(springContext);
		defaultEnvironment.setContext(globalContext);
		
		installTransactionContext(defaultEnvironment);
		
		// installAuthenticatedUserId(environment);
				// installProcessEngineContext(environment);
				// installTransactionContext(environment);
		
		if (log.isTraceEnabled())
			log.trace("opening box" + defaultEnvironment);
		return defaultEnvironment;
	}
	 protected void installTransactionContext(DefaultEnvironment environment) {
		 SimpleContext transactionContext = new SimpleContext(CONTEXTNAME_TRANSACTION);
//		    WireContext transactionContext = new WireContext(transactionWireDefinition, Context.CONTEXTNAME_TRANSACTION, true);
		    // add the environment block context to the environment
		    environment.setContext(transactionContext);

		    EnvironmentImpl.pushEnvironment(environment);
		    try {
		    	SpringTransaction springTransaction = new SpringTransaction();
		    	transactionContext.set(SpringTransaction.class.getName(), springTransaction);
		      // finish the creation of the environment wire context
		      //transactionContext.create();

		    } catch (RuntimeException e) {
		      EnvironmentImpl.popEnvironment();
		      throw e;
		    }
		  }

	//	public EnvironmentImpl openEnvironment(Context transactionContext) {
//		
//		
//		DefaultEnvironment boxEnvironment = new DefaultEnvironment();
//		
//		boxEnvironment.setContext(springContext);
//		boxEnvironment.setContext(globalContext);
//		boxEnvironment.setContext(transactionContext);
//		
//		// installAuthenticatedUserId(environment);
//		// installProcessEngineContext(environment);
//		// installTransactionContext(environment);
//		
//		if (log.isTraceEnabled())
//			log.trace("opening box" + boxEnvironment);
//		return boxEnvironment;
//	}

	/* (non-Javadoc)
	 * @see com.qyf404.box.core.env.EnvironmentFactory#close()
	 */
	@Override
	public void close() {
		log.warn("BoxEnvironmentFactory的close被调用.但该方法不处理任何事情.");
		
	}
	


}
