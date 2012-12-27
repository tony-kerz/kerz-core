package com.kerz.geo.nominatim;

import java.util.Map;
import java.util.TreeMap;

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
import com.kerz.geo.domain.State;
import com.kerz.http.HttpHelper;
import com.kerz.text.FormattedException;

public class GeocoderImpl implements Geocoder, InitializingBean
{
	private static String BASE_URL = "nominatim.openstreetmap.org";
	// http://nominatim.openstreetmap.org/search?q=20001&countrycodes=US&format=xml&addressdetails=1&limit=1&email=anthony.kerz@gmail.com
	private static String GEO_URL = BASE_URL + "/search";
	// http://nominatim.openstreetmap.org/reverse?format=xml&lat=52.5487429714954&lon=-1.81602098644987&zoom=18&addressdetails=1&limit=1&email=anthony.kerz@gmail.com
	private static String REV_URL = BASE_URL + "/reverse";
	private static String GEO_XPATH = "//searchresults/place";
	private static String REV_XPATH = "//reversegeocode/addressparts";
	private static String reverseUrl;
	private String appId = "anthony.kerz@gmail.com";
	private Logger log = LoggerFactory.getLogger(GeocoderImpl.class);
	private RestTemplate restTemplate;
	private String url;

	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(appId);
		Assert.notNull(restTemplate);
		Map<String, String> map = new TreeMap<String, String>();
		map.put("q", "{address}");
		map.put("email", appId);
		map.put("countrycodes", "US");
		map.put("format", "xml");
		map.put("addressdetails", "1");
		map.put("limit", "1");
		url = HttpHelper.getUrl(GEO_URL, map);
		map.remove("q");
		map.put("lat", "{lat}");
		map.put("lon", "{lon}");
		reverseUrl = HttpHelper.getUrl(REV_URL, map);
	}

	public GeocoderResponse geocode(String address)
	{
		return geocode(url, GEO_XPATH, address);
	}

	private GeocoderResponse geocode(String urlString, String xpath, Object... urlVariables)
	{
		if (log.isDebugEnabled())
		{
			log.debug("getGeoAddress(): url={}, vars={}", urlString, urlVariables);
		}

		String xml = getRestTemplate().getForObject(urlString, String.class, urlVariables);

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

		Element resultElt = (Element) doc.selectSingleNode(xpath);

		GeocoderResponse geoAddress = new GeocoderResponse();
		geoAddress.setCity(resultElt.elementText("city"));
		geoAddress.setCountryCode(resultElt.elementText("country_code"));
		// nominatim currently doesn't support count-code
		// geoAddress.setCountyCode(resultElt.elementText("countycode"));
		geoAddress.setCounty(resultElt.elementText("county"));
		geoAddress.setHouse(resultElt.elementText("house_number"));
		geoAddress.setLatitude(getFloat(resultElt, "lat"));
		geoAddress.setLatitude(getFloat(resultElt, "lon"));
		geoAddress.setPostalCode(resultElt.elementText("postcode"));
		geoAddress.setState(resultElt.elementText("state"));
		State state = State.reverseLookup(geoAddress.getState());
		if (state != null)
		{
			geoAddress.setStateCode(state.toString());
		}
		geoAddress.setStreet(resultElt.elementText("road"));
		// nominatim currently doesn't support unit and unittype details
		// geoAddress.setUnit(resultElt.elementText("unit"));
		// geoAddress.setUnitType(resultElt.elementText("unittype"));
		geoAddress.setHash(resultElt.attributeValue("place_id"));
		return geoAddress;
	}

	float getFloat(Element elt, String attrName)
	{
		float result = 0;
		String floatString = elt.attributeValue(attrName);
		if (floatString != null)
		{
			result = Float.parseFloat(floatString);
		}
		return result;
	}

	public RestTemplate getRestTemplate()
	{
		return restTemplate;
	}

	@Override
	public GeocoderResponse reverse(GeoPoint geoPoint)
	{
		return geocode(reverseUrl, REV_XPATH, geoPoint.getLat(), geoPoint.getLng());
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
