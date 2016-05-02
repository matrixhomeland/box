package com.qyf404.box.common.cmd;

import java.io.Serializable;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.qyf404.box.common.model.Enabled;
import com.qyf404.box.core.cmd.AbstractCommand;
import com.qyf404.box.core.env.Environment;

public class GetEnabledCommand<T> extends AbstractCommand<T>{

	private static final long serialVersionUID = 1L;
	private Serializable id;
	private Class<T> clazz;
	private LockOptions lockOptions;
	
	public GetEnabledCommand(Class<T> clazz, Serializable id){
		this.id = id;
		this.clazz = clazz;
		this.lockOptions = LockOptions.NONE;
	}
	@SuppressWarnings("unchecked")
	@Override
	public T execute(Environment environment) throws Exception {
		SessionFactory sessionFactory = environment.get(SessionFactory.class);
		Session session = sessionFactory.getCurrentSession();
		
		T t = clazz.newInstance();
		
		if( t instanceof Enabled) {
			Query query = session.createQuery("from " + clazz.getName() + " where id = :id and enabled = true ");
			
			query.setInteger("id", Integer.valueOf(id.toString()));
			
			t = (T) query.uniqueResult();
		}else {
			 t = (T) session.get(clazz, Integer.valueOf(id.toString()), lockOptions);	
		}
		
		
		
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
