package com.ieka.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ieka.common.pojo.IEKAResult;
import com.ieka.search.serivce.ItemService;

/**
 * 索引库维护
 * @author fx50j
 *
 */
@Controller
@RequestMapping("/manager")
public class ItemController {
	@Autowired
	private ItemService itemService;

	/**
	 * 导入所有商品到索引库
	 */
	@RequestMapping("/importall")
	@ResponseBody
	public IEKAResult importAllItems(){
		IEKAResult result = itemService.importAllItems();
		return result;
	}
}
