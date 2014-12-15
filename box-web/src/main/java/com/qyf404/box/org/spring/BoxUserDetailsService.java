package com.qyf404.box.org.spring;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.qyf404.box.model.User;
import com.qyf404.box.service.UserService;

/**
 * 实现spring的用户管理接口
 * 
 * @author qyfmac
 * 
 */
@Service
public class BoxUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = userService.getUserByUsername(username);

		Collection<? extends GrantedAuthority> auths = new ArrayList<GrantedAuthority>();

		// 得到用户的权限
		auths = user.getAuthorities();

		String password = null;

		// 取得用户的密码
		password = user.getPassword();

		return new org.springframework.security.core.userdetails.User(username,
				password, true, true, true, true, auths);

	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
