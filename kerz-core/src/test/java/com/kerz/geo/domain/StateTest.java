package com.kerz.geo.domain;

import junit.framework.Assert;

import org.junit.Test;

public class StateTest
{
	@Test
	public void reverse()
	{
		State result = State.reverseLookup("New York");
		Assert.assertEquals("NY", result.toString());
	}

	@Test
	public void nullName()
	{
		State result = State.reverseLookup(null);
		Assert.assertNull(result);
	}	
	
	@Test
	public void nonName()
	{
		State result = State.reverseLookup("find-me");
		Assert.assertNull(result);
	}
}
