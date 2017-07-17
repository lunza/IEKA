package com.ieka.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ieka.common.pojo.ExceptionUtil;
import com.ieka.common.pojo.IEKAResult;
import com.ieka.pojo.TbUser;
import com.ieka.sso.service.UserService;

/**
 * 校验模块
 * 
 * @author fx50j
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param, @PathVariable Integer type, String callback) {
		IEKAResult result = null;

		// 参数有效性校验
		if (StringUtils.isBlank(param)) {
			result = IEKAResult.build(400, "校验内容不能为空!");
		}
		if (type == null) {
			result = IEKAResult.build(400, "校验内容类型不能为空!");
		}
		if (type != 1 && type != 2 && type != 3) {
			result = IEKAResult.build(400, "校验类型错误!");
		}
		// 校验出错
		if (null != result) {
			// jsonp的调用,判断有没有回调信息
			if (null != callback) {
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			} else {
				return result;
			}
		}
		try {
			result = userService.checkData(param, type);
			return result;
		} catch (Exception e) {
			result = IEKAResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		// 校验成功,判断有无回调信息
		if (null != callback) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		} else {
			return result;
		}
	}

	/**
	 * 创建用户controller
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/register")
	@ResponseBody
	public IEKAResult createUser(TbUser user) {
		try {

			IEKAResult result = userService.createUser(user);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return IEKAResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 用户登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public IEKAResult userLogein(String username, String password,HttpServletRequest request,HttpServletResponse response) {
		try {
			IEKAResult result = userService.userLogin(username, password,request,response);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return IEKAResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 支持jsonp的查询用户信息
	 * 
	 * @param token
	 * @param callback
	 * @return
	 */
	@RequestMapping("/token/{token}")
	@ResponseBody
		public Object getUserByToken(@PathVariable String token, String callback) {
		IEKAResult result = null;
			try {
				result = userService.getUserByToken(token);
			} catch (Exception e) {
				e.printStackTrace();
				result = IEKAResult.build(500, ExceptionUtil.getStackTrace(e));
			}
			
			//判断是否为jsonp调用
			if (StringUtils.isBlank(callback)) {
				return result;
			} else {
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			}
			
		}

}
