package com.viveksoft.dataanalysis.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.viveksoft.dataanalysis.model.ListedProperty;
import com.viveksoft.dataanalysis.repository.PropertyRepository;

@RunWith(MockitoJUnitRunner.class)
public class PropertyServiceImplTest {
	
	@Mock
	PropertyRepository propertyRepository;
	
	private PropertyService propertyService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		propertyService = new PropertyServiceImpl(propertyRepository);
	}

	@Test
	public void getPriceDiff() {
		Mockito.when(propertyRepository.getListedProperties()).thenReturn(getAllProperties());
		Double difference = propertyService.getPriceDiff("Detached", "Flat");
		Mockito.verify(propertyRepository).getListedProperties();
		assertEquals(202500, difference);
	}

	private List<ListedProperty> getAllProperties() {
		List<ListedProperty> properties = new ArrayList<>();
		ListedProperty p1 = getListedProperty("Flat", 210000L);
		ListedProperty p2 = getListedProperty("Flat", 230000L);
		ListedProperty p3 = getListedProperty("Flat", 210000L);
		ListedProperty p4 = getListedProperty("Flat", 220000L);
		
		ListedProperty p5 = getListedProperty("Detached", 420000L);
		ListedProperty p6 = getListedProperty("Detached", 420000L);
		ListedProperty p7 = getListedProperty("Detached", 420000L);
		
		ListedProperty p8 = getListedProperty("Mansion", 150000L);
		
		properties.add(p1);
		properties.add(p2);
		properties.add(p3);
		properties.add(p4);
		properties.add(p5);
		properties.add(p6);
		properties.add(p7);
		properties.add(p8);
		
		return properties;
	}

	private ListedProperty getListedProperty(String propertyType, Long price) {
		ListedProperty p1 = new ListedProperty();
		p1.setPropertyType(propertyType);
		p1.setPrice(price);
		return p1;
	}

}
