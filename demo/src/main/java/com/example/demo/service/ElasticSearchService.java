package com.example.demo.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entity.EmployeeEntity;

public interface ElasticSearchService {

	public Object getDetails(String authorizationHeader);

	public Object getSearchDetails(String authorizationHeader,@RequestBody Object ob1);

	public Object getAllUser(String authorizationHeader, Object ob1);

	public List<EmployeeEntity> getAllEmployees(String field);

	public List<EmployeeEntity> getEmp();

}
