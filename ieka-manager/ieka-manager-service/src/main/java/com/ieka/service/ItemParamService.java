package com.ieka.service;

import com.ieka.common.pojo.IEKAResult;
import com.ieka.pojo.TbItemParam;

public interface ItemParamService {
	
	public IEKAResult getItemParamByCid(long cid);
	public IEKAResult insertItemParam(TbItemParam itemParam);

}
