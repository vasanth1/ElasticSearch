package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.config.ElasticConfig;
import com.example.demo.entity.EmployeeEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.ElasticSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;




@RestController
@CrossOrigin
public class ElasticSearchApi {

	
	@Autowired
	ElasticSearchService elasticSearch;
	
	@Autowired ElasticConfig config;
	
	@CrossOrigin
	@GetMapping(value = "/getting")
	public Object gettingthethings(HttpServletRequest request) {
		final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		System.out.println(authorizationHeader);
		
		return (elasticSearch.getDetails(authorizationHeader));

	}
	
	@CrossOrigin
	@GetMapping(value = "/search")
	public Object searchthethings(HttpServletRequest request , @RequestBody Object ob1) {
		final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		//System.out.println(authorizationHeader);
		System.out.println(ob1);
		return (elasticSearch.getSearchDetails(authorizationHeader, ob1));

	}
	
	@CrossOrigin
	@GetMapping(value = "/findAllUsers")
	public Object searchUsers(HttpServletRequest request , @RequestBody Object ob1) {
		final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		//System.out.println(authorizationHeader);
		System.out.println(ob1);
		return (elasticSearch.getAllUser(authorizationHeader, ob1));

	}

	 @GetMapping("/all")
	    public List<UserEntity> readAll() throws IOException {
	        List<UserEntity> users = new ArrayList<>();
	        SearchRequest searchRequest = new SearchRequest("user-management");
	        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
	        searchRequest.source(searchSourceBuilder);
	        searchSourceBuilder.size(5);
	        SearchResponse searchResponse = config.client().search(searchRequest, RequestOptions.DEFAULT);
	        for(SearchHit searchHit : searchResponse.getHits().getHits()){
	        	UserEntity user = new ObjectMapper().readValue(searchHit.getSourceAsString(),UserEntity.class);
	            users.add(user);
	        }
	        return users;
	    }
	 
	 @GetMapping("/all/{field}")
	    public List<UserEntity> readUsername(@PathVariable final String field) throws IOException {
	        List<UserEntity> users = new ArrayList<>();
	        SearchRequest searchRequest = new SearchRequest("user-management");
	        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	        searchSourceBuilder.query(QueryBuilders.matchQuery("userName",field));
	        searchRequest.source(searchSourceBuilder);
	        searchSourceBuilder.size(5);
	        SearchResponse searchResponse = config.client().search(searchRequest, RequestOptions.DEFAULT);
	        for(SearchHit searchHit : searchResponse.getHits().getHits()){
	        	UserEntity user = new ObjectMapper().readValue(searchHit.getSourceAsString(),UserEntity.class);
	            users.add(user);
	        }
	        return users;
	    }
	 
	 @GetMapping("/allEmployee")
	    public List<EmployeeEntity> readEmployeeAll() throws IOException {
	       return elasticSearch.getEmp();
	    }
	 
	 @GetMapping("/allEmployee/{field}")
	    public List<EmployeeEntity> readEmployeename(@PathVariable final String field) throws IOException {
	      
		 return elasticSearch.getAllEmployees(field);
	    }
	 
	 
	
}
