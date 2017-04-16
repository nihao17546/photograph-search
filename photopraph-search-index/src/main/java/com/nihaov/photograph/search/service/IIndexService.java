package com.nihaov.photograph.search.service;

/**
 * Created by nihao on 17/4/15.
 */
public interface IIndexService {
    void index(long first,long last);
    void delete(long first,long last);
    void deleteAll();
    void index(Long id);
}
