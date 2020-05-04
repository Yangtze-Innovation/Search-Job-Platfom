package com.couragehe.souzhi.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.couragehe.souzhi.base.SearchResponse;
import com.couragehe.souzhi.bean.Position;
import com.couragehe.souzhi.bean.PositionSearchParam;
import com.couragehe.souzhi.search.mapper.PositionMapper;
import com.couragehe.souzhi.service.PositionElasticService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @PackageName:com.couragehe.souzhi.search.service
 * @ClassName:PositionElasticService
 * @Description:
 * @Autor:CourageHe
 * @Date: 2020/4/24 23:07
 */
@Service
public class PositionElasticServiceImpl implements PositionElasticService {
    public static final String INDEX_NAME="souzhi";
    public static final String TYPE_NAME="position";
    @Autowired
    JestClient jestClient;
    @Autowired
    PositionMapper positionMapper;

    @Override
    public boolean saveAll() {
        //声明起点，从1开始
        int from = 1;
        //声明查询的数据条数
        int pageSize = 0;

        do {
            //从数据库种查询数据
            List<Position> positions = positionMapper.selectPositionListASLimit(from, 2000);


            //把查询到的数据封装为jobInfoField

            try {
                for (Position position : positions) {
                    Index put = new Index.Builder(position)
                            .index(INDEX_NAME)
                            .type(TYPE_NAME)
                            .id(position.getId())
                            .build();
                    jestClient.execute(put);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            pageSize = positions.size();
            //起点向后递增
            from += pageSize;
            System.out.println("查询起点：" + from);
        } while (pageSize == 2000);
        return false;
    }

    @Override
    public SearchResponse searchPositionFromEs(PositionSearchParam positionSearchParam) {
        String searchDsl = getSearchDsl(positionSearchParam);
        SearchResponse searchResponse = new SearchResponse();
        //用api执行复杂查询
        List<Position> positionList = new ArrayList<>();
        Search search = new Search.Builder(searchDsl).addIndex(INDEX_NAME).addType(TYPE_NAME).build();
        SearchResult result = null;
        try {
                result = jestClient.execute(search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long total = result.getTotal();
        searchResponse.setTotal(total);
        List<SearchResult.Hit<Position,Void>>hits = result.getHits(Position.class);
        for (SearchResult.Hit<Position,Void>hit : hits){
            Position position = hit.source;
            //高亮部分显示替换
            Map<String,List<String>> highlight = hit.highlight;
            String positionName = highlight.get("positionName").get(0);
            position.setPositionName(positionName);
            positionList.add(position);
        }
        searchResponse.setData(positionList);
        searchResponse.setPageSize(positionSearchParam.getSize());
        return searchResponse;
    }


    /**
     * 通过jest客户端提供的builder工具，用以组合复杂的查询JSON
     * @param positionSearchParam
     * @return
     */
    private String getSearchDsl(PositionSearchParam positionSearchParam){
        //jest的dsl工具
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //bool
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //filter
        if(StringUtils.isNotBlank(positionSearchParam.getEducationRequire())){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("educationRequire",positionSearchParam.getEducationRequire());
            boolQueryBuilder.filter(termQueryBuilder);
        }
        if(StringUtils.isNotBlank(positionSearchParam.getOriginWebsite())){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("originWebsite",positionSearchParam.getOriginWebsite());
            boolQueryBuilder.filter(termQueryBuilder);
        }
        if(StringUtils.isNotBlank(positionSearchParam.getWorkCity())){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("workCity",positionSearchParam.getWorkCity());
            boolQueryBuilder.filter(termQueryBuilder);
        }
        if(StringUtils.isNotBlank(positionSearchParam.getWorkNature())){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("workNature",positionSearchParam.getWorkNature());
            boolQueryBuilder.filter(termQueryBuilder);
        }
        //must
        if(StringUtils.isNotBlank(positionSearchParam.getKeyword())){
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("positionName",positionSearchParam.getKeyword());
            boolQueryBuilder.must(matchQueryBuilder);
        }
        //query
        searchSourceBuilder.query(boolQueryBuilder);

        //highlight
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<b>");
        highlightBuilder.postTags("</b>");
        highlightBuilder.field("positionName");
        searchSourceBuilder.highlight(highlightBuilder);

        //from
        searchSourceBuilder.from(positionSearchParam.getFrom());
        //size
        searchSourceBuilder.size(positionSearchParam.getSize());
        //sort
        searchSourceBuilder.sort("id", SortOrder.DESC);

        String searchDsl = searchSourceBuilder.toString();
        System.out.println(searchDsl);
        return searchDsl;

    }
}
