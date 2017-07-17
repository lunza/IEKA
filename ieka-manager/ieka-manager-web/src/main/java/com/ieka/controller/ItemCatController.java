package com.ieka.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ieka.common.pojo.TreeNode;
import com.ieka.service.ItemCatService;

/**
 * 商品分类管理
 * <p>Title: ItemCatController</p>
 * <p>Description: </p>
 * <p>Company: www.ieka.com</p> 
 * @author	孙菁
 * @date	2017年2月15日上午9:49:18
 * @version 1.0
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<TreeNode> getItemCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {
		List<TreeNode> list = itemCatService.getItemCatList(parentId);
		return list;
	}
	
}

