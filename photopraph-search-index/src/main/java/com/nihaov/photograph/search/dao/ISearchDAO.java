package com.nihaov.photograph.search.dao;

import com.nihaov.photograph.search.model.dto.ImageDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by nihao on 17/4/15.
 */
public interface ISearchDAO {
    List<ImageDTO> selectNeedIndex(@Param("first") Long first,@Param("last") Long last);
    ImageDTO selectNeedIndexById(@Param("id") Long id);
}
