package com.ieka.order.dao;

public interface JedisClient {

	String get(String key);

	String set(String key, String value);

	// 基于hash的get和set,便于分类存储大量数据
	String hget(String hkey, String key);

	Long hset(String hkey, String key, String value);

	// 自增
	long incr(String key);

	// key的过期时间
	long expire(String key, int second);
	
	long ttl(String key);
	
	public long del(String key);
	
	long hdel(String hkey,String key);
}
