package com.ieka.rest.service;

import com.ieka.common.pojo.IEKAResult;

public interface RedisService {

	IEKAResult syncContent(long contentCid);
}
