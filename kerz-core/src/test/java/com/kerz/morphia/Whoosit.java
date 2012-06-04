package com.kerz.morphia;

import com.kerz.domain.SettableIdentifiableBase;

public class Whoosit extends SettableIdentifiableBase<Integer>
{
	protected Whoosit()
	{
	}
	
	public Whoosit(Integer id)
	{
		this.id = id;
	}
}
