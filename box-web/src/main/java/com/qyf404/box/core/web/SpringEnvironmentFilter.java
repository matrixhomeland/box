package com.qyf404.box.core.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 配置spring环境的filter。保证每个进入的请求都有一个默认的spring工厂环境。
 * @author qyfmac
 *
 */
public class SpringEnvironmentFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
//		EnvironmentImpl.pushEnvironment(DefaultEnvironment.getInstance().openEnvironment());
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
