package com.ieka.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ieka.common.pojo.EUDataGridResult;
import com.ieka.common.pojo.IEKAResult;
import com.ieka.common.utils.IDUtils;
import com.ieka.mapper.TbItemDescMapper;
import com.ieka.mapper.TbItemMapper;
import com.ieka.mapper.TbItemParamItemMapper;
import com.ieka.pojo.TbItem;
import com.ieka.pojo.TbItemDesc;
import com.ieka.pojo.TbItemExample;
import com.ieka.pojo.TbItemExample.Criteria;
import com.ieka.pojo.TbItemParamItem;
import com.ieka.service.ItemService;

/**
 * 商品管理Service
 * <p>
 * Title: ItemServiceImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: www.ieka.com
 * </p>
 * 
 * @author 孙菁
 * @date 2017年1月22日上午10:47:14
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	/**
	 * 根据id查询商品信息
	 */
	@Override
	public TbItem getItemById(long itemId) {

		// TbItem item = itemMapper.selectByPrimaryKey(itemId);
		// 添加查询条件
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			TbItem item = list.get(0);
			return item;
		}
		return null;
	}

	/**
	 * 商品列表查询
	 * @param page
	 * @param rows
	 * @return
	 * @see com.taotao.service.ItemService#getItemList(long, long)
	 */
	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		// 查询商品列表
		TbItemExample example = new TbItemExample();
		// 分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	/**
	 * 添加商品
	 */
	@Override
	public IEKAResult createItem(TbItem item, String desc, String itemParam) throws Exception {
		// 需要生成一个商品ID(IDUtils)
		Long itemId = IDUtils.genItemId();
		item.setId(itemId);
		// 商品状态(1-正常,2-下架,3-删除)
		item.setStatus((byte) 1);
		// 商品添加日期,修改日期
		item.setCreated(new Date());
		item.setUpdated(new Date());
		// 插入到数据库
		itemMapper.insert(item);
		// 添加商品描述信息
		IEKAResult result = InsertItemDesc(itemId, desc);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		// 添加规格参数
		result=insertItemParamItem(itemId, itemParam);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		// 返回添加成功信息
		return IEKAResult.ok();
	}

	/**
	 * 添加商品描述
	 * 
	 * @param itemId
	 * @param desc
	 * @return
	 */
	private IEKAResult InsertItemDesc(Long itemId, String desc) {
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(itemId);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());

		itemDescMapper.insert(itemDesc);
		return IEKAResult.ok();
	}

	/**
	 * 添加商品规格参数 
	 * @param itemId
	 * @param itemParam
	 * @return
	 */
	private IEKAResult insertItemParamItem(Long itemId, String itemParam) {

		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		itemParamItemMapper.insert(itemParamItem);
		return IEKAResult.ok();
	}
}
