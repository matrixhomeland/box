package com.qyf404.box.core.env;

import java.util.HashMap;
import java.util.Set;

/**
 * 上下文的简单实现
 * @author qyfmac
 *
 */
public class SimpleContext implements Context {
	private HashMap<String, Object> map = new HashMap<String, Object>();
	private String name;
	public SimpleContext(String name){
		this.name = name;
	}
	@Override
	public Object get(String key) {
		return map.get(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Class<T> type) {
		return (T) map.get(type.getName());
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean has(String key) {
		return map.containsKey(key);
	}

	@Override
	public Set<String> keys() {
		return map.keySet();
	}

	@Override
	public Object set(String key, Object value) {
		return map.put(key, value);
	}
}
