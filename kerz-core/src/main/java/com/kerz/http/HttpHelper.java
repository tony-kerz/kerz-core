package com.kerz.http;

import java.util.Map;
import java.util.Map.Entry;

public class HttpHelper
{
  public static String getUrl(String domain, Map<String, String> parms)
  {
    return getUrl(domain, parms, false);
  }

  public static String getUrl(String domain, Map<String, String> parms, boolean secure)
  {
    StringBuilder url = new StringBuilder();
    url.append("http");
    if (secure)
    {
      url.append("s");
    }
    url.append("://");
    url.append(domain);
    if ((parms != null) && (parms.size() > 0))
    {
      url.append("?");
    }
    String connector = "";
    for (Entry<String, String> entry : parms.entrySet())
    {
      url.append(connector);
      url.append(entry.getKey()).append("=").append(entry.getValue());
      connector = "&";
    }
    return url.toString();
  }
}
