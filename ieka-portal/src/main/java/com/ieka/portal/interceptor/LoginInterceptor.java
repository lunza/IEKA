package com.ieka.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ieka.common.utils.CookieUtils;
import com.ieka.pojo.TbUser;
import com.ieka.portal.service.UserService;
import com.ieka.portal.service.impl.UserServiceImpl;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private UserServiceImpl userService;

	// 在Handler执行之前处理,返回值决定Handler是否执行,true执行,false不执行
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 判断用户是否登录,从cookie中取token
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		// 根据token换取用户信息,调用sso系统接口
		TbUser user = userService.getUserByToken(token);
		// 取不到,url跳转到登录页面,并把用户请求的url作为参数传递给登录页面,返回false(拦截)
		if (null == user) {
			response.sendRedirect(
					userService.SSO_BASE_URL + userService.SSO_PAGE_LOGIN + "?redirct=" + request.getRequestURL());
			return false;
		}
		// 取到了,返回true(放行)
		return true;
	}

	// 在Handler执行之后,返回ModelAndView之前处理
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	// 在返回ModelAndView之后处理
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
