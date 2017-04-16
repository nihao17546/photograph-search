package com.nihaov.photograph.search.service.impl;

import com.google.common.base.Strings;
import com.nihaov.photograph.search.model.result.SearchResult;
import com.nihaov.photograph.search.model.vo.ImageVO;
import com.nihaov.photograph.search.service.ISearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nihao on 17/4/15.
 */
@Service
public class SearchServiceImpl implements ISearchService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private SolrServer solrServer;
    @Value("#{configProperties['solrUrl']}")
    private String solrUrl;
    @Value("#{configProperties['defaultQuery']}")
    private String defaultQuery;

    @PostConstruct
    public void init(){
        solrServer = new HttpSolrServer(solrUrl);
    }

    @Override
    public SearchResult search(String keyword,int page,int rows) {
        return search(defaultQuery,keyword,page,rows);
    }

    @Override
    public SearchResult search(String name, String keyword,int page,int rows) {
        SearchResult result = new SearchResult();
        if(!Strings.isNullOrEmpty(keyword)){
            SolrQuery query = new SolrQuery();
            query.set("df", name);
            query.setQuery(keyword);
            query.setStart((page - 1) * rows);
            query.setRows(rows);
            QueryResponse response = null;
            try {
                response = solrServer.query(query);
            } catch (SolrServerException e) {
                logger.error("solr search ERROR",e);
            }
            if(response!=null){
                SolrDocumentList solrDocumentList = response.getResults();
                long recordCount = solrDocumentList.getNumFound();
                List<ImageVO> data = new ArrayList(new Long(recordCount).intValue());
                for (SolrDocument solrDocument : solrDocumentList) {
                    ImageVO imageVO = new ImageVO(solrDocument);
                    data.add(imageVO);
                }
                result.setData(data);
                result.setRecordCount(recordCount);
                long pageCount = recordCount / rows;
                if (recordCount % rows > 0) {
                    pageCount++;
                }
                result.setPageCount(pageCount);
                result.setCurPage(page);
            }
        }
        return result;
    }
}
