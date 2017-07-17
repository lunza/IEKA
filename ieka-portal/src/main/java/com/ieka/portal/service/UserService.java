package com.ieka.portal.service;

import com.ieka.pojo.TbUser;

public interface UserService {

	TbUser getUserByToken(String token);
}
