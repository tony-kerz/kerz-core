package com.kerz.morphia.converter;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.code.morphia.Datastore;
import com.kerz.morphia.MorphiaHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ConverterTest
{
	@Autowired
	private Datastore ds;
	private Logger log = LoggerFactory.getLogger(ConverterTest.class);
	
	// @After
	public void after()
	{
		MorphiaHelper.deleteAll(ds, ThingWithNonEntity.class);
	}
	
	@Test
	public void converter()
	{
		ThingWithNonEntity twnt = new ThingWithNonEntity();
		DateTime dateTime = new DateTime();
		twnt.setDateTime(dateTime);
		ds.save(twnt);
		twnt = ds.get(twnt);
		Assert.assertEquals(dateTime, twnt.getDateTime());
	}
}
