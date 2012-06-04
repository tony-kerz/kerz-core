package com.kerz.morphia;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

public class DatastoreFactoryBean implements FactoryBean<Datastore>
{
	private String databaseName;
	private String hostName;
	
	public DatastoreFactoryBean(String hostName, String databaseName)
	{
		Assert.notNull(hostName);
		Assert.notNull(databaseName);
		this.hostName = hostName;
		this.databaseName = databaseName;
	}
	
	@Override
	public Datastore getObject() throws Exception
	{
		Mongo mongo = new Mongo(hostName);
		return new Morphia().createDatastore(mongo, databaseName);
	}
	
	@Override
	public Class<Datastore> getObjectType()
	{
		return Datastore.class;
	}
	
	@Override
	public boolean isSingleton()
	{
		return true;
	}
	
}
