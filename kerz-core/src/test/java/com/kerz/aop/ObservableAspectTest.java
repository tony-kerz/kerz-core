package com.kerz.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ObservableAspectTest
{
	@Autowired
	private ObservableTarget ot;
	@Autowired
	private ObservableTarget2 ot2;
	
	@Test
	public void basic()
	{
		ot.observableMethod("yoo", "hoo");
		ot2.observableMethod("boo", 2);
	}
}
