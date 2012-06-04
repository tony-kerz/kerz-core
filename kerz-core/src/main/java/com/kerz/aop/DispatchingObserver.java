package com.kerz.aop;

import java.util.Observable;
import java.util.Observer;

public class DispatchingObserver implements Observer
{
	@Override
	public void update(Observable o, Object arg)
	{
		ObservedEvent oe = (ObservedEvent) arg;
		oe.dispatch(this);
	}
}
