package com.kerz.geo.domain;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum State
{
	AL("Alabama"), MT("Montana"), AK("Alaska"), NE("Nebraska"), AZ("Arizona"), NV("Nevada"), AR("Arkansas"), NH(
			"New Hampshire"), CA("California"), NJ("New Jersey"), CO("Colorado"), NM("New Mexico"), CT("Connecticut"), NY(
			"New York"), DE("Delaware"), NC("North Carolina"), FL("Florida"), ND("North Dakota"), GA("Georgia"), OH("Ohio"), HI(
			"Hawaii"), OK("Oklahoma"), ID("Idaho"), OR("Oregon"), IL("Illinois"), PA("Pennsylvania"), IN("Indiana"), RI(
			"RhodeIsland"), IA("Iowa"), SC("South Carolina"), KS("Kansas"), SD("South Dakota"), KY("Kentucky"), TN(
			"Tennessee"), LA("Louisiana"), TX("Texas"), ME("Maine"), UT("Utah"), MD("Maryland"), VT("Vermont"), MA(
			"Massachusetts"), VA("Virginia"), MI("Michigan"), WA("Washington"), MN("Minnesota"), WV("West Virginia"), MS(
			"Mississippi"), WI("Wisconsin"), MO("Missouri"), WY("Wyoming"), DC("District of Columbia");

	public static Map<String, State> lookupMap = new HashMap<String, State>();

	static
	{
		for (State item : EnumSet.allOf(State.class))
		{
			lookupMap.put(item.value, item);
		}
	}

	private final String value;

	State(String value)
	{
		this.value = value;
	}

	public static State reverseLookup(String name)
	{
		return lookupMap.get(name);
	}
}