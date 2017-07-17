package com.ieka.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.ieka.common.pojo.SearchResult;



public interface SearchDao {

	SearchResult search(SolrQuery query) throws Exception;
}
