package com.kerz.morphia;

public class Sequence extends MorphiaSettableIdentifiable<String>
{
  private static final long serialVersionUID = 1L;

  long next;

	public long getNext()
  {
  	return next;
  }
}
