package com.dev;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EsClient {

    public static RestHighLevelClient getRestHighLevelClient(){
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.99.100", 9200, "http")));
    }

    public static void main(String[] args) {
        try (RestHighLevelClient client = getRestHighLevelClient()){
            IndexRequest request = new IndexRequest("test_index","_doc");
            Map<String, Object> map = new HashMap<>();
            map.put("name","alic");
            map.put("age",20);
            map.put("sex","女");
            map.put("desc","高级ai");
            map.put("job","整合骑士");
            request.source(map);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            DocWriteResponse.Result result = response.getResult();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
