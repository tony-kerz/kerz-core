package com.kerz.morphia;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MorphiaHelperTest
{
	@Autowired
	private Datastore ds;
	
	@After
	public void after()
	{
		Query<Sequence> q = ds.createQuery(Sequence.class);
		ds.delete(q);
		Assert.assertTrue(ds.getCount(Sequence.class) == 0);
	}
	
	@Test
	public void basic()
	{
		long next = MorphiaHelper.getNextSequence(ds, "testSequence");
		Assert.assertEquals(1, next);
		next = MorphiaHelper.getNextSequence(ds, "testSequence");
		Assert.assertEquals(2, next);
	}
}
