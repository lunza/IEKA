package com.ieka.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ieka.common.pojo.IEKAResult;
import com.ieka.pojo.TbUser;

public interface UserService {
	IEKAResult checkData(String content, Integer type);
	IEKAResult createUser(TbUser user);
	IEKAResult userLogin(String username,String password,HttpServletRequest request,HttpServletResponse response);
	
	IEKAResult getUserByToken(String token);
}
