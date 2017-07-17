package com.ieka.service;

import com.ieka.common.pojo.EUDataGridResult;
import com.ieka.common.pojo.IEKAResult;
import com.ieka.pojo.TbItem;

public interface ItemService {
	public TbItem getItemById(long itemId);

	EUDataGridResult getItemList(int page, int rows);


	IEKAResult createItem(TbItem item, String desc, String itemParam) throws Exception;
}
