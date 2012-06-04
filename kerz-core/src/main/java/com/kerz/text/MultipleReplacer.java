package com.kerz.text;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultipleReplacer
{
  private Map<String, String> m_replaceMap;
  private Pattern m_replacePattern;

  public MultipleReplacer(Map<String, String> replaceMap)
  {
    StringBuilder regex = new StringBuilder();
    String connector = "";
    for (String key : replaceMap.keySet())
    {
      regex.append(connector).append(key);
      connector = "|";
    }
    m_replacePattern = Pattern.compile(regex.toString());
    m_replaceMap = replaceMap;
  }

  public String replaceAll(String input)
  {
    StringBuffer sb = new StringBuffer(input.length());
    Matcher m = m_replacePattern.matcher(input);

    int end = 0;
    while (m.find())
    {
      String wordToReplace = m.group();
      String replacement = (String) m_replaceMap.get(wordToReplace);
      m.appendReplacement(sb, replacement);
      end = m.end();
    }
    sb.append(input.substring(end));

    return sb.toString();
  }
}
