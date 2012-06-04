package com.kerz.util;

import org.springframework.util.Assert;

public class EnumHelper
{
  /**
   * A common method for all enums since they can't have another base class
   * 
   * @param <T>
   *          Enum type
   * @param clazz
   *          enum type. All enums must be all caps.
   * @param enumString
   *          case sensitive
   * @return corresponding enum, or null 
   */
  public static <T extends Enum<T>> T getEnumFromString(Class<T> clazz, String enumString)
  {
    Assert.notNull(clazz, "class required");
    Assert.notNull(enumString, "enum string required");
    T result = null;
    try
    {
      result = Enum.valueOf(clazz, enumString);
    }
    catch (IllegalArgumentException iae)
    {
      // nada, we will just return null...
    }
    return result;
  }

}
