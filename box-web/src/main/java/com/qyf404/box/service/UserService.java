package com.qyf404.box.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.qyf404.box.common.cmd.GetCommand;
import com.qyf404.box.core.cmd.Command;
import com.qyf404.box.core.env.Environment;
import com.qyf404.box.core.svc.CommandService;
import com.qyf404.box.model.User;

@Service
public class UserService{
	@Resource(name = "txRequiredCommandService")
	private CommandService commandService;


	public User getUserByUsername(final String username){
		
		return commandService.execute(new Command<User>() {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("unchecked")
			@Override
			public User execute(Environment environment) throws Exception {
				
				SessionFactory sessionFactory = environment.get(SessionFactory.class);
				Session session = sessionFactory.getCurrentSession();
				Query query = session.createQuery("from User").setString("username", username).setFirstResult(0).setMaxResults(1);
				List<User> list = query.list();
				if(list == null || list.size() == 0){
					return null;
				}
				return list.get(0);
			}
		});
	}
	public User getUserByEmail(final String email){
		return commandService.execute(new Command<User>() {

			@Override
			public User execute(Environment environment) throws Exception {
				SessionFactory sessionFactory = environment.get(SessionFactory.class);
				Session session = sessionFactory.getCurrentSession();
				Query query = session.createQuery("from User").setString("email", email).setFirstResult(0).setMaxResults(1);
				List<User> list = query.list();
				if(list == null || list.size() == 0){
					return null;
				}
				return list.get(0);
			}
			
		});
	}
	
	public User get(java.io.Serializable id) {
		return commandService.execute(new GetCommand<User>(User.class, id));
	}
	

	public User save(User user) {
		user.save(commandService);
		return user;
	}

	public User update(User user) {
		User persistentUser = this.get(user.getId());
		if (StringUtils.isNotBlank(user.getEmail())) {
			persistentUser.setEmail(user.getEmail());
		}

		persistentUser.update(commandService);
		return persistentUser;
	}

	public void delete(User user) {
		user.delete(commandService);
	}
	
	public Collection<User> getCollection(final Map<String,?> map){
		return commandService.execute(new Command<Collection<User>>() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public Collection<User> execute(Environment environment)
					throws Exception {
				SessionFactory sessionFactory = environment.get(SessionFactory.class);
				Session session = sessionFactory.getCurrentSession();
				Query query = session.createQuery("from User");
				query.setFirstResult((Integer)map.get("firstResult"))
					.setMaxResults((Integer)map.get("maxResult"));
				
				return query.list();
			}
		});
	}

	
}
