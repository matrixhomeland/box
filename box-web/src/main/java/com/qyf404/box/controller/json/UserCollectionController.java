package com.qyf404.box.controller.json;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qyf404.box.model.User;
import com.qyf404.box.service.UserService;


@Controller
@RequestMapping(value="/users")
public class UserCollectionController {
	@Autowired
	private UserService userService;

	private Gson gson;
	
	@PostConstruct
	public void init(){
		
		GsonBuilder gsonBuilder = new GsonBuilder()  
        .excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性  
        .enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式  
        .serializeNulls()
        .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")//时间转化为特定格式    
        //.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)//会把字段首字母大写,注:对于实体上使用了@SerializedName注解的不会生效.  
        .setPrettyPrinting() //对json结果格式化.  
        .setVersion(1.0);    //有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.  
                            //@Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么  
                            //@Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用.  
		
		
		setGson(gsonBuilder.create());
		
	}
	
	public void setGson(Gson gson) {
		this.gson = gson;
	}
	
	protected Gson getGson() {
		return gson;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@ResponseBody()
	@RequestMapping(value = "", method = { RequestMethod.GET })
	public String get(HttpServletRequest request,HttpServletResponse response){
		int firstResult = Integer.valueOf(request.getParameter("firstResult"));
		int maxResult = Integer.valueOf(request.getParameter("maxResult"));
		
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("firstResult", firstResult);
		map.put("maxResult", maxResult);
		
		Collection<User> userColl = userService.getCollection(map);
		return getGson().toJson(userColl);
	}
	
}
