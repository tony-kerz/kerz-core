package com.kerz.beans;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author A716948
 *
 */
public class BeanHelper
{
  /**
   * http://tutorials.jenkov.com/java-reflection/generics.html#returntypes
   * @param clazz
   * @param propertyName
   * @return
   */
  public static List<Class<?>> getReturnTypeActualTypeArguments(Class<?> clazz, String propertyName)
  {
    List<Class<?>> result = new ArrayList<Class<?>>();

    try
    {
      Method method = clazz.getMethod("get" + StringUtils.capitalize(propertyName));

      Type returnType = method.getGenericReturnType();

      if (returnType instanceof ParameterizedType)
      {
        ParameterizedType type = (ParameterizedType) returnType;
        Type[] typeArguments = type.getActualTypeArguments();
        for (Type typeArgument : typeArguments)
        {
          Class<?> typeArgClass = (Class<?>) typeArgument;
          result.add(typeArgClass);
        }
      }
    }
    catch (Throwable t)
    {
      throw new RuntimeException(t);
    }

    return result;
  }

  /**
   * http://tutorials.jenkov.com/java-reflection/generics.html#parametertypes
   * @param clazz
   * @param propertyName
   * @param propertyClass
   * @return
   */
  public static List<Class<?>> getParameterTypeActualTypeArguments(Class<?> clazz, String propertyName,
      Class<?> propertyClass)
  {
    List<Class<?>> result = new ArrayList<Class<?>>();

    try
    {
      Method method = clazz.getMethod("set" + StringUtils.capitalize(propertyName), propertyClass);

      Type[] genericParameterTypes = method.getGenericParameterTypes();

      for (Type genericParameterType : genericParameterTypes)
      {
        if (genericParameterType instanceof ParameterizedType)
        {
          ParameterizedType aType = (ParameterizedType) genericParameterType;
          Type[] parameterArgTypes = aType.getActualTypeArguments();
          for (Type parameterArgType : parameterArgTypes)
          {
            Class<?> parameterArgClass = (Class<?>) parameterArgType;
            result.add(parameterArgClass);
          }
        }
      }
    }
    catch (Throwable t)
    {
      throw new RuntimeException(t);
    }

    return result;
  }
}
