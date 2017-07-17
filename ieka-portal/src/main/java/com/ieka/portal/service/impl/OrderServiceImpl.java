package com.ieka.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ieka.common.pojo.IEKAResult;
import com.ieka.common.utils.HttpClientUtil;
import com.ieka.common.utils.JsonUtils;
import com.ieka.portal.pojo.Order;
import com.ieka.portal.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;
	

	@Override
	public String createOrder(Order order) {
		//调用taotao-order的服务提交订单。
		String json = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, JsonUtils.objectToJson(order));
		//把json转换成taotaoResult
		IEKAResult taotaoResult = IEKAResult.format(json);
		if (taotaoResult.getStatus() == 200) {
			Integer orderId = (Integer) taotaoResult.getData();
			return orderId.toString();
		}
		return "";
	}

}

