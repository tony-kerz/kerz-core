package com.kerz.geo;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.kerz.geo.domain.Address;
import com.kerz.geo.domain.Building;
import com.kerz.geo.domain.GeoPoint;
import com.kerz.geo.domain.UnitType;

public class GeoHelper
{
	private Geocoder geocoder;
	
	private Logger log = LoggerFactory.getLogger(GeoHelper.class);
	
	public Address geocode(Address address)
	{
		Building building = address.getBuilding();
		UnitType unitType = address.getUnitType();
		String unit = address.getUnit();
		StringBuilder sb = new StringBuilder();
		sb.append(building.getHouse()).append(" ").append(building.getStreet());
		if ((unitType != null) && StringUtils.hasText(unit))
		{
			sb.append(", ").append(unitType.toString()).append(" ").append(unit);
		}
		sb.append(", ").append(building.getCity()).append(", ").append(building.getStateCode()).append(" ")
		    .append(building.getPostalCode());
		String addrString = sb.toString();
		
		GeocoderResponse gr = geocoder.geocode(addrString);
		
		building.setCity(gr.getCity());
		building.setCountryCode(gr.getCountryCode());
		building.setCountyCode(gr.getCountyCode());
		building.setGeoPoint(new GeoPoint(gr.getLatitude(), gr.getLongitude()));
		building.setHash(gr.getHash());
		if (StringUtils.hasText(gr.getHouse()))
		{
			building.setHouse(gr.getHouse());
		}
		building.setPostalCode(gr.getPostalCode());
		building.setStateCode(gr.getStateCode());
		if (StringUtils.hasText(gr.getStreet()))
		{
			building.setStreet(gr.getStreet());
		}
		// what will yahoo return for unit type strings...?
		if (StringUtils.hasText(gr.getUnitType()))
		{
			try
			{
				address.setUnitType(UnitType.valueOf(gr.getUnit()));
				address.setUnit(gr.getUnit());
			}
			catch (IllegalArgumentException iae)
			{
				// log and move on for now...
				log.error(MessageFormat.format("unexpected unit type={0}", gr.getUnitType()));
			}
		}
		return address;
	}
	
	public void setGeocoder(Geocoder geocoder)
	{
		this.geocoder = geocoder;
	}
}
