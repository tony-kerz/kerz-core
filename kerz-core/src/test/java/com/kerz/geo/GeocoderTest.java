package com.kerz.geo;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kerz.geo.domain.GeoPoint;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class GeocoderTest
{
	@Autowired
	private Geocoder geocoder;
	
	@Test
	public void basic()
	{
		String address = "151 farmington avenue, hartford";
		GeocoderResponse geoAddress = geocoder.geocode(address);
		System.out.println(geoAddress);
	}
	
	@Test
	public void zip()
	{
		String address = "06067";
		GeocoderResponse geoAddress = geocoder.geocode(address);
		System.out.println(geoAddress);
	}
	
	@Test
	public void hash()
	{
		GeocoderResponse geoAddress1 = geocoder.geocode("300 hebron avenue, glastonbury ct");
		System.out.println(geoAddress1);
		GeocoderResponse geoAddress2 = geocoder.geocode("300 hebron avenue, suite 205, glastonbury ct");
		System.out.println(geoAddress2);
		GeocoderResponse geoAddress3 = geocoder.geocode("113, hebron ave, glastonbury ct");
		System.out.println(geoAddress3);
		Assert.assertEquals(geoAddress1.getHash(), geoAddress2.getHash());
		Assert.assertNotSame(geoAddress1.getHash(), geoAddress3.getHash());
	}
	
	@Test
	public void reverse()
	{
		GeocoderResponse geoAddress = geocoder.reverse(new GeoPoint(37.5627, -122.3195));
		Assert.assertEquals(geoAddress.getCity(), "San Mateo");
		System.out.println(geoAddress);
	}
	
	@Test
	public void county()
	{
		String address = "06067";
		GeocoderResponse geoAddress = geocoder.geocode(address);
		System.out.println(geoAddress);
		Assert.assertEquals(geoAddress.getCounty(), "Hartford County");
	}
}
