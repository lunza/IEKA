package com.ieka.rest.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * solrj测试
 * @author fx50j
 *
 */
public class SolrJTest {
	@Test
	public void addDocument() throws Exception{
		//创建连接(单机版http,集群版cloud)
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.133:8080/solr");
		//创建索引文档对象
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "test001");
		document.addField("item_title", "测试商品2");
		document.addField("item_price", 54321);
		//将索引文档写入索引库
		solrServer.add(document);		
		//提交
		solrServer.commit();
	}
	@Test
	public void deleteDocument()throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.133:8080/solr");
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
		
		
	}
	@Test
	public void queryDocument() throws Exception{
		SolrServer server = new HttpSolrServer("http://192.168.25.133:8080/solr");
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		query.setStart(1);
		query.setRows(10);
		QueryResponse response = server.query(query);
		SolrDocumentList results = response.getResults();
		System.err.println("共查询到记录:"+results.getNumFound());
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
		
		}
	}
}
