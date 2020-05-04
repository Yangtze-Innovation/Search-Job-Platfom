package com.couragehe.souzhi.service;

import com.couragehe.souzhi.base.SearchResponse;
import com.couragehe.souzhi.bean.PositionSearchParam;

/**
 * @PackageName:com.couragehe.souzhi.service
 * @ClassName:PositionElasticService
 * @Description:
 * @Autor:CourageHe
 * @Date: 2020/4/24 23:06
 */
public interface PositionElasticService {
    boolean saveAll();

    SearchResponse searchPositionFromEs(PositionSearchParam positionSearchParam);
}
