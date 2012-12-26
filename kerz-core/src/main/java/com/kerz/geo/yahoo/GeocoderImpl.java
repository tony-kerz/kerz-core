package com.kerz.geo.yahoo;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.kerz.geo.Geocoder;
import com.kerz.geo.GeocoderResponse;
import com.kerz.geo.domain.GeoPoint;
import com.kerz.http.HttpHelper;
import com.kerz.text.FormattedException;

public class GeocoderImpl implements Geocoder, InitializingBean
{
	// http://where.yahooapis.com/geocode?q=1600+Pennsylvania+Avenue,+Washington,+DC&appid=[yourappidhere]
	private static String reverseUrl;
	// http://where.yahooapis.com/geocode?q=38.898717,+-77.035974&gflags=R&appid=[yourappidhere]
	private static String URL_BASE = "where.yahooapis.com/geocode";
	private String appId = "qW9ib95c";
	private Logger log = LoggerFactory.getLogger(GeocoderImpl.class);
	
	private RestTemplate restTemplate;
	
	private String url;
	
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(appId);
		Assert.notNull(restTemplate);
		Map<String, String> map = new HashMap<String, String>();
		map.put("q", "{address}");
		map.put("appId", appId);
		url = HttpHelper.getUrl(URL_BASE, map);
		map.put("q", "{lat},{lng}");
		map.put("gflags", "R");
		reverseUrl = HttpHelper.getUrl(URL_BASE, map);
	}
	
	public GeocoderResponse geocode(String address)
	{
		return geocode(url, address);
	}
	
	private GeocoderResponse geocode(String urlString, Object... urlVariables)
	{
		if (log.isDebugEnabled())
		{
			log.debug("getGeoAddress(): url={}, vars={}", urlString, urlVariables);
		}
		
		for (int i = 0; i < urlVariables.length; i++)
		{
			urlVariables[i] = urlVariables[i].toString().replace(' ', '+');
		}
		
		String xml = getRestTemplate().getForObject(urlString, String.class, urlVariables);
		
		// <?xml version="1.0" encoding="UTF-8"?>
		// <ResultSet version="1.0">
		// <Error>0</Error>
		// <ErrorMessage>No error</ErrorMessage>
		// <Locale>us_US</Locale>
		// <Quality>87</Quality>
		// <Found>1</Found>
		// <Result>
		// <quality>87</quality>
		// <latitude>37.416275</latitude>
		// <longitude>-122.025092</longitude>
		// <offsetlat>37.416397</offsetlat>
		// <offsetlon>-122.025055</offsetlon>
		// <radius>500</radius>
		// <name>
		// </name>
		// <line1>701 1st Ave</line1>
		// <line2>Sunnyvale, CA 94089-1019</line2>
		// <line3>
		// </line3>
		// <line4>United States</line4>
		// <house>701</house>
		// <street>1st Ave</street>
		// <xstreet>
		// </xstreet>
		// <unittype>
		// </unittype>
		// <unit>
		// </unit>
		// <postal>94089-1019</postal>
		// <neighborhood>
		// </neighborhood>
		// <city>Sunnyvale</city>
		// <county>Santa Clara County</county>
		// <state>California</state>
		// <country>United States</country>
		// <countrycode>US</countrycode>
		// <statecode>CA</statecode>
		// <countycode>
		// </countycode>
		// <uzip>94089</uzip>
		// <hash>DDAD1896CC0CDC41</hash>
		// <woeid>12797150</woeid>
		// <woetype>11</woetype>
		// </Result>
		// </ResultSet>
		
		Document doc = null;
		try
		{
			doc = DocumentHelper.parseText(xml);
		}
		catch (Throwable t)
		{
			throw new FormattedException(t, "xml={}", xml);
		}
		
		if (log.isDebugEnabled())
		{
			log.debug("getGeoAddress(): xml={}", xml);
		}
		
		Element resultElt = (Element) doc.selectSingleNode("//ResultSet/Result");
		
		GeocoderResponse geoAddress = new GeocoderResponse();
		geoAddress.setCity(resultElt.elementText("city"));
		geoAddress.setCountryCode(resultElt.elementText("countrycode"));
		geoAddress.setCountyCode(resultElt.elementText("countycode"));
		geoAddress.setCounty(resultElt.elementText("county"));
		geoAddress.setHouse(resultElt.elementText("house"));
		geoAddress.setLatitude(Float.parseFloat(resultElt.elementText("latitude")));
		geoAddress.setLongitude(Float.parseFloat(resultElt.elementText("longitude")));
		geoAddress.setPostalCode(resultElt.elementText("postal"));
		geoAddress.setStateCode(resultElt.elementText("statecode"));
		geoAddress.setStreet(resultElt.elementText("street"));
		geoAddress.setUnit(resultElt.elementText("unit"));
		geoAddress.setUnitType(resultElt.elementText("unittype"));
		geoAddress.setHash(resultElt.elementText("hash"));
		return geoAddress;
	}
	
	public RestTemplate getRestTemplate()
	{
		return restTemplate;
	}
	
	@Override
	public GeocoderResponse reverse(GeoPoint geoPoint)
	{
		return geocode(reverseUrl, geoPoint.getLat(), geoPoint.getLng());
	}
	
	public void setAppId(String appId)
	{
		this.appId = appId;
	}
	
	public void setRestTemplate(RestTemplate restTemplate)
	{
		this.restTemplate = restTemplate;
	}
}
