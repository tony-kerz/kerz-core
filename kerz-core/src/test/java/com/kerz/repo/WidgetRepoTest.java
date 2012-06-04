package com.kerz.repo;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kerz.domain.Widget;
import com.kerz.domain.WidgetSpecificationBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class WidgetRepoTest
{
	@Autowired
	private WidgetRepository m_widgetRepo;
	
	@Autowired
	private WidgetSpecificationBuilder m_wsb;
	
	@Test
	public void findByName()
	{
		Widget w = new Widget();
		w.setName("findByName");
		m_widgetRepo.save(w);
		List<Widget> widgets = m_widgetRepo.findByName("findByName");
		Assert.assertTrue(widgets.size() == 1);
		Widget found = widgets.get(0);
		Assert.assertEquals(found, w);
	}
	
	@Test
	public void findByNameLike()
	{
		Widget w = new Widget();
		w.setName("findByNameLike");
		m_widgetRepo.save(w);
		List<Widget> widgets = m_widgetRepo.findByNameLike("findByNameL%");
		Assert.assertTrue(widgets.size() == 1);
		Widget found = widgets.get(0);
		Assert.assertEquals(found, w);
	}
	
	@Test
	public void genericSpecBuilder()
	{
		Widget w = new Widget();
		w.setName("findByExample");
		m_widgetRepo.save(w);
		Widget example = new Widget();
		example.setName("findByE%");
		List<Widget> widgets = m_widgetRepo.findAll(m_wsb.matchesExample(example));
		Assert.assertTrue(widgets.size() == 1);
		Widget found = widgets.get(0);
		Assert.assertEquals(found, w);
	}
	
	// @Test
	// public void findByExample()
	// {
	// Widget w = new Widget();
	// w.setName("findme");
	// m_widgetService.save(w);
	// Widget example = new Widget();
	// example.setName("find%");
	// List<Widget> widgets = m_widgetService.findByExample(example);
	// Assert.assertTrue(widgets.size() == 1);
	// Widget found = widgets.get(0);
	// Assert.assertEquals(found, w);
	// }
	
	private void save(Widget w)
	{
		m_widgetRepo.save(w);
		long id = w.getId();
		System.out.println("save generated id of [" + id + "]");
		w = m_widgetRepo.findOne(id);
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
