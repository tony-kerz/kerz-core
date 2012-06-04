package com.kerz.xml;

import java.util.HashMap;
import java.util.Map;

import com.kerz.text.MultipleReplacer;

public class EntityUnreplacer extends MultipleReplacer
{
  private static Map<String, String> s_replaceMap;
  
  static
  {
    s_replaceMap = new HashMap<String, String>(5);
    s_replaceMap.put("&amp;", "&");
    s_replaceMap.put("&apos;", "'");
    s_replaceMap.put("&lt;", "<");
    s_replaceMap.put("&gt;", ">");
    s_replaceMap.put("&quot;", "\"");
  }
  
  public EntityUnreplacer()
  {
    super(s_replaceMap);
  }
}
