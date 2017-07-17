package com.ieka.order.service;

import java.util.List;

import com.ieka.common.pojo.IEKAResult;
import com.ieka.pojo.TbOrder;
import com.ieka.pojo.TbOrderItem;
import com.ieka.pojo.TbOrderShipping;

public interface OrderService {
	IEKAResult createOrder(TbOrder order, List<TbOrderItem> itemList, TbOrderShipping orderShipping);
}
