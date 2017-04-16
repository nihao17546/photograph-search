package com.nihaov.photograph.search.web;

import com.alibaba.fastjson.JSON;
import com.nihaov.photograph.search.model.result.SearchResult;
import com.nihaov.photograph.search.service.IIndexService;
import com.nihaov.photograph.search.service.ISearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by nihao on 17/4/15.
 */
@Controller
public class SearchController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IIndexService indexService;
    @Resource
    private ISearchService searchService;

    @RequestMapping("/query/image/tag/{page}/{rows}/{keyword}/{param}")
    @ResponseBody
    public String queryImageByTag(@PathVariable("page") Integer page,
                             @PathVariable("rows") Integer rows,
                             @PathVariable("keyword") String keyword){
        SearchResult searchResult = searchService.search("image_tag",keyword,page,rows);
        return JSON.toJSONString(searchResult);
    }
    @RequestMapping("/query/image/{page}/{rows}/{keyword}")
    @ResponseBody
    public String queryImage(@PathVariable("page") Integer page,
                                  @PathVariable("rows") Integer rows,
                                  @PathVariable("keyword") String keyword){
        SearchResult searchResult = searchService.search(keyword,page,rows);
        return JSON.toJSONString(searchResult);
    }

}
