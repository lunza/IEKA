package com.ieka.order.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ieka.common.pojo.IEKAResult;
import com.ieka.mapper.TbOrderItemMapper;
import com.ieka.mapper.TbOrderMapper;
import com.ieka.mapper.TbOrderShippingMapper;
import com.ieka.order.dao.JedisClient;
import com.ieka.order.service.OrderService;
import com.ieka.pojo.TbOrder;
import com.ieka.pojo.TbOrderItem;
import com.ieka.pojo.TbOrderShipping;
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${ORDER_INIT_ID}")
	private String ORDER_INIT_ID;
	@Value("${ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;
	@Value("${ORDER_DETAIL_GEN_KEY}")
	private String ORDER_DETAIL_GEN_KEY;
/**
 * 订单生成
 */
	@Override
	public IEKAResult createOrder(TbOrder order, List<TbOrderItem> itemList, TbOrderShipping orderShipping) {
		// 插入订单表
		//获得订单号
		String string = jedisClient.get(ORDER_GEN_KEY);
		if(StringUtils.isBlank(string)){
			jedisClient.set(ORDER_GEN_KEY, ORDER_INIT_ID);
		}
		long orderId = jedisClient.incr(ORDER_GEN_KEY);
		//补全pojo
		order.setOrderId(orderId+"");
		//插入订单明细:1.未付款 2.已付款 3.未发货 4.已发货 5.交易成功 6.交易过期
		order.setStatus(1);
		Date date = new Date();
		order.setCreateTime(date);
		order.setUpdateTime(date);
		//0.未评价 1.已评价
		order.setBuyerRate(0);
		//插入数据
		orderMapper.insert(order);
		//插入订单明细
		for ( TbOrderItem tbOrderItem : itemList) {
			long detailOrderId = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
			tbOrderItem.setId(detailOrderId+"");
			tbOrderItem.setOrderId(orderId+"");
			//向订单明细插入记录
			orderItemMapper.insert(tbOrderItem);
		}
		//插入物流表
		orderShipping.setOrderId(orderId+"");
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		return IEKAResult.ok(orderId);
	}

}
