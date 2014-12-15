package com.qyf404.box.core.env;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qyf404.box.core.BoxException;

/**
 * @author qyf404
 */
public class BasicEnvironment extends EnvironmentImpl {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory
			.getLogger(BasicEnvironment.class);

	protected String authenticatedUserId;
	protected Map<String, Context> contexts = new LinkedHashMap<String, Context>();
	protected Throwable exception;

	protected transient ClassLoader classLoader;

	// context methods
	// ////////////////////////////////////////////////////////////

	public Context getContext(String contextName) {
		return contexts.get(contextName);
	}

	public void setContext(Context context) {
		contexts.put(context.getName(), context);
	}

	public Context removeContext(Context context) {
		return removeContext(context.getName());
	}

	public Context removeContext(String contextName) {
		return contexts.remove(contextName);
	}

	// public Context getEnvironmentFactoryContext() {
	// return getContext(Context.CONTEXTNAME_PROCESS_ENGINE);
	// }

	/**
	 * 获取一次调用的上下文，该上下文一般保存某次调用command的临时数据，请求返回后，数据就会被销毁。
	 * 
	 * @return
	 */
	public Context getEnvironmentContext() {
		return getContext(Context.CONTEXTNAME_TRANSACTION);
	}

	// authenticatedUserId
	// //////////////////////////////////////////////////////

	public String getAuthenticatedUserId() {
		return authenticatedUserId;
	}

	public void setAuthenticatedUserId(String authenticatedUserId) {
		this.authenticatedUserId = authenticatedUserId;
	}

	// classloader methods
	// //////////////////////////////////////////////////////

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	// search methods
	// ///////////////////////////////////////////////////////////

	public Object get(String name) {
		return get(name, null);
	}

	public Object get(String name, String[] searchOrder) {
		return get(name, searchOrder, true);
	}

	public Object get(String name, boolean nullIfNotFound) {
		return get(name, null, nullIfNotFound);
	}

	public Object get(String name, String[] searchOrder, boolean nullIfNotFound) {
		if (searchOrder == null) {
			searchOrder = getDefaultSearchOrder();
		}
		for (String contextName : searchOrder) {
			Context context = contexts.get(contextName);
			if (context.has(name)) {
				return context.get(name);
			}
		}
		if (nullIfNotFound) {
			return null;
		} else {
			throw new BoxException("Null value found for " + name
					+ " but null is not allowed");
		}
	}

	public <T> T get(Class<T> type) {
		return get(type, (String[]) null);
	}

	public <T> T get(Class<T> type, String[] searchOrder) {
		if (searchOrder == null) {
			searchOrder = getDefaultSearchOrder();
		}
		for (String contextName : searchOrder) {
			Context context = contexts.get(contextName);
			T object = context.get(type);
			if (object != null)
				return object;
		}
		return null;
	}

	/**
	 * searches an object based on type in the default search order. if this
	 * environment contains the given context, the search skips contexts
	 * registered after it.
	 */
	public <T> T get(Class<T> type, Context requester) {
		String[] searchOrder = getDefaultSearchOrder();
		int searchPosition = 0;
		for (int i = 0; i < searchOrder.length; i++) {
			if (contexts.get(searchOrder[i]) == requester) {
				searchPosition = i + 1;
				break;
			}
		}
		for (int i = searchPosition; i < searchOrder.length; i++) {
			Context context = contexts.get(searchOrder[i]);
			T object = context.get(type);
			if (object != null)
				return object;
		}
		return null;
	}

	// close
	// ////////////////////////////////////////////////////////////////////

	public void close() {
		log.trace("closing " + this);

		EnvironmentImpl popped = EnvironmentImpl.popEnvironment();
		if (this != popped) {
			throw new BoxException("environment nesting problem");
		}
	}

	// private methods
	// //////////////////////////////////////////////////////////

	protected String[] getDefaultSearchOrder() {
		int size = contexts.size();
		String[] defaultSearchOrder = new String[size];

		int index = size;
		for (String contextName : contexts.keySet()) {
			defaultSearchOrder[--index] = contextName;
		}

		return defaultSearchOrder;
	}
}
