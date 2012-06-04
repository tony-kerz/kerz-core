package com.kerz.beans;

import java.util.List;

import org.junit.Test;

public class BeanHelperTest
{
  @Test
  public void basic()
  {
    List<Class<?>> returnTypes = BeanHelper.getReturnTypeActualTypeArguments(TestClass.class, "widgets");
    for (Class<?> clazz : returnTypes)
    {
      System.out.println("return: clazz=" + clazz);
    }

    List<Class<?>> parmTypes = BeanHelper.getParameterTypeActualTypeArguments(TestClass.class, "widgets", List.class);
    for (Class<?> clazz : parmTypes)
    {
      System.out.println("parm: clazz=" + clazz);
    }
  }

  class TestClass
  {
    private List<Widget> widgets;

    public void setWidgets(List<Widget> widgets)
    {
      this.widgets = widgets;
    }

    public List<Widget> getWidgets()
    {
      return widgets;
    }
  }

  class Widget
  {
  }
}
