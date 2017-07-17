package com.ieka.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ieka.common.pojo.EUTreeNode;
import com.ieka.common.pojo.IEKAResult;
import com.ieka.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;

	/**
	 * 内容分类初始化
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		List<EUTreeNode> categoryList = contentCategoryService.getCategoryList(parentId);
		return categoryList;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public IEKAResult createContentCateGory(Long parentId,String name){
		IEKAResult result = contentCategoryService.insertContentCategory(parentId, name);
		return result;
	}
	@RequestMapping("/delete")
	@ResponseBody
	public IEKAResult deleteContentCateGory(Long parentId,Long id){
		IEKAResult result = contentCategoryService.deleteContentCategory(parentId, id);
		return result;
		
	}
	@RequestMapping("/update")
	@ResponseBody
	public IEKAResult updateContentCateGory(Long id,String name){
		IEKAResult result = contentCategoryService.updateContentCategory(id, name);
		return result;
		
	}
}
