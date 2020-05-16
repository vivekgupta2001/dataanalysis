package com.viveksoft.dataanalysis.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.viveksoft.dataanalysis.model.Properties;
import com.viveksoft.dataanalysis.service.PropertyService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class PropertyResourceTest {
	
	@Autowired
	private PropertyResource propertyResource;
	
	@MockBean
	PropertyService propertyService;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void checkContextLoaded() {
		assertNotNull(propertyResource);
	}
	
	@Test
	public void checkDataLoaded() {
		ResponseEntity<Properties> propertiesBody = restTemplate.getForEntity("/properties", Properties.class);
		Properties properties = propertiesBody.getBody();
		assertNotNull(properties);
		assertEquals(24, properties.getListedProperties().size());
	}
	
	@Test
	public void checkAveragePriceDifferenceBetweenPropertyType() {
		given(propertyService.getPriceDiff("Detached", "Terraced")).willReturn(100000D);
		ResponseEntity<Double> priceDiffBody = restTemplate.getForEntity("/properties/price/diff/Detached/Terraced", Double.class);
		System.out.println(priceDiffBody.getBody());
		assertEquals(100000, priceDiffBody.getBody());
	}

}
