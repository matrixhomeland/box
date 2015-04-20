package com.qyf404.box.common.cmd;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.qyf404.box.common.model.Enabled;
import com.qyf404.box.core.cmd.VoidCommand;
import com.qyf404.box.core.env.Environment;


public class LogicallyDeleteCommand<T extends Enabled> extends VoidCommand {
	private static final long serialVersionUID = 1L;
	private T obj;
	
	private Class<T> clazz;
	private Serializable id;
	
	private boolean executeByObj;
	
	public LogicallyDeleteCommand(T obj){
		this.obj = obj;
		executeByObj = true;
	}
	
	public LogicallyDeleteCommand(Class<T> clazz, Serializable id){
		this.clazz = clazz;
		this.id = id;
		executeByObj = false;
	}
	@Override
	protected void executeVoid(Environment environment) throws Exception {
		SessionFactory sessionFactory = environment.get(SessionFactory.class);
		Session session = sessionFactory.getCurrentSession();
		if(executeByObj){
			obj.disable();
			session.update(obj);
		}else{
			Enabled obj = (Enabled)session.get(clazz, id);
			obj.disable();
			session.update(obj);
		}
	}

}
