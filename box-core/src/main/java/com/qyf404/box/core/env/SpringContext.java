/*
 */
package com.qyf404.box.core.env;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * 基于spring框架的上下文，用于获取spring管理的对象。
 * 
 * @author qyf404@gmail.com
 * @since 2014年12月3日
 */
public class SpringContext implements Context {
  
  private static final Logger LOG = LoggerFactory.getLogger(SpringContext.class.getName());

	private ApplicationContext applicationContext;

	public SpringContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jbpm.api.env.Context#get(java.lang.String)
	 */
	public Object get(String key) {
		if (applicationContext.containsBean(key)) {
			return applicationContext.getBean(key);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> type) {
		String[] names = applicationContext.getBeanNamesForType(type);
		if (names.length > 1 && LOG.isWarnEnabled()) {
		    LOG.warn("Multiple Spring beans found for type " + type + " returning the first one found");
		}
		
		if (names.length >= 1) {
		  return (T) applicationContext.getBean(names[0]);
		} else {
			try{
				return applicationContext.getBean(type);
			}catch (Exception e){
				e.printStackTrace();
				LOG.warn("Spring bean not found for type " + type, e);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jbpm.api.env.Context#getName()
	 */
	public String getName() {
		return Context.CONTEXTNAME_SPRING;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jbpm.api.env.Context#has(java.lang.String)
	 */
	public boolean has(String key) {
		return applicationContext.containsBean(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jbpm.api.env.Context#keys()
	 */
	public Set<String> keys() {
		Set<String> set = new HashSet<String>(Arrays.asList(applicationContext.getBeanDefinitionNames()));
		return set;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jbpm.api.env.Context#set(java.lang.String, java.lang.Object)
	 */
	public Object set(String key, Object value) {
		throw new RuntimeException("Can't add to the spring context");
	}

}
