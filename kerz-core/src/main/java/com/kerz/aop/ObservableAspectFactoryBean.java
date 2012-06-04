package com.kerz.aop;

import java.util.List;
import java.util.Observer;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;

public class ObservableAspectFactoryBean implements FactoryBean<ObservableAspect>
{
	private List<Observer> observers;
	
	@Override
	public ObservableAspect getObject() throws Exception
	{
		Assert.notEmpty(observers, "observers must not be empty");
		ObservableAspect oa = new ObservableAspect();
		for (Observer o : observers)
		{
			oa.addObserver(o);
		}
		return oa;
	}
	
	@Override
	public Class<ObservableAspect> getObjectType()
	{
		return ObservableAspect.class;
	}
	
	@Override
	public boolean isSingleton()
	{
		return true;
	}
	
	public void setObservers(List<Observer> observers)
	{
		this.observers = observers;
	}
	
}
