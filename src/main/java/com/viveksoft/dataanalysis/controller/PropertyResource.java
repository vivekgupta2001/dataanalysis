package com.viveksoft.dataanalysis.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viveksoft.dataanalysis.model.Properties;
import com.viveksoft.dataanalysis.service.PropertyService;
import com.viveksoft.dataanalysis.model.Address;
import com.viveksoft.dataanalysis.model.Building;
import com.viveksoft.dataanalysis.model.ListedProperty;

@RestController
@RequestMapping(path="/properties")
public class PropertyResource {
	
	private PropertyService propertyService;
	
	public PropertyResource(PropertyService propertyService) {
		this.propertyService = propertyService;
	}
	
	@GetMapping
	@RequestMapping(path = "")
	public Properties getListedProperties() throws IOException {
		BufferedInputStream bf = null;
		List<ListedProperty> listedProperties = new ArrayList<>();
		try {
			 bf = new BufferedInputStream(new FileInputStream(ResourceUtils.getFile("classpath:data/properties.csv")));
			 Scanner scanner = new Scanner(bf);
			 scanner.nextLine();
			 while(scanner.hasNextLine()) {
				 String csvData[] = scanner.nextLine().split(",");
				 ListedProperty property = new ListedProperty();
				 property.setId(Long.valueOf(csvData[0]));
				 property.setPrice(Long.valueOf(csvData[1]));
				 Building building = new Building();
				 building.setBedrooms(!StringUtils.isEmpty(csvData[2]) ? Integer.valueOf(csvData[2]) : null);
				 building.setBathrooms(!StringUtils.isEmpty(csvData[3]) ? Integer.valueOf(csvData[2]) : null);
				 property.setBuilding(building);
				 Address address = new Address();
				 address.setHouseNumber(csvData[4]);
				 address.setAddress(csvData[5]);
				 address.setRegion(csvData[6]);
				 address.setPostcode(csvData[7]);
				 property.setAddress(address);
				 property.setPropertyType(csvData[8]);
				 listedProperties.add(property);
			 }
			 scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			bf.close();
		}
		Properties properties = new Properties();
		properties.setListedProperties(listedProperties);
		return properties;
	}
	
	@GetMapping(path = "/price/diff/{fromType}/{toType}")
	public Double getPriceDiff(@PathVariable("fromType") String fromType, @PathVariable("toType") String toType) {
		return propertyService.getPriceDiff(fromType, toType);
	}
	
	@GetMapping(path = "/price/average/{postcode}")
	public Double getAveragePrice(@PathVariable("postcode") String postcode) {
		return propertyService.getAveragePrice(postcode);
	}
}
