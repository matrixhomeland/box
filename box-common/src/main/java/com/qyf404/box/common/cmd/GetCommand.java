package com.qyf404.box.common.cmd;

import java.io.Serializable;

import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.qyf404.box.core.cmd.AbstractCommand;
import com.qyf404.box.core.env.Environment;

public class GetCommand<T> extends AbstractCommand<T>{

	private static final long serialVersionUID = 1L;
	private Serializable id;
	private Class<T> clazz;
	private LockOptions lockOptions;
	
	public GetCommand(Class<T> clazz, Serializable id){
		this.id = id;
		this.clazz = clazz;
		this.lockOptions = LockOptions.NONE;
	}
	@SuppressWarnings("unchecked")
	@Override
	public T execute(Environment environment) throws Exception {
		SessionFactory sessionFactory = environment.get(SessionFactory.class);
		Session session = sessionFactory.getCurrentSession();
		T t = (T) session.get(clazz, Integer.valueOf(id.toString()), lockOptions);
		return t;
	}
	public LockOptions getLockOptions() {
		return lockOptions;
	}
	public void setLockOptions(LockOptions lockOptions) {
		this.lockOptions = lockOptions;
	}
	public Serializable getId() {
		return id;
	}
	public Class<T> getClazz() {
		return clazz;
	}
	
	
	

}
