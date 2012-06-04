package com.kerz.geo;

import org.apache.commons.lang.builder.ToStringBuilder;

public class GeocoderResponse
{
  private String city;
  private String countryCode;
  private String countyCode;
  private String hash;
  private String house;
  private float latitude;
  private float longitude;
  private String postalCode;
  private String stateCode;
  private String street;
  private String unit;
  private String unitType;

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

  public String getHash()
  {
    return hash;
  }

  public String getHouse()
  {
    return house;
  }

  public float getLatitude()
  {
    return latitude;
  }

  public float getLongitude()
  {
    return longitude;
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

  public String getUnit()
  {
    return unit;
  }

  public String getUnitType()
  {
    return unitType;
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

  public void setHash(String hash)
  {
    this.hash = hash;
  }

  public void setHouse(String house)
  {
    this.house = house;
  }

  public void setLatitude(float latitude)
  {
    this.latitude = latitude;
  }

  public void setLongitude(float longitude)
  {
    this.longitude = longitude;
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

  public void setUnit(String unit)
  {
    this.unit = unit;
  }

  public void setUnitType(String unitType)
  {
    this.unitType = unitType;
  }

  @Override
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }
}
