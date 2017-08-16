box
===

[![Build Status](https://travis-ci.org/qyf404/box.svg?branch=master)](https://travis-ci.org/qyf404/box)


* 这个项目主要是实现一套通用的java服务器端的框架。方便快速搭建一个可用的并且可以快速开发的基础代码。
* 项目计划将spring、hibernate、springMVC整合起来。

特性
===
该框架摒弃了传统的三层结构（controller－service－dao），因为传统的三层结构往往会在service层注入各种service，导致在service层的代码耦合度很高。
换而使用命令模式来代替dao层，让dao层的代码全部变成命令，在service层执行一个一个命令来操作数据库数据。例如下面这段代码片段：

  
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
	}
  
