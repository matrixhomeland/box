package com.qyf404.box.common.web.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

public abstract class AbstractEntityController {
//	private static Logger log = LoggerFactory.getLogger(AbstractEntityController.class);
	
	
	@ResponseBody()
	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	public String get(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String id) {
		setContenttype(response);
		Object obj = doGet(request, response, id);
		if(obj == null){
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
		return getGson().toJson(obj); 
		
	}

	protected abstract Object doGet(HttpServletRequest request,
			HttpServletResponse response, String id);


	@ResponseBody()
	@RequestMapping(method = { RequestMethod.POST })
	public String post(HttpServletRequest request, HttpServletResponse response) {
		String result = getGson().toJson(doPost(request, response));
		setContenttype(response);
		return result;
	}

	protected abstract Object doPost(HttpServletRequest request,
			HttpServletResponse response);

	@ResponseBody()
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public String put(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) {
		return getGson().toJson(doPut(request, response, id));
		
	}

	protected abstract Object doPut(HttpServletRequest request,
			HttpServletResponse response, String id);


	@ResponseBody()
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public String delete(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String id) {
		return getGson().toJson(doDelete(request, response, id));
		

	}

	protected abstract Object doDelete(HttpServletRequest request,
			HttpServletResponse response, String id);

	
	@ResponseBody()
	@RequestMapping( value = "/{id}", method = { RequestMethod.PATCH })
	public String patch(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) {
		return getGson().toJson(doPatch(request, response, id));
		
		
	}

	protected abstract Object doPatch(HttpServletRequest request,
			HttpServletResponse response, String id) ;

	protected abstract Gson getGson() ;


	protected void setContenttype(HttpServletResponse response){
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
	}
	
}
