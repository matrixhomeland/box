package com.qyf404.box.core.env;

import java.util.Set;

/**
 * a group of named objects in an {@link EnvironmentImpl} that have a similar lifespan.
 * 
 * 
 * 
 * 上下文，包含了
 * @author qyf404
 */
public interface Context {
	/**加载spring信息的上下文*/
	String CONTEXTNAME_SPRING = "spring";
	/**加载全局配置的上下文*/
	String CONTEXTNAME_GLOBAL = "global";
//	/**加载某次会话的上下文*/
//	String CONTEXTNAME_SESSION = "session";
//	/**加载某次请求的上下文*/
//	String CONTEXTNAME_REQUEST = "request";
	
//  /** key of the process-engine-context in the environment */
//  String CONTEXTNAME_PROCESS_ENGINE = "process-engine";
  
  /** key of the transaction-context in the environment */
  String CONTEXTNAME_TRANSACTION = "transaction";

//  /** key of the execution-context in the environment */
//  String CONTEXTNAME_EXECUTION = "execution";
//
//  /** key of the task-context in the environment */
//  String CONTEXTNAME_TASK = "task";
//  
//  /** key of the job-context in the environment */
//  String CONTEXTNAME_JOB = "job";
  
  String getName();

  Object get(String key);
  <T> T get(Class<T> type);

  boolean has(String key);
  Object set(String key, Object value);
  Set<String> keys();

}
