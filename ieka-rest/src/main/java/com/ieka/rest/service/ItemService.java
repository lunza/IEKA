package com.ieka.rest.service;

import com.ieka.common.pojo.IEKAResult;

public interface ItemService {
	IEKAResult getItemBaseInfo(long itemId);
	IEKAResult getItemDesc(long itemId);
	IEKAResult getItemParam(long itemId);
}
