package com.ieka.rest.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ieka.mapper.TbItemCatMapper;
import com.ieka.pojo.TbItemCat;
import com.ieka.pojo.TbItemCatExample;
import com.ieka.pojo.TbItemCatExample.Criteria;
import com.ieka.rest.pojo.CatNode;
import com.ieka.rest.pojo.CatResult;
import com.ieka.rest.service.ItemCatService;

/**
 * 分类服务
 * 
 * @author fx50j
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	

	@Override
	public CatResult getItemCatList() {

		CatResult catResult = new CatResult();
		// 总分类查询结果
		catResult.setData(getCatList(0));
		return catResult;
	}

	/**
	 * 查询单个节点下的子节点
	 * 
	 * @param parentId
	 * @return
	 */
	private List<?> getCatList(long parentId) {
		
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		// 返回值list
		List resultList = new ArrayList<>();
		//计数器,当计数器>=14时中止循环
		int count = 0;
		// 向list中添加节点
		for (TbItemCat tbItemCat : list) {
			//判断是否为父节点,是父节点则添加n,u,i
			if(tbItemCat.getIsParent()){	
			CatNode catNode = new CatNode();
			//主节点
			if (parentId == 0) {
				catNode.setName("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
			//有子节点的子节点
			} else {
				catNode.setName(tbItemCat.getName());
			}
				catNode.setUrl("/products/" + tbItemCat.getId() + ".html");
				// 递归,调用自己查询自己节点下的子节点集合
				catNode.setItem(getCatList(tbItemCat.getId()));
				resultList.add(catNode);
				//计数器+1
				count++;
				//受到版面限制,第一层级只取14条记录,当计数>=14时自动break
				if(parentId == 0&&count>=14){
					break;
				}
				//不是父节点则直接添加字符串
			}else{
				resultList.add("/products/"+tbItemCat.getId()+".html|" + tbItemCat.getName());
			}
		}
		return resultList;

	}

}
