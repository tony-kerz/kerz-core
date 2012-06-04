package com.kerz.morphia;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

public class ObjectIdDao<T> extends BasicDAO<T, ObjectId>
{
	public ObjectIdDao(Class<T> entityClass, Datastore ds)
	{
		super(entityClass, ds);
	}
	
	public ObjectIdDao(Class<T> entityClass, Mongo mongo, Morphia morphia, String dbName)
	{
		super(entityClass, mongo, morphia, dbName);
	}
	
	public ObjectIdDao(Datastore ds)
	{
		super(ds);
	}
	
	public ObjectIdDao(Mongo mongo, Morphia morphia, String dbName)
	{
		super(mongo, morphia, dbName);
	}
	
	public WriteResult deleteById(String id)
	{
		return super.deleteById(new ObjectId(id));
	}
	
	public T get(String id)
	{
		return super.get(new ObjectId(id));
	}
	
}
