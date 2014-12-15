package com.qyf404.box.controller.json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.qyf404.box.common.web.rest.AbstractEntityController;
import com.qyf404.box.model.User;
import com.qyf404.box.service.UserService;

@Controller
@RequestMapping(value="/user")
public class UserController extends AbstractEntityController{
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	
	@Autowired
	private UserService userService;

	private Gson gson;
	@Autowired
	public void setGson(@Qualifier("gson") Gson gson) {
		this.gson = gson;
	}
	
	@Override
	protected Gson getGson() {
		return gson;
	}
	
	@Override
	protected Object doGet(HttpServletRequest request,
			HttpServletResponse response, String id) {
		log.debug("ContentType="+request.getContentType());
		User user = userService.get(id);
		return user;
	}

	@Override
	protected Object doPost(HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("ContentType="+request.getContentType());
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		User user = new User() ;
		user.setUsername(name);
		user.setPassword(password);
		user.setEmail(email);
		
		return userService.save(user);
	}

	@Override
	protected Object doPut(HttpServletRequest request,
			HttpServletResponse response, String id) {
		String email = request.getParameter("email");
		User user = new User() ;
		user.setId(Integer.valueOf(id));
		user.setEmail(email);
		
		return userService.update(user);
	}

	@Override
	protected Object doDelete(HttpServletRequest request,
			HttpServletResponse response, String id) {
		User user = new User() ;
		user.setId(Integer.valueOf(id));
		userService.delete(user);
		return null;
	}


	@Override
	protected Object doPatch(HttpServletRequest request,
			HttpServletResponse response, String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
