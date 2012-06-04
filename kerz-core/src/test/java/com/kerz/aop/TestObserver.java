package com.kerz.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestObserver extends DispatchingObserver
{
	private Logger log = LoggerFactory.getLogger(TestObserver.class);

	public void handle_ObservableTarget_observableMethod(String arg1, String arg2)
	{
		log.debug("arg1={}, arg2={}", arg1, arg2);
	}
	
	public void handle_ObservableTarget2_observableMethod(String arg1, Integer arg2)
	{
		log.debug("arg1={}, arg2={}", arg1, arg2);
	}
}
