package com.kerz.geo.domain;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Address implements Serializable
{
  private static final long serialVersionUID = 1L;

  @NotNull
	@Valid
	@Embedded
	private Building building = new Building();
	
	public String getPhone()
	{
		return phone;
	}
	
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	
	private String unit;
	private UnitType unitType;
	private String phone;
	
	protected Address()
	{
	}
	
	public void setUnit(String unit)
	{
		this.unit = unit;
	}
	
	public void setUnitType(UnitType unitType)
	{
		this.unitType = unitType;
	}
	
	public Address(Building building)
	{
		super();
		this.building = building;
	}
	
	public Address(Building building, String unit, UnitType unitType)
	{
		super();
		this.building = building;
		this.unit = unit;
		this.unitType = unitType;
	}
	
	public Building getBuilding()
	{
		return building;
	}
	
	public String getUnit()
	{
		return unit;
	}
	
	public UnitType getUnitType()
	{
		return unitType;
	}
	
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
