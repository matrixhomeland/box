//package com.qyf404.box.core.env;
//
//import javax.annotation.PostConstruct;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//
///**
// * spring的环境工厂
// * @author qyfmac
// *
// */
//@Component("springEnvironmentFactory")
//public class SpringEnvironmentFactory extends DefaultEnvironmentFactory
//		implements ApplicationContextAware {
//	private static final long serialVersionUID = 1L;
//
//	private static final Logger log = LoggerFactory
//			.getLogger(SpringEnvironmentFactory.class.getName());
//
//
//	private static EnvironmentFactory instance;
//	public static EnvironmentFactory getInstance(){
//		return instance;
//	}
//	
//	private ApplicationContext applicationContext;
//	
//	@PostConstruct
//	public void init(){
//		instance = this;
//	}
//	
//	@Override
//	public void setApplicationContext(ApplicationContext applicationContext)
//			throws BeansException {
//		this.applicationContext = applicationContext;
//	}
//
//
//	// public static ProcessEngine create(ConfigurationImpl configuration) {
//	// SpringProcessEngine springProcessEngine = null;
//	//
//	// ApplicationContext applicationContext = null;
//	// if (configuration.isInstantiatedFromSpring()) {
//	// applicationContext = (ApplicationContext)
//	// configuration.getApplicationContext();
//	//
//	// springProcessEngine = new SpringProcessEngine();
//	// springProcessEngine.applicationContext = applicationContext;
//	// springProcessEngine.initializeProcessEngine(configuration);
//	//
//	// LocalSessionFactoryBean localSessionFactoryBean =
//	// springProcessEngine.get(LocalSessionFactoryBean.class);
//	// Configuration hibernateConfiguration =
//	// localSessionFactoryBean.getConfiguration();
//	// springProcessEngine.processEngineWireContext
//	// .getWireDefinition()
//	// .addDescriptor(new ProvidedObjectDescriptor(hibernateConfiguration,
//	// true));
//	//
//	// springProcessEngine.checkDb(configuration);
//	//
//	// } else {
//	// String springCfg = (String)
//	// configuration.getProcessEngineWireContext().get("spring.cfg");
//	// if (springCfg==null) {
//	// springCfg = "applicationContext.xml";
//	// }
//	// applicationContext = new ClassPathXmlApplicationContext(springCfg);
//	// springProcessEngine = (SpringProcessEngine)
//	// applicationContext.getBean("processEngine");
//	// }
//	//
//	// return springProcessEngine;
//	// }
//	
//	public EnvironmentImpl openEnvironment() {
//		DefaultEnvironment environment = new DefaultEnvironment();
//
//		if (log.isTraceEnabled())
//			log.trace("opening spring" + environment);
//
//		environment.setContext(new SpringContext(applicationContext));
//
//		// installAuthenticatedUserId(environment);
//		// installProcessEngineContext(environment);
//		// installTransactionContext(environment);
//
//		return environment;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public <T> T get(Class<T> type) {
//		T candidateComponent = super.get(type);
//
//		if (candidateComponent != null) {
//			return candidateComponent;
//		}
//
//		String[] names = applicationContext.getBeanNamesForType(type);
//
//		if (names.length >= 1) {
//
//			if (names.length > 1 && log.isWarnEnabled()) {
//				log.warn("Multiple beans for type " + type
//						+ " found. Returning the first result.");
//			}
//
//			return (T) applicationContext.getBean(names[0]);
//		}
//
//		return null;
//	}
//
//	@Override
//	public Object get(String key) {
//		if (applicationContext.containsBean(key)) {
//			return applicationContext.getBean(key);
//		}
//
//		return super.get(key);
//	}
//
//}
