package com.ieka.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ieka.common.pojo.IEKAResult;
import com.ieka.common.pojo.Item;
import com.ieka.common.utils.HttpClientUtil;
import com.ieka.common.pojo.SearchResult;
import com.ieka.portal.service.SearchService;

/**
 * 商品搜索Service
 
 */

@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	
	@Override
	public SearchResult search(String queryString, int page) {
		// 调用ieka-search的服务
		//查询参数
		Map<String, String> param = new HashMap<>();
		param.put("q", queryString);
		param.put("page", page + "");
		try {
			//调用服务
			String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
			System.out.println(json);
			//把字符串转换成java对象
			IEKAResult iekaResult = IEKAResult.formatToPojo(json, SearchResult.class);
			if(iekaResult==null){
				Item item = new Item();
				item.setId("148950835038789");
				item.setPrice(100);
				item.setImage("http://192.168.25.133/images/2017/03/15/1489508343650969.jpg");
				item.setTitle("cat");
				item.setSell_point("catcat");
				item.setItem_des("catcat");
				item.setCategory_name("类目111");
				List list = new ArrayList<>();
				list.add(item);
				return new SearchResult(list,1,1,1);
			}
			if (iekaResult.getStatus() == 200) {
				SearchResult result = (SearchResult) iekaResult.getData();
				return result;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

