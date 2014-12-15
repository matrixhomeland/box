package com.qyf404.box.common.cmd;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.qyf404.box.core.cmd.VoidCommand;
import com.qyf404.box.core.env.Environment;

public class SaveOrUpdateCommand<T> extends VoidCommand {
	private static final long serialVersionUID = 1L;
	private T obj;
	public SaveOrUpdateCommand(T obj) {
		this.obj = obj;
	}

	@Override
	protected void executeVoid(Environment environment) throws Exception {
		SessionFactory sessionFactory = environment.get(SessionFactory.class);
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
	}

}
