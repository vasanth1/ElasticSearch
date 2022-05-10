package com.example.demo.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ElasticConfig {
	private static final String HOST = "search-demotest-f4dnvk36iiqficc5iynskiyxqq.us-east-1.es.amazonaws.com";
	private static final int PORT = 443; // tries 9200, 9300, 80
	private static final String PROTOCOL = "https";

	@Bean
	public RestHighLevelClient client(){   
	final CredentialsProvider credentialsProvider =new BasicCredentialsProvider();
	credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials("admin", "Admin@123"));
	RestClientBuilder builder =RestClient.builder(new HttpHost(HOST, PORT, "https")).setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
    System.out.println("Builder---------------------------"+builder);
	 RestHighLevelClient client = new RestHighLevelClient(builder);
	    return client;
	}
}