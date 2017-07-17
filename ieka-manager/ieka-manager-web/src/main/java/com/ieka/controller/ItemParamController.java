package com.ieka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ieka.common.pojo.IEKAResult;
import com.ieka.pojo.TbItemParam;
import com.ieka.service.ItemParamService;

/**
 * 商品模板管理controller
 * @author fx50j
 *
 */
@Controller
@RequestMapping("/item/param/")
public class ItemParamController {
	@Autowired
	private ItemParamService itemParamService;

	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	public IEKAResult getParamById(@PathVariable long itemCatId) {

		IEKAResult result = itemParamService.getItemParamByCid(itemCatId);
		return result;
	}
	
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public IEKAResult insertItemParam(@PathVariable long cid,String paramData){
		//创建pojo
		TbItemParam itemParam = new TbItemParam();
		itemParam.setParamData(paramData);
		itemParam.setItemCatId(cid);
		//调用service对应方法
		IEKAResult result = itemParamService.insertItemParam(itemParam);
		return result;
		
	}
}
