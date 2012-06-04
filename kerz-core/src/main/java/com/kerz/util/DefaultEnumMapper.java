package com.kerz.util;

import java.util.HashMap;
import java.util.Map;

public class DefaultEnumMapper implements EnumMapper
{
  private Map<Class<?>, Map<String, String>> cache = new HashMap<Class<?>, Map<String, String>>();

  public <T extends Enum<T>> Map<String, String> getMap(Class<T> enumm)
  {
    Map<String, String> result = cache.get(enumm);
    if (result == null)
    {
      result = new HashMap<String, String>();
      T[] constants = enumm.getEnumConstants();
      for (int i = 0; i < constants.length; i++)
      {
        T constant = constants[i];
        result.put(constant.name(), getValue(constant));
      }
      cache.put(enumm, result);
    }
    return result;
  }

  protected <T extends Enum<T>> String getValue(T enumConstant)
  {
    return enumConstant.toString();
  }
}
