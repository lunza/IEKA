package com.ieka.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ieka.common.utils.JsonUtils;
import com.ieka.mapper.TbContentMapper;
import com.ieka.pojo.TbContent;
import com.ieka.pojo.TbContentExample;
import com.ieka.pojo.TbContentExample.Criteria;
import com.ieka.rest.dao.JedisClient;
import com.ieka.rest.service.ContentService;
/**
 * 内容管理
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	@Override
	public List<TbContent> getContentList(long contentCid) {
		//从缓存中根据redis常量分配位置取内容
		try {
			String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, contentCid+"");
			if(!StringUtils.isBlank(result)){
				//将字符串转换成list
				List<TbContent> resultList = JsonUtils.jsonToList(result, TbContent.class);
				return resultList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//根据内容分类id查询内容列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentCid);
		//执行查询
		List<TbContent> list = contentMapper.selectByExample(example);
		//向缓存中添加内容(内容list)
		try {
			//需要把list转换成字符串,存入指定的redis区域(常量)中
			String json = JsonUtils.objectToJson(list);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, contentCid+"", json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
