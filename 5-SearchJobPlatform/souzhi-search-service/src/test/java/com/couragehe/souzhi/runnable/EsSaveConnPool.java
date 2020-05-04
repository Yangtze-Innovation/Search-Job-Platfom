package com.couragehe.souzhi.runnable;

import com.couragehe.souzhi.bean.Position;
import com.couragehe.souzhi.search.mapper.PositionMapper;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @PackageName:com.couragehe.souzhi.runnable
 * @ClassName:EsSaveConnPool
 * @Description:
 * @Autor:CourageHe
 * @Date: 2020/5/3 20:48
 */
@Component
public class EsSaveConnPool {
    @Autowired
    JestClient jestClient;
    @Autowired
    PositionMapper positionMapper;
    public static final String INDEX_NAME="souzhi";
    public static final String TYPE_NAME="position";


    public void saveAll() throws Exception {
        //声明起点，从1开始
        int from = 120000;
        //声明查询的数据条数
        int pageSize = 0;
        System.out.println("查询起点："+from);
        do {
            //从数据库种查询数据
            List<Position> positions = positionMapper.selectPositionListASLimit(from,2000);


            //把查询到的数据封装为jobInfoField
            for(Position position : positions) {
                Index put = new Index.Builder(position)
                        .index(INDEX_NAME)
                        .type(TYPE_NAME)
                        .id(position.getId())
                        .build();
                jestClient.execute(put);
            }
            pageSize = positions.size();
            //起点向后递增
            from+= pageSize;
            System.out.println("查询终点："+from);

        }while(pageSize == 2000&&from<=240000);
    }

}
