package com.kerz.jackson2;

import javax.persistence.Entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerz.jpa.NamedVersionedPersistable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ObjectMapperFactoryBeanTest
{
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void basic() throws Throwable
	{
		Widget widget = new Widget(123l, "foobar");

		String json = objectMapper.writeValueAsString(widget);
		System.out.println("initial widget json: " + json);

		widget = objectMapper.readValue(json, Widget.class);

		json = objectMapper.writeValueAsString(widget);
		System.out.println("deserialized widget json: " + json);
	}

	@Test
	public void basicJpa() throws Throwable
	{
		JpaWidget widget = new JpaWidget();
		widget.setName("jpafoobar");

		PropertyAccessor pa = new DirectFieldAccessor(widget);
		pa.setPropertyValue("id", 123l);
		pa.setPropertyValue("version", 456l);
		
		String json = objectMapper.writeValueAsString(widget);
		System.out.println("initial jpa-widget json: " + json);

		widget = objectMapper.readValue(json, JpaWidget.class);

		json = objectMapper.writeValueAsString(widget);
		System.out.println("deserialized jpa-widget json: " + json);
	}
}

@Entity
class JpaWidget extends NamedVersionedPersistable<Long>
{
	private static final long serialVersionUID = 1L;

	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}

class Widget
{
	private Long id;

	private String name;

	Widget()
	{
	}

	Widget(Long id, String name)
	{
		this.id = id;
		this.name = name;
	}

	public Long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}