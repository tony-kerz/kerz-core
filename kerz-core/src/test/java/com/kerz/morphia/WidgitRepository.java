package com.kerz.morphia;

import com.google.code.morphia.Datastore;

public class WidgitRepository extends ObjectIdDao<Widgit>
{
	public WidgitRepository(Datastore ds)
	{
		super(ds);
	}
}
