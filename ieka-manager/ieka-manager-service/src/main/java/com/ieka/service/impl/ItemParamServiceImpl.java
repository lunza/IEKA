package com.ieka.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ieka.common.pojo.IEKAResult;
import com.ieka.mapper.TbItemParamMapper;
import com.ieka.pojo.TbItemParam;
import com.ieka.pojo.TbItemParamExample;
import com.ieka.pojo.TbItemParamExample.Criteria;
import com.ieka.service.ItemParamService;

/**
 * 商品规格参数模板
 * 
 * @author fx50j
 *
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;

	/**
	 * 做一个对于模板的查询,判断模板是否已添加到数据库
	 * 
	 * @param 模板id
	 * @return 模板对象
	 */
	@Override
	public IEKAResult getItemParamByCid(long cid) {
		// 使用criteria创建查询条件
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		// 判断是否查询到结果
		if (list != null && list.size() > 0) {
			return IEKAResult.ok(list.get(0));
		}
		return IEKAResult.ok();
	}

	/**
	 * 将模板插入数据库
	 */
	@Override
	public IEKAResult insertItemParam(TbItemParam itemParam) {
		// 补全pojo
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		// 插入数据
		itemParamMapper.insert(itemParam);
		return IEKAResult.ok();
		
		
	}

}
