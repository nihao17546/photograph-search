package com.nihaov.photograph.search.service;

import com.nihaov.photograph.search.model.result.SearchResult;

/**
 * Created by nihao on 17/4/15.
 */
public interface ISearchService {
    SearchResult search(String keyword,int page,int rows);
    SearchResult search(String name,String keyword,int page,int rows);
}
