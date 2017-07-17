package com.ieka.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ieka.common.pojo.ExceptionUtil;
import com.ieka.common.pojo.IEKAResult;
import com.ieka.rest.dao.JedisClient;
import com.ieka.rest.service.RedisService;
/**
 * 缓存同步服务(删除key)
 * @author fx50j
 *
 */
@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;

	@Override
	public IEKAResult syncContent(long contentCid) {

		try {
			jedisClient.hdel(INDEX_CONTENT_REDIS_KEY, contentCid + "");
		} catch (Exception e) {
			return IEKAResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return IEKAResult.ok();
	}
}
