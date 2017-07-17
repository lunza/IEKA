package com.ieka.service;

import java.util.List;

import com.ieka.common.pojo.TreeNode;

/**
 * 树节点
 * 
 * @author fx50j
 * @param parentId
 */
public interface ItemCatService {

	public List<TreeNode> getItemCatList(long parentId);

}
