package com.qyf404.box.common.cmd;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.qyf404.box.core.cmd.VoidCommand;
import com.qyf404.box.core.env.Environment;


public class DeleteCommand<T> extends VoidCommand {
	private static final long serialVersionUID = 1L;
	private T obj;
	public DeleteCommand(T obj){
		this.obj = obj;
	}
	@Override
	protected void executeVoid(Environment environment) throws Exception {
		SessionFactory sessionFactory = environment.get(SessionFactory.class);
		Session session = sessionFactory.getCurrentSession();
		session.delete(obj);
	}

}
