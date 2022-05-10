package com.example.demo.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.example.demo.config.ElasticConfig;
import com.example.demo.entity.EmployeeEntity;
import com.example.demo.service.ElasticSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ElasticSearchImpl implements ElasticSearchService {

	@Autowired
	ElasticConfig config;

	@Override
	public Object getDetails(String authorizationHeader) {
		ObjectMapper objMap = new ObjectMapper();
		RestTemplate restTemplate = new RestTemplate();
		String getApi = "https://search-demotest-f4dnvk36iiqficc5iynskiyxqq.us-east-1.es.amazonaws.com";
		HttpHeaders headers = new HttpHeaders();
		String auth1 = (String) authorizationHeader.subSequence(6, authorizationHeader.length());
		headers.setBasicAuth(auth1);
		HttpEntity<?> httpEntity = new HttpEntity<Object>(headers);
		System.out.println(getApi);
		try {
			ResponseEntity<String> taskResp = restTemplate.exchange(getApi, HttpMethod.GET, httpEntity, String.class);

			System.out.println("elastic search details response: " + taskResp.getBody());
			Object ob1 = objMap.readValue(taskResp.getBody().toString(), Object.class);
			System.out.println("------------------------");
			System.out.println(ob1);
			System.out.println("-------------------------");
			return ob1;
		}

		catch (Exception e) {
			System.out.println("Exception occured while hitting AWS Api: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	@Override
	public Object getSearchDetails(String authorizationHeader, @RequestBody Object ob1) {
		ObjectMapper objMap = new ObjectMapper();
		RestTemplate restTemplate = new RestTemplate();
		String getApi = "https://search-search1-gatwritnnxuazyjclzo57ouswm.us-east-1.es.amazonaws.com/user/_search";
		System.out.println(authorizationHeader);
		System.out.println("--------------------" + authorizationHeader.subSequence(6, authorizationHeader.length()));
		HttpHeaders headers = new HttpHeaders();
		// headers.setBasicAuth("Nithish","nithishP@23");
		String auth1 = (String) authorizationHeader.subSequence(6, authorizationHeader.length());
		headers.setBasicAuth(auth1);

		HttpEntity<?> httpEntity = new HttpEntity<Object>(ob1, headers);
		System.out.println("httpentity-----" + httpEntity);
		try {
			ResponseEntity<String> taskResp = restTemplate.exchange(getApi, HttpMethod.POST, httpEntity, String.class);
			// System.out.println("elastic search details response: " + taskResp.getBody());
			Object ob2 = objMap.readValue(taskResp.getBody().toString(), Object.class);
			System.out.println("------------------------");
			System.out.println(ob1);
			System.out.println("-------------------------");
			return ob2;
		}

		catch (Exception e) {
			System.out.println("Exception occured while hitting AWS Api: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	@Override
	public Object getAllUser(String authorizationHeader, Object ob1) {

		return null;
	}

	@Override
	public List<EmployeeEntity> getAllEmployees(String field) {

		try {
			List<EmployeeEntity> users = new ArrayList<>();
			SearchRequest searchRequest = new SearchRequest("employee");
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(QueryBuilders.matchQuery("name", field));
			searchRequest.source(searchSourceBuilder);
			searchSourceBuilder.size(5);
			SearchResponse searchResponse = config.client().search(searchRequest, RequestOptions.DEFAULT);
			for (SearchHit searchHit : searchResponse.getHits().getHits()) {
				EmployeeEntity user = new ObjectMapper().readValue(searchHit.getSourceAsString(), EmployeeEntity.class);
				users.add(user);
			}
			return users;
		} catch (Exception e) {
			System.out.println("Exception occured :" + e.getMessage());
			return new ArrayList<>();
		}
	}

	@Override
	public List<EmployeeEntity> getEmp() {
		try {
			List<EmployeeEntity> users = new ArrayList<>();
			SearchRequest searchRequest = new SearchRequest("employee");
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(QueryBuilders.matchAllQuery());
			searchRequest.source(searchSourceBuilder);
			searchSourceBuilder.size(5);
			SearchResponse searchResponse = config.client().search(searchRequest, RequestOptions.DEFAULT);
			for (SearchHit searchHit : searchResponse.getHits().getHits()) {
				EmployeeEntity user = new ObjectMapper().readValue(searchHit.getSourceAsString(), EmployeeEntity.class);
				users.add(user);
			}
			return users;
		} catch (Exception e) {
			System.out.println("Exception occured :" + e.getMessage());
			return new ArrayList<>();
		}

	}
}
