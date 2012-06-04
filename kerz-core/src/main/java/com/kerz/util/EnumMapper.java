package com.kerz.util;

import java.util.Map;

public interface EnumMapper
{
  <T extends Enum<T>> Map<String, String> getMap(Class<T> enumm);
}
