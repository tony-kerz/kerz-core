package com.kerz.morphia;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.util.Assert;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.code.morphia.query.UpdateResults;

public class MorphiaHelper
{
	public static <T> void deleteAll(Datastore ds, Class<T> clazz)
	{
		Query<T> q = ds.createQuery(clazz);
		ds.delete(q);
		Assert.isTrue(ds.getCount(clazz) == 0);
	}
	
	public static long getNextSequence(Datastore ds, Class<?> clazz)
	{
		return getNextSequence(ds, StringUtils.uncapitalize(clazz.getSimpleName()));
	}
	
	public static long getNextSequence(Datastore ds, String sequenceName)
	{
		Query<Sequence> q = ds.createQuery(Sequence.class).field(Mapper.ID_KEY).equal(sequenceName);
		UpdateOperations<Sequence> ops = ds.createUpdateOperations(Sequence.class).inc("next");
		Sequence seq = ds.findAndModify(q, ops, false, true);
		return seq.getNext();
	}
	
	public static <T> UpdateResults<T> updateFirst(BasicDAO<T, ObjectId> repo, Query<T> q, UpdateOperations<T> ops)
	{
		UpdateResults<T> results = repo.updateFirst(q, ops);
		Assert.isTrue(!results.getHadError(), "update error");
		Assert.isTrue(results.getUpdatedCount() == 1, "unexpected update count");
		return results;
	}
}
