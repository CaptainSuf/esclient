package com.dev;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSearch {

    @Autowired
    RestHighLevelClient client;

    @Test
    public void testSearchAll()throws Exception{
        // 创建请求对象
        SearchRequest request = new SearchRequest("test_add_index");
        // 搜索源构建对象
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // matchAllQuery查询全部
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        // 设置返回的字段
        sourceBuilder.fetchSource(new String[]{"name","age"},new String[]{});
        // 设置搜索源
        request.source(sourceBuilder);
        // 发起请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = response.getHits();
        if(hits.getTotalHits().value>0){
            for(SearchHit hit:hits.getHits()){
                System.out.println(hit.getSourceAsMap());
                //System.out.println(hit);
            }
        }
    }

    @Test
    public void testMatchQuery() throws Exception{
        // 创建请求对象
        SearchRequest request = new SearchRequest("test_add_index");
        // 搜索源构建对象
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // matchAllQuery查询全部
        sourceBuilder.query(QueryBuilders.matchQuery("name","二"));
        // 设置返回的字段
        sourceBuilder.fetchSource(new String[]{"name","age","job","desc"},new String[]{});
        // 设置搜索源
        request.source(sourceBuilder);
        // 发起请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = response.getHits();
        if(hits.getTotalHits().value>0){
            for(SearchHit hit:hits.getHits()){
                System.out.println(hit.getSourceAsMap());
            }
        }
    }

    @Test
    public void testSearchPage()throws Exception{
        // 创建请求对象
        SearchRequest request = new SearchRequest("test_add_index");
        // 搜索源构建对象
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 当前页
        int page = 1;
        // 每页返回数
        int size = 1;
        // 开始的下标
        int from = (page-1)*size;
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        // matchAllQuery查询全部
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        // 设置返回的字段
        sourceBuilder.fetchSource(new String[]{"name","age"},new String[]{});
        // 设置搜索源
        request.source(sourceBuilder);
        // 发起请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = response.getHits();
        if(hits.getTotalHits().value>0){
            for(SearchHit hit:hits.getHits()){
                System.out.println(hit.getSourceAsMap());
            }
        }
    }

    @Test
    public void testTermQuery() throws Exception{
        // 创建请求对象
        SearchRequest request = new SearchRequest("test_add_index");
        // 搜索源构建对象
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // termQuery查询
        sourceBuilder.query(QueryBuilders.termQuery("name","dalao"));
        // 设置返回的字段
        sourceBuilder.fetchSource(new String[]{"name","age","job","desc"},new String[]{});
        // 设置搜索源
        request.source(sourceBuilder);
        // 发起请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = response.getHits();
        if(hits.getTotalHits().value>0){
            for(SearchHit hit:hits.getHits()){
                System.out.println(hit.getSourceAsMap());
            }
        }
    }

    @Test
    public void testMultiMatchQuery()throws Exception{
        // 创建请求对象
        SearchRequest request = new SearchRequest("test_add_index");
        // 搜索源构建对象
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // matchAllQuery查询全部
        sourceBuilder.query(QueryBuilders.multiMatchQuery("二 苦","name","desc")
                .minimumShouldMatch("50%")
                .field("name",10));
        // 设置返回的字段
        sourceBuilder.fetchSource(new String[]{"name","age"},new String[]{});
        // 设置搜索源
        request.source(sourceBuilder);
        // 发起请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = response.getHits();
        if(hits.getTotalHits().value>0){
            for(SearchHit hit:hits.getHits()){
                System.out.println(hit.getSourceAsMap());
            }
        }
    }

    @Test
    public void testBoolQuery()throws Exception{
        // 创建请求对象
        SearchRequest request = new SearchRequest("test_add_index");
        // 搜索源构建对象
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // matchAllQuery查询全部
        // 定义boolQuery
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(QueryBuilders.termQuery("name","dalao"));
        boolBuilder.should(QueryBuilders.matchQuery("age","50"));
        sourceBuilder.query(boolBuilder);
        // 设置返回的字段
        sourceBuilder.fetchSource(new String[]{"name","age"},new String[]{});
        // 设置搜索源
        request.source(sourceBuilder);
        // 发起请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = response.getHits();
        if(hits.getTotalHits().value>0){
            for(SearchHit hit:hits.getHits()){
                System.out.println(hit.getSourceAsMap());
                //System.out.println(hit);
            }
        }
    }
}
