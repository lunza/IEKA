package com.ieka.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ieka.common.pojo.IEKAResult;
import com.ieka.common.utils.JsonUtils;
import com.ieka.mapper.TbItemDescMapper;
import com.ieka.mapper.TbItemMapper;
import com.ieka.mapper.TbItemParamItemMapper;
import com.ieka.pojo.TbItem;
import com.ieka.pojo.TbItemDesc;
import com.ieka.pojo.TbItemParamItem;
import com.ieka.pojo.TbItemParamItemExample;
import com.ieka.pojo.TbItemParamItemExample.Criteria;
import com.ieka.rest.dao.JedisClient;
import com.ieka.rest.service.ItemService;

/**
 * 商品信息管理
 * 
 * @author fx50j
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;

	@Override
	public IEKAResult getItemBaseInfo(long itemId) {
		try {
			// 添加缓存逻辑
			// 从缓存逻辑中取商品信息,商品id对应信息
			String json = jedisClient.get(REDIS_ITEM_KEY);
			if (!StringUtils.isBlank(json)) {
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				return IEKAResult.ok(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItem item = itemMapper.selectByPrimaryKey(itemId);

		// 把商品信息放入缓存
		try {
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base", JsonUtils.objectToJson(item));
			// 设置key的有效期
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用iekaResult包装一下
		return IEKAResult.ok(item);
	}

	@Override
	public IEKAResult getItemDesc(long itemId) {
		try {
			// 添加缓存逻辑
			// 从缓存逻辑中取商品信息,商品id对应信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
			if (!StringUtils.isBlank(json)) {
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return IEKAResult.ok(itemDesc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemDesc desc = itemDescMapper.selectByPrimaryKey(itemId);
		// 把商品信息放入缓存
		try {
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", JsonUtils.objectToJson(desc));
			// 设置key的有效期
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 设置key的有效期
		return IEKAResult.ok(desc);

	}

	@Override
	public IEKAResult getItemParam(long itemId) {
		//添加缓存
		try {
			//添加缓存逻辑
			//从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
			//判断是否有值
			if (!StringUtils.isBlank(json)) {
				//把json转换成java对象
				TbItemParamItem paramItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				return IEKAResult.ok(paramItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//根据商品id查询规格参数
		//设置查询条件
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size()>0) {
			TbItemParamItem paramItem = list.get(0);
			try {
				//把商品信息写入缓存
				jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
				//设置key的有效期
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return IEKAResult.ok(paramItem);
		}
		return IEKAResult.build(400, "无此商品规格");
	}

}
