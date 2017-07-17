package com.ieka.service;

import java.util.List;

import com.ieka.common.pojo.EUTreeNode;
import com.ieka.common.pojo.IEKAResult;
public interface ContentCategoryService {

	List<EUTreeNode> getCategoryList(long parentId);
	IEKAResult insertContentCategory(long parentId,String name);
	IEKAResult deleteContentCategory(long parentId,long id);
	IEKAResult updateContentCategory(long Id,String name);
}
