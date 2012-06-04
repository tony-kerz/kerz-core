package com.kerz.beans;

public class PropertyStatus
{
	private boolean excluded;
	private boolean included;

	public PropertyStatus()
	{
	}

	public PropertyStatus(boolean included, boolean excluded)
	{
		this.included = included;
		this.excluded = excluded;
	}

	public boolean isExcluded()
	{
		return excluded;
	}

	public boolean isIncluded()
	{
		return included;
	}

	public void setExcluded(boolean excluded)
	{
		this.excluded = excluded;
	}

	public void setIncluded(boolean included)
	{
		this.included = included;
	}
}
