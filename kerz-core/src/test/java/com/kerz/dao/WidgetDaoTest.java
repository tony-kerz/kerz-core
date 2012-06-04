package com.kerz.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kerz.domain.Widget;
import com.kerz.service.WidgetService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
// public class WidgetDaoTest extends
// AbstractTransactionalJUnit4SpringContextTests
public class WidgetDaoTest
{
	@Autowired
	private WidgetDao m_widgetDao;
	
	@Autowired
	private WidgetService m_widgetService;
	
	@Test
	public void findByExample()
	{
		Widget w = new Widget();
		w.setName("findme");
		m_widgetService.save(w);
		Widget example = new Widget();
		example.setName("find%");
		List<Widget> widgets = m_widgetService.findByExample(example);
		Assert.assertTrue(widgets.size() == 1);
		Widget found = widgets.get(0);
		Assert.assertEquals(found, w);
	}
	
	private void save(Widget w)
	{
		m_widgetService.save(w);
		long id = w.getId();
		System.out.println("save generated id of [" + id + "]");
		w = m_widgetDao.findById(id);
		System.out.println("findById() returned [" + w + "]");
		Assert.assertNotNull(w);
		Assert.assertTrue(id == w.getId());
	}
	
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void saveInvalid()
	{
		Widget w = new Widget();
		w.setName("w1");
		save(w);
	}
	
	@Test
	public void saveValid()
	{
		Widget w = new Widget();
		w.setName("widget-one");
		save(w);
	}
}
