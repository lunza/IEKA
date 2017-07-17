package com.ieka.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ieka.common.pojo.IEKAResult;
import com.ieka.portal.service.ContentService;

@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;

	@RequestMapping("/index")
	public String showIndex(Model model){
		String adJson = contentService.getContentList();
		model.addAttribute("ad1",adJson);
		return "index";
		
	}
	@RequestMapping(value="/httpclient/post",method=RequestMethod.POST)
	@ResponseBody
	public IEKAResult testPost(){
		return IEKAResult.ok();
	}
}