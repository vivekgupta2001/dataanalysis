package com.viveksoft.dataanalysis.service;

public interface PropertyService {

	Double getPriceDiff(String fromPropertyType, String toPropertyType);

	Double getAveragePrice(String postcode);

}
