package com.kerz.geo;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.kerz.geo.domain.GeoPoint;

public class BoundingBox
{
	private double minLat, maxLat, minLng, maxLng;
	private GeoPoint origin;
	private int radiusMiles;
	
	public BoundingBox(GeoPoint origin, int radiusMiles)
	{
		super();
		this.origin = origin;
		this.radiusMiles = radiusMiles;
		double originLng = origin.getLng();
		double originLat = origin.getLat();
		GeoLocation gl = GeoLocation.fromDegrees(originLat, originLng);
		GeoLocation[] gls = gl.boundingCoordinates(radiusMiles, GeoConstants.EARTH_RADIUS_MI);
		
		minLng = gls[0].getLongitudeInDegrees();
		maxLng = gls[1].getLongitudeInDegrees();
		
		minLat = gls[0].getLatitudeInDegrees();
		maxLat = gls[1].getLatitudeInDegrees();
	}
	
	public double getMaxLat()
	{
		return maxLat;
	}
	
	public double getMaxLng()
	{
		return maxLng;
	}
	
	public double getMinLat()
	{
		return minLat;
	}
	
	public double getMinLng()
	{
		return minLng;
	}
	
	public GeoPoint getOrigin()
	{
		return origin;
	}
	
	public int getRadiusMiles()
	{
		return radiusMiles;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
