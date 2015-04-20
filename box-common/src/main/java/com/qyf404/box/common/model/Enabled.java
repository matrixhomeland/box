package com.qyf404.box.common.model;

/**
 * 控制对象是否启用接口。也就是控制对象是否已经被删除。一般情况这种数据不在页面上显示。只在持久话处保存。
 * @author qyfmac
 *
 */
public interface Enabled {
	
	/**
	 * 是否启用
	 * @return
	 */
	public boolean isEnabled();
	
	
	
	/**
	 * 执行删除操作
	 */
	public void disable();
	
}
