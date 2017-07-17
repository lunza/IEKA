package com.ieka.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ieka.common.pojo.ExceptionUtil;
import com.ieka.common.pojo.IEKAResult;
import com.ieka.pojo.TbContent;
import com.ieka.rest.service.ContentService;

/**
 * 发布服务,查询参数
 * @author fx50j
 *
 */
@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;
	@RequestMapping("/list/{contentCategoryId}")
	@ResponseBody
	public IEKAResult getContentList(@PathVariable Long contentCategoryId){
		List<TbContent> list;
		try {
			list = contentService.getContentList(contentCategoryId);
			return IEKAResult.ok(list);
		} catch (Exception e) {
			// 异常处理,抛出500异常
			return IEKAResult.build(500, ExceptionUtil.getStackTrace(e));
		}		
	}
}
