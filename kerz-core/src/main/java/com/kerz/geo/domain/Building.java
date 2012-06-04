package com.kerz.geo.domain;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.kerz.text.FormattedException;

public class Building implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Size(min = 2, max = 50)
	private String city;
	private String countryCode;
	private String countyCode;
	@Embedded
	private GeoPoint geoPoint = new GeoPoint();
	private String hash;
	private String house;
	@NotNull
	@Pattern(regexp = "[0-9]{5}(-[0-9]{4})?")
	// assuming US for now
	private String postalCode;
	@NotNull
	@Size(min = 2, max = 2)
	private String stateCode;
	@NotNull
	@Size(min = 2, max = 50)
	private String street;
	
	protected Building()
	{
	}
	
	public String getHouseAndStreet()
	{
		String result = null;
		if (StringUtils.hasText(house) && StringUtils.hasText(street))
		{
			result = house + " " + street;
		}
		else
		{
			result = "";
		}
		return result;
	}
	
	public void setHouseAndStreet(String houseAndStreet)
	{
		Assert.hasLength(houseAndStreet);
		String[] tokens = houseAndStreet.split(" ");
		if (tokens.length < 2)
		{
			throw new FormattedException("houseAndStreet=[{0}] requires at least two tokens", houseAndStreet);
		}
		this.house = tokens[0].trim();
		this.street = houseAndStreet.substring(house.length()).trim();
	}
	
	public Building(String house, String street, String city, String stateCode, String postalCode)
	{
		super();
		this.city = city;
		this.house = house;
		this.postalCode = postalCode;
		this.stateCode = stateCode;
		this.street = street;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public String getCountryCode()
	{
		return countryCode;
	}
	
	public String getCountyCode()
	{
		return countyCode;
	}
	
	public GeoPoint getGeoPoint()
	{
		return geoPoint;
	}
	
	public String getHash()
	{
		return hash;
	}
	
	public String getHouse()
	{
		return house;
	}
	
	public String getPostalCode()
	{
		return postalCode;
	}
	
	public String getStateCode()
	{
		return stateCode;
	}
	
	public String getStreet()
	{
		return street;
	}
	
	public void setCity(String city)
	{
		this.city = city;
	}
	
	public void setCountryCode(String countryCode)
	{
		this.countryCode = countryCode;
	}
	
	public void setCountyCode(String countyCode)
	{
		this.countyCode = countyCode;
	}
	
	public void setGeoPoint(GeoPoint geoPoint)
	{
		this.geoPoint = geoPoint;
	}
	
	public void setHash(String hash)
	{
		this.hash = hash;
	}
	
	public void setHouse(String house)
	{
		this.house = house;
	}
	
	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}
	
	public void setStateCode(String stateCode)
	{
		this.stateCode = stateCode;
	}
	
	public void setStreet(String street)
	{
		this.street = street;
	}
	
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
