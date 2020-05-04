package com.couragehe.souzhi.search.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.couragehe.souzhi.search.mapper.PositionMapper;
import com.couragehe.souzhi.service.SearchService;
import io.searchbox.client.JestClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @PackageName:com.couragehe.souzhi.search.service
 * @ClassName:SearchServiceImpl
 * @Description:
 * @Autor:CourageHe
 * @Date: 2020/3/23 13:44
 */
@Service
public class SearchServiceImpl implements SearchService {
    public static final String INDEX_NAME="souzhi";
    public static final String TYPE_NAME="position";
    @Autowired
    JestClient jestClient;

    @Autowired
    private PositionMapper positionMapper;


    @Override
    public String index() {
//        Index index= new Index.Builder()
//                .index(INDEX_NAME)
//                .type(TYPE_NAME)
//                .id()
//                .build();
//        try {
//            jestClient.execute(index);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return "index";
//        ModelMapper
    }
}
