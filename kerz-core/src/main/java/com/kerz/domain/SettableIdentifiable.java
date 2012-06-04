package com.kerz.domain;

public interface SettableIdentifiable<T> extends Identifiable<T>
{
	void setId(T id);
}
