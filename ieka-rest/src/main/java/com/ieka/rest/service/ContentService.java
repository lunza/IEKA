package com.ieka.rest.service;

import java.util.List;

import com.ieka.pojo.TbContent;

public interface ContentService {

	List<TbContent> getContentList(long contentCid);
}
