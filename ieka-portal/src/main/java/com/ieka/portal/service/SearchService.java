package com.ieka.portal.service;

import com.ieka.common.pojo.SearchResult;

public interface SearchService {

	SearchResult search(String queryString,int page);
}
