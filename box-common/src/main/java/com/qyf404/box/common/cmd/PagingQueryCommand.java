package com.qyf404.box.common.cmd;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.qyf404.box.core.cmd.QueryCommand;
import com.qyf404.box.core.env.Environment;

public class PagingQueryCommand<Collection> extends QueryCommand<Collection> {

	private Class clazz;
	
	public PagingQueryCommand(Class clazz , int firstResult, int maxResults) {
		super(firstResult, maxResults);
		this.clazz = clazz;
	}

	@Override
	public Collection execute(Environment environment) throws Exception {
		SessionFactory sessionFactory = environment.get(SessionFactory.class);
		Session session = sessionFactory.getCurrentSession();
		List list = session.createCriteria(clazz).setFirstResult(firstResult).setMaxResults(maxResults).list();
		
		return  (Collection) list;
	}

}
