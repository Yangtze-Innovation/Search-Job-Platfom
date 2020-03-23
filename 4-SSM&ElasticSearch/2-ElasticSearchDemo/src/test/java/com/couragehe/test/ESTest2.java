package com.couragehe.test;

import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.search.MatchQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ESTest2 {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    //满足某个条件（聚合）的文档集合
    @Test
    public void Test() {

    }

    @Test
    public void Test2() {

    }

    @Test
    public void Test3() {

    }

    @Test
    public void Test4() {

    }


}
