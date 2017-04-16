package com;

import com.nihaov.photograph.search.dao.ISearchDAO;
import com.nihaov.photograph.search.model.dto.ImageDTO;
import com.nihaov.photograph.search.model.result.SearchResult;
import com.nihaov.photograph.search.service.IIndexService;
import com.nihaov.photograph.search.service.ISearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by nihao on 17/4/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class test {
    @Resource
    private ISearchDAO searchDAO;
    @Resource
    private IIndexService indexService;
    @Resource
    private ISearchService searchService;

    @Test
    public void test01(){
        List<ImageDTO> list = searchDAO.selectNeedIndex(1L,100L);
        System.out.println("---------");
    }
    @Test
    public void test02(){
        indexService.index(18001L,19000L);
        indexService.index(19001L,20000L);
        indexService.index(20001L,21000L);
    }
    @Test
    public void test03(){
        SearchResult searchResult = searchService.search("image_tag","猫",1,20);
        System.out.println("---------");
    }
    @Test
    public void test00(){
        System.out.println(System.currentTimeMillis()+"------------------");
    }
}
