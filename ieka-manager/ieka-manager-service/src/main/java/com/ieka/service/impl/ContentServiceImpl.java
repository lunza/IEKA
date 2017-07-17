package com.ieka.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ieka.common.pojo.ExceptionUtil;
import com.ieka.common.pojo.IEKAResult;
import com.ieka.common.utils.HttpClientUtil;
import com.ieka.mapper.TbContentMapper;
import com.ieka.pojo.TbContent;
import com.ieka.service.ContentService;
/**
 * 内容管理
 * @author fx50j
 *
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	@Override
	public IEKAResult insertContent(TbContent content) {
		// 补全pojo
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		//添加缓存同步逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId());
		} catch (Exception e) {
			// 失败记录日志通知管理员
			return IEKAResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return IEKAResult.ok();
	}
}
