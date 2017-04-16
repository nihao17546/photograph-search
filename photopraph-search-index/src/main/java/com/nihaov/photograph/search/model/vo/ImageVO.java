package com.nihaov.photograph.search.model.vo;

import com.nihaov.photograph.search.model.constant.BaseConstant;
import org.apache.solr.common.SolrDocument;

import java.util.List;

/**
 * Created by nihao on 17/4/15.
 */
public class ImageVO {
    private Long id;
    private String title;
    private String path;
    private Long uid;
    private List<String> tags;

    public ImageVO(SolrDocument document) {
        Object id = document.get("id");
        Object title = document.get("image_title");
        Object path = document.get("image_path");
        Object uid = document.get("image_uid");
        Object tags = document.get("image_tag");
        if(id!=null){
            this.id = Long.parseLong(id.toString());
        }
        if(title!=null){
            this.title = (String) title;
        }
        if(path!=null){
            this.path = BaseConstant.compressPicPrefix + (String) path;
        }
        if(uid!=null){
            this.uid = (long) uid;
        }
        if(tags!=null){
            this.tags = (List<String>) tags;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
