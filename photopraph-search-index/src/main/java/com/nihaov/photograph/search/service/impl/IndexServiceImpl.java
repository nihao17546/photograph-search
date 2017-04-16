package com.nihaov.photograph.search.service.impl;

import com.nihaov.photograph.search.dao.ISearchDAO;
import com.nihaov.photograph.search.model.dto.ImageDTO;
import com.nihaov.photograph.search.service.IIndexService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <fieldType name="text_ik" class="solr.TextField">
 <analyzer class="org.wltea.analyzer.lucene.IKAnalyzer"/>
 </fieldType>
 <field name="image_title" type="text_ik" indexed="true" stored="true"/>
 <field name="image_path" type="string" indexed="false" stored="true"/>
 <field name="image_uid" type="long" indexed="false" stored="true"/>
 <field name="image_tag"  type="string" indexed="true" stored="true" required="false" multiValued="true"/>
 <field name="image_keywords" type="text_ik" indexed="true" stored="false" multiValued="true"/>
 <copyField source="image_title" dest="image_keywords"/>
 <copyField source="image_tag" dest="image_keywords"/>
 * Created by nihao on 17/4/15.
 */
@Service
public class IndexServiceImpl implements IIndexService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private ISearchDAO searchDAO;
    private SolrServer solrServer;
    @Value("#{configProperties['solrUrl']}")
    private String solrUrl;

    @PostConstruct
    public void init(){
        solrServer = new HttpSolrServer(solrUrl);
    }

    @Override
    public void index(long first, long last) {
        if(first>last){
            throw new RuntimeException("first must smaller than last");
        }
        List<ImageDTO> list = searchDAO.selectNeedIndex(first,last);
        for(ImageDTO imageDTO:list){
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id",imageDTO.getId());
            document.addField("image_title",imageDTO.getTitle());
            document.addField("image_path",imageDTO.getPath());
            document.addField("image_uid",imageDTO.getUid());
            for(String tag:imageDTO.getTags()){
                document.addField("image_tag",tag);
            }
            try {
                solrServer.add(document);
            } catch (Exception e) {
                logger.error("solr add ERROR,id is {}",imageDTO.getId(),e);
            }
        }
        try {
            solrServer.commit();
        } catch (Exception e) {
            logger.error("solr commit ERROR,first is "+first+",last is "+last,e);
        }
    }

    @Override
    public void delete(long first, long last) {
        if(first>last){
            throw new RuntimeException("first must smaller than last");
        }
        List<String> list = new ArrayList<>();
        for(Long i=first;i<=last;i++){
            list.add(i.toString());
        }
        try {
            solrServer.deleteById(list);
            solrServer.commit();
        } catch (Exception e) {
            logger.error("solr delete ERROR,first is "+first+",last is "+last,e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            solrServer.deleteByQuery("*:*");
            solrServer.commit();
        } catch (Exception e) {
            logger.error("solr deleteAll ERROR",e);
        }
    }

    @Override
    public void index(Long id) {
        ImageDTO imageDTO = searchDAO.selectNeedIndexById(id);
        if(imageDTO!=null){
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id",imageDTO.getId());
            document.addField("image_path",imageDTO.getPath());
            document.addField("image_title",imageDTO.getTitle());
            document.addField("image_uid",imageDTO.getUid());
            for(String tag:imageDTO.getTags()){
                document.addField("image_tag",tag);
            }
            try {
                solrServer.add(document);
                solrServer.commit();
            } catch (Exception e) {
                logger.error("solr index single ERROR,id is {}",imageDTO.getId(),e);
            }
        }
    }
}
