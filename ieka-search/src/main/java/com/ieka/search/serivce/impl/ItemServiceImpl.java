package com.ieka.search.serivce.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ieka.common.pojo.ExceptionUtil;
import com.ieka.common.pojo.IEKAResult;
import com.ieka.common.pojo.Item;
import com.ieka.search.mapper.ItemMapper;

import com.ieka.search.serivce.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;

	@Override
	public IEKAResult importAllItems() {

		try {
			List<Item> list = itemMapper.getItemList();
			for (Item item : list) {
				// 创建一个SolrInputDocument对象
				SolrInputDocument document = new SolrInputDocument();
				document.setField("id", item.getId());
				document.setField("item_title", item.getTitle());
				document.setField("item_sell_point", item.getSell_point());
				document.setField("item_price", item.getPrice());
				document.setField("item_image", item.getImage());
				document.setField("item_category_name", item.getCategory_name());
				document.setField("item_desc", item.getItem_des());
				// 写入索引库
				solrServer.add(document);
			}
			solrServer.commit();
		} catch (Exception e) {
			return IEKAResult.build(500, ExceptionUtil.getStackTrace(e));

		}

		return IEKAResult.ok();
	}

}
