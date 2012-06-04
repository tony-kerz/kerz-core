package com.kerz.morphia;

import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.annotations.Entity;

@Entity
public class Widgit extends MorphiaIdentifiable
{
	private static final long serialVersionUID = 1L;
	
	private List<Whatsit> whatsits = new ArrayList<Whatsit>();
	
	public List<Whatsit> getWhatsits()
	{
		return whatsits;
	}
}
