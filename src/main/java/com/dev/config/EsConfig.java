package com.dev.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsConfig {

    @Value("${esclient.elasticsearch.hostlist}")
    private String hostList;


    @Bean
    public RestHighLevelClient restHighLevelClient(){
        // 读取hostList地址信息
        String[] hostports = hostList.split(",");
        // 创建HttpHost数组
        HttpHost[] httpHosts = new HttpHost[hostports.length];
        String hostport;
        for(int i=0;i<hostports.length;i++){
            hostport = hostports[i];
            httpHosts[i] = new HttpHost(hostport.split(":")[0],Integer.valueOf(hostport.split(":")[1]),"http");
        }
        // 创建RestHighLevelClient客户端
        return new RestHighLevelClient(RestClient.builder(httpHosts));
    }

    @Bean
    public RestClient restClient() {
        // 读取hostList地址信息
        String[] hostports = hostList.split(",");
        // 创建HttpHost数组
        HttpHost[] httpHosts = new HttpHost[hostports.length];
        String hostport;
        for(int i=0;i<hostports.length;i++){
            hostport = hostports[i];
            httpHosts[i] = new HttpHost(hostport.split(":")[0],Integer.valueOf(hostport.split(":")[1]),"http");
        }
        // 创建RestClient客户端
        return RestClient.builder(httpHosts).build();
    }

}
