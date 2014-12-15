package com.qyf404.box.core.env;

import java.io.Serializable;

/**
 * factory for {@link EnvironmentImpl}s.  
 * 
 * <p>Default implementation is 
 * {@link BoxEnvironmentFactory}. EnvironmentFactory is thread safe, you 
 * should use one environment factory for all your threads.
 * </p>
 * 
 * 
 * @author qyf404@gmail.com
 * @since 2014年12月3日
 */
public interface EnvironmentFactory extends Context, Serializable {
  
  /**
   * open a new EnvironmentImpl.  The client is responsible for 
   * closing the environment with {@link EnvironmentImpl#close()}.
   */
  EnvironmentImpl openEnvironment();
  
  /**
   * closes this environment factory and cleans any allocated 
   * resources.
   */
  void close();

  
}
