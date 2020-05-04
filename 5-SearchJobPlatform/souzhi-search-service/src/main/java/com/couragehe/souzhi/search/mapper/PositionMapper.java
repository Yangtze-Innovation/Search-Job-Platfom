package com.couragehe.souzhi.search.mapper;

import com.couragehe.souzhi.bean.Position;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @PackageName:com.couragehe.souzhi.crawler.mapper
 * @ClassName:JobInfoMapper
 * @Description:
 * @Autor:CourageHe
 * @Date: 2020/4/6 23:25
 */
public interface PositionMapper extends Mapper<Position> {

    List<Position> selectPositionListASLimit(@Param("from")int from, @Param("size")int size);
}
