package com.ieka.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ieka.common.pojo.EUTreeNode;
import com.ieka.common.pojo.IEKAResult;
import com.ieka.mapper.TbContentCategoryMapper;
import com.ieka.pojo.TbContentCategory;
import com.ieka.pojo.TbContentCategoryExample;
import com.ieka.pojo.TbContentCategoryExample.Criteria;
import com.ieka.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		// 根据parentId查询节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 查询所有节点
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<>();
		// 遍历节点取出信息添加到EUTreeNode中供EasyUI使用
		for (TbContentCategory tbContentCategory : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			// 是父节点则关闭,不是则展开
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public IEKAResult insertContentCategory(long parentId, String name) {
		// 设置新增节点属性
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		// 1正常,2删除
		contentCategory.setStatus(1);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);

		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		// 添加记录
		contentCategoryMapper.insert(contentCategory);
		// 查看父节点isParent是否为true,如果不是则设为true
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			// 更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}

		return IEKAResult.ok(contentCategory);
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@Override
	public IEKAResult deleteContentCategory(long parentId, long id) {
		
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		contentCategoryMapper.deleteByPrimaryKey(id);

		if (parentCat.getIsParent()) {
			parentCat.setIsParent(false);
			// 更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		return IEKAResult.ok();
	}

	@Override
	public IEKAResult updateContentCategory(long id, String name) {

		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		contentCategory.setName(name);

		return IEKAResult.ok(contentCategory);
	}

}
