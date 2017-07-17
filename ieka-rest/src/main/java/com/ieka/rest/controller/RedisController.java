package com.ieka.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ieka.common.pojo.IEKAResult;
import com.ieka.rest.service.RedisService;

/**
 * 缓存同步controller
 * @author fx50j
 *
 */
@Controller
@RequestMapping("/cache/sync")
public class RedisController {

	@Autowired
	private RedisService redisService;
	
	@RequestMapping("/content/{contentCid}")
	@ResponseBody
	public IEKAResult contentSync(@PathVariable Long contentCid){
		IEKAResult result = redisService.syncContent(contentCid);
		return result;
		
	}
}
