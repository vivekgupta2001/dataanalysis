package com.viveksoft.dataanalysis.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.viveksoft.dataanalysis.model.ListedProperty;
import com.viveksoft.dataanalysis.model.Properties;

@Repository
public class PropertyRepository {
	
	@Autowired
	private RestTemplate restTemplate;

	public List<ListedProperty> getListedProperties() {
		ResponseEntity<Properties> body = restTemplate.getForEntity("http://localhost:8080/properties", Properties.class);
		return body.getBody().getListedProperties();
	}

}
