package com.ieka.rest.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ieka.common.utils.JsonUtils;
import com.ieka.rest.pojo.CatResult;
import com.ieka.rest.service.ItemCatService;
/**
 * 分类列表
 * @author fx50j
 *
 */
@Controller
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping(value="/itemcat/list",produces=MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
	@ResponseBody
	public String getItemCatList(String callback){
		CatResult catResult = itemCatService.getItemCatList();
		//把pojo转换成字符串
		String json = JsonUtils.objectToJson(catResult);
		//拼装返回值
		String result= callback+"("+json+");";
		
		return result;
		
	}
	/**
	 * 使用Spring提供的json工具类
	 * @param callBack
	 * @return
	 */
/*	@RequestMapping(value="/itemcat/list")
	@ResponseBody
	public Object getItemCatList(String callBack){
		CatResult catResult = itemCatService.getItemCatList();
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(catResult);
		mappingJacksonValue.setJsonpFunction(callBack);
		return mappingJacksonValue;
	}*/

}
