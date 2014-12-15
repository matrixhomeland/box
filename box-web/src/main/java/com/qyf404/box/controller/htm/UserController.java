//package com.qyf404.box.controller.htm;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.qyf404.box.core.env.EnvironmentFactory;
//import com.qyf404.box.core.env.EnvironmentImpl;
//import com.qyf404.box.model.UserModel;
//import com.qyf404.box.service.UserService;
//
////@Controller
//@RequestMapping(value="/user")
//public class UserController {
//	private static Logger log = LoggerFactory.getLogger(UserController.class);
////	@Resource(name="springEnvironmentFactory")
////	private EnvironmentFactory springEnvironmentFactory ;
//	
//	
//	@Autowired
//	private UserService userService;
//	
//	@ResponseBody
//	@RequestMapping(value="/{id}", method=RequestMethod.GET)
//	public String get(HttpServletRequest request, HttpServletResponse response,@PathVariable String id){
//		log.debug("id={}",id );
//		
//		UserModel user = userService.get(id);
//		
//		return "user/user";
//	}
//	
//	@RequestMapping(value="", method=RequestMethod.POST)
//	public String post(HttpServletRequest request, HttpServletResponse response, UserModel user){
//		
//		return "success";
//	}
//	
//	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
//	public String delete(HttpServletRequest request, HttpServletResponse response,@PathVariable String id){
//		log.debug("id={}",id);
//		
//		
//		return "success";
//		
//	}
//}
