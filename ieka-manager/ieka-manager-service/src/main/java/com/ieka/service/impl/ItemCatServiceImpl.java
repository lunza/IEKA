package com.ieka.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ieka.common.pojo.TreeNode;
import com.ieka.mapper.TbItemCatMapper;
import com.ieka.pojo.TbItemCat;
import com.ieka.pojo.TbItemCatExample;
import com.ieka.pojo.TbItemCatExample.Criteria;
import com.ieka.service.ItemCatService;

/**
 * 商品分类管理
 * <p>Title: ItemCatServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.ieka.com</p> 
 * @author	孙菁
 * @date	2017年2月15日上午9:49:18
 * @version 1.0
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	/**
	 * 根据父id查询当前节点的列表
	 */
	
	@Override
	public List<TreeNode> getItemCatList(long parentId) {
		//根据parentId查询分类列表
		//创建类目对象example
		TbItemCatExample example = new TbItemCatExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		//分类列表转换成TreeNode的列表
		List<TreeNode> resultList = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			//创建一个TreeNode对象
			TreeNode node = new TreeNode(tbItemCat.getId(), tbItemCat.getName(), 
					tbItemCat.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		
		return resultList;
	}

}

