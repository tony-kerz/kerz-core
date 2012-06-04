package com.kerz.service;

import com.kerz.dao.GenericDao;
import com.kerz.domain.Widget;

public class WidgetServiceImpl extends GenericServiceImpl<Widget> implements WidgetService
{
	public WidgetServiceImpl(GenericDao<Widget> dao)
	{
		super(dao);
	}
}
