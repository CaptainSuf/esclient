package com.dev;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestIndex {

    @Autowired
    RestHighLevelClient client;

    @Test
    public void testCreateIndex()throws Exception{
        // 创建索引
        CreateIndexRequest request = new CreateIndexRequest("test_add1_index");
        request.settings(Settings.builder().put("number_of_shards",1).put("number_of_replicas",0));
        // 设置索引映射
        request.mapping("{\n" +
                "      \"dynamic\" : \"false\",\n" +
                "      \"properties\" : {\n" +
                "        \"age\" : {\n" +
                "          \"type\" : \"short\"\n" +
                "        },\n" +
                "        \"desc\" : {\n" +
                "          \"type\" : \"text\",\n" +
                "          \"analyzer\" : \"standard\"\n" +
                "        },\n" +
                "        \"job\" : {\n" +
                "          \"type\" : \"keyword\"\n" +
                "        },\n" +
                "        \"name\" : {\n" +
                "          \"type\" : \"text\"\n" +
                "        }\n" +
                "      }\n" +
                "    }", XContentType.JSON);
        // 操作客户端,执行创建
        CreateIndexResponse response=client.indices().create(request,RequestOptions.DEFAULT);
        //得到响应
        boolean acknowledged = response.isAcknowledged();
        System.out.println(acknowledged);
    }
    @Test
    public void testDeleteIndex()throws Exception{
        // 设置要删除的索引
        DeleteIndexRequest request = new DeleteIndexRequest("test_add1_index");
        // 操作客户端
        IndicesClient indices =client.indices();
        // 执行删除
        AcknowledgedResponse response = indices.delete(request,RequestOptions.DEFAULT);
        //得到响应
        boolean acknowledged = response.isAcknowledged();
        System.out.println(acknowledged);
    }

    @Test
    public void testAddDoc()throws Exception{
        // 组装文档数据
        Map<String,Object> map = new HashMap<>();
        map.put("name","二把手");
        map.put("age",50);
        map.put("job","mgr");
        map.put("desc","干辛苦活的");
        // 设置索引
        IndexRequest request = new IndexRequest("test_add_index");
        request.source(map);
        //通过client进行http的请求
        IndexResponse indexResponse = client.index(request,RequestOptions.DEFAULT);
        DocWriteResponse.Result result = indexResponse.getResult();
        System.out.println(result);
    }

    @Test
    public void testUpdateDoc()throws Exception{
        // 组装文档数据
        Map<String,Object> map = new HashMap<>();
        map.put("name","dalao");
        map.put("age",51);
        map.put("job","mgr");
        map.put("desc","干辛苦活的");
        // 设置索引
        UpdateRequest request = new UpdateRequest("test_add_index","0Xvjam8B-0JvHIwTV_VN");
        request.doc(map);
        //通过client进行http的请求
        UpdateResponse response = client.update(request,RequestOptions.DEFAULT);
        DocWriteResponse.Result result = response.getResult();
        System.out.println(result);
    }

    @Test
    public void testGetDoc()throws Exception{
        GetRequest request = new GetRequest("test_add_index","0Xvjam8B-0JvHIwTV_VN");
        GetResponse response = client.get(request,RequestOptions.DEFAULT);
        Map<String,Object> map = response.getSource();
        System.out.println(map);
    }
}
