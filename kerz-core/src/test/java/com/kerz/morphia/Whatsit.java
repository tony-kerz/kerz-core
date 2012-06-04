package com.kerz.morphia;

import java.util.ArrayList;
import java.util.List;

import com.kerz.domain.SettableIdentifiableBase;

public class Whatsit extends SettableIdentifiableBase<Integer>
{
	private List<Whoosit> whoosits = new ArrayList<Whoosit>();
	
	protected Whatsit()
	{
	}
	
	public Whatsit(Integer id)
	{
		this.id = id;
	}
	
	public List<Whoosit> getWhoosits()
	{
		return whoosits;
	}
}
