package com.kerz.domain;

import java.util.List;

public class IdentifiableHelper
{
	public static <T> Identifiable<T> find(List<? extends Identifiable<T>> list, T id)
	{
		Identifiable<T> result = null;
		for (Identifiable<T> i : list)
		{
			if (i.getId().equals(id))
			{
				result = i;
				break;
			}
		}
		return result;
	}
}
