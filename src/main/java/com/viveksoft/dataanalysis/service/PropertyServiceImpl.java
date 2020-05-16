package com.viveksoft.dataanalysis.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.viveksoft.dataanalysis.exception.ServiceException;
import com.viveksoft.dataanalysis.model.ListedProperty;
import com.viveksoft.dataanalysis.repository.PropertyRepository;

@Service
public class PropertyServiceImpl implements PropertyService {
	
	private PropertyRepository propertyRepository;

	public PropertyServiceImpl(PropertyRepository propertyRepository) {
		this.propertyRepository = propertyRepository;
	}

	@Override
	public Double getPriceDiff(String fromPropertyType, String toPropertyType) {
		List<ListedProperty> properties = propertyRepository.getListedProperties();
		Map<String, Double> propertyTypePriceMap = properties.parallelStream()
			.filter(p -> (p.getPropertyType().equals(fromPropertyType)|| p.getPropertyType().equals(toPropertyType)))
			.collect(Collectors.groupingBy(ListedProperty::getPropertyType, Collectors.averagingDouble(ListedProperty::getPrice)));
		return propertyTypePriceMap.get(fromPropertyType) - propertyTypePriceMap.get(toPropertyType);
	}

	@Override
	public Double getAveragePrice(String postcode) {
		List<ListedProperty> properties = propertyRepository.getListedProperties();
		return properties.parallelStream().filter(p -> p.getAddress().getPostcode().contains(postcode)).mapToDouble(ListedProperty::getPrice).average()
				.orElseThrow(() -> new ServiceException("No records found"));
	}

}
