package com.ieka.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ieka.common.pojo.IEKAResult;
import com.ieka.rest.service.ItemService;
/**
 * 商品信息管理
 * @author fx50j
 *
 */
@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping("/info/{itemId}")
	@ResponseBody
	public IEKAResult getItemBaseInfo(@PathVariable Long itemId) {
		IEKAResult result = itemService.getItemBaseInfo(itemId);
		return result;
	}
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public IEKAResult getItemDesc(@PathVariable Long itemId) {
		IEKAResult result = itemService.getItemDesc(itemId);
		return result;
	}
	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public IEKAResult getItemParam(@PathVariable Long itemId) {
		IEKAResult result = itemService.getItemParam(itemId);
		return result;
	}

}
