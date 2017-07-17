package com.ieka.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.ieka.common.pojo.IEKAResult;
import com.ieka.common.utils.CookieUtils;
import com.ieka.common.utils.JsonUtils;
import com.ieka.mapper.TbUserMapper;
import com.ieka.pojo.TbUser;
import com.ieka.pojo.TbUserExample;
import com.ieka.pojo.TbUserExample.Criteria;
import com.ieka.sso.dao.JedisClient;
import com.ieka.sso.service.UserService;

/**
 * 校验服务
 * 
 * @author fx50j
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	@Value("${SSO_SESSION_EXPIRE}")
	private Integer SSO_SESSION_EXPIRE;

	/**
	 * 对数据进行校验
	 */
	@Override
	public IEKAResult checkData(String content, Integer type) {
		// 创建查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 对数据进行校验,1,2,3分别代表username,phone,email
		if (1 == type) {
			criteria.andUsernameEqualTo(content);
			// 电话校验
		} else if (2 == type) {
			criteria.andPhoneEqualTo(content);
			// 邮箱校验
		} else if (3 == type) {
			criteria.andEmailEqualTo(content);
		}
		List<TbUser> list = userMapper.selectByExample(example);
		if (list == null || list.size() > 0) {
			return IEKAResult.ok(false);
		}
		return IEKAResult.ok(true);
	}

	/**
	 * 注册
	 */
	@Override
	public IEKAResult createUser(TbUser user) {

		user.setCreated(new Date());
		user.setUpdated(new Date());
		// 使用spring框架提供的MD5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.insert(user);

		return IEKAResult.ok();
	}

	/**
	 * 用户登录,登录成功后将用户信息写入cookie和redis
	 */
	@Override
	public IEKAResult userLogin(String username, String password,HttpServletRequest request,HttpServletResponse response) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		// 验证用户名
		if (null == list || list.size() == 0) {
			return IEKAResult.build(400, "用户名或密码错误!");
		}
		TbUser tbUser = list.get(0);
		// 验证密码
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(tbUser.getPassword())) {
			return IEKAResult.build(400, "用户名或密码错误!");
		}
		// 验证通过,生成token
		String token = UUID.randomUUID().toString();
		// 保存用户之前清除用户对象中的密码
		tbUser.setPassword(null);
		// 把用户信息写入redis
		jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(tbUser));
		// 设置session的过期时间(30分钟)
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
		
		//添加cookie,不设失效时间,关闭浏览器自动清除
		CookieUtils.setCookie(request, response, "TT_TOKEN", token);
		// 返回token
		return IEKAResult.ok(token);
	}

	/**
	 * 根据token从redis中查询用户信息
	 */
	@Override
	public IEKAResult getUserByToken(String token) {
		
		//根据token从redis中查询用户信息
		String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
		//判断是否为空
		if (StringUtils.isBlank(json)) {
			return IEKAResult.build(400, "此session已经过期，请重新登录");
		}
		//更新过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
		//返回用户信息
		return IEKAResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));
	}


}
