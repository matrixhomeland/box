package com.qyf404.box.common.web.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


public abstract class AbstractCollectionController<T> {
	private static Logger log = LoggerFactory
			.getLogger(AbstractCollectionController.class);
	
	@ResponseBody()
	@RequestMapping(method = { RequestMethod.GET })
	public String get(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String id) {
		String result;
		try {
			result = doGet(request, response, id);

			// response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			log.error("请求处理失败", e);
			result = doGetErrorResult();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		log.debug("result={}", result);
		return result;
	}

	protected abstract String doGet(HttpServletRequest request,
			HttpServletResponse response, String id);

	protected String doGetErrorResult() {
		return doErrorResult();
	}

	@ResponseBody()
	@RequestMapping(method = { RequestMethod.POST })
	public String post(HttpServletRequest request, HttpServletResponse response) {
		String result;
		try {
			result = doPost(request, response);

			//response.setStatus(HttpServletResponse.SC_CREATED);
		} catch (Exception e) {
			log.error("请求处理失败", e);
			result = doPostErrorResult();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	protected abstract String doPost(HttpServletRequest request,
			HttpServletResponse response);

	protected String doPostErrorResult() {
		return doErrorResult();
	}

	@ResponseBody()
	@RequestMapping( method = { RequestMethod.PUT })
	public String put(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) {
		String result;
		try {
			result = doPut(request, response, id);

			//response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			log.error("请求处理失败", e);
			result = doPutErrorResult();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	protected abstract String doPut(HttpServletRequest request,
			HttpServletResponse response, String id);

	protected String doPutErrorResult() {
		return doErrorResult();
	}

	@ResponseBody()
	@RequestMapping( method = { RequestMethod.DELETE })
	public String delete(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String id) {
		String result;
		try {
			result = doDelete(request, response, id);

			//response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			log.error("请求处理失败", e);
			result = doDeleteErrorResult();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		return result;

	}

	protected abstract String doDelete(HttpServletRequest request,
			HttpServletResponse response, String id);

	protected String doDeleteErrorResult() {
		return doErrorResult();
	}


	@ResponseBody()
	@RequestMapping( method = { RequestMethod.PATCH })
	public String patch(HttpServletRequest request, HttpServletResponse response) {
		String result;
		try {
			result = doPatch(request, response);
			
			//response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			log.error("请求处理失败", e);
			result = doPatchErrorResult();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		return result;
		
	}

	protected abstract String doPatch(HttpServletRequest request,
			HttpServletResponse response) ;

	protected String doPatchErrorResult() {
		return doErrorResult();
	}

	protected String doErrorResult() {
		return "";
	}
}
