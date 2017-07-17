package com.ieka.search.serivce;

import com.ieka.common.pojo.SearchResult;

public interface SearchService {

	public SearchResult search(String queryString, int page, int rows) throws Exception;
}
