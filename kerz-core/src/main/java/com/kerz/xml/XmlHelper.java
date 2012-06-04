package com.kerz.xml;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.kerz.dom4j.Dom4jHelper;

public class XmlHelper
{
  private static final String CDATA_CLOSE = "]]>";
  private static final String CDATA_CLOSE_ENCODE = "]]]]><![CDATA[>";
  private static final String CDATA_OPEN = "<![CDATA[";
  private static final int CDATA_LENGTH = CDATA_OPEN.length() + CDATA_CLOSE.length();
  private final static String END_QUOTE = "\"";
  private final static String EQUALS_OPEN_QUOTE = "=\"";
  private final static String DUMMY_EQUALS_URL = EQUALS_OPEN_QUOTE + "x" + END_QUOTE;
  private static EntityUnreplacer s_entityUnreplacer = new EntityUnreplacer();
  private final static String XMLNS = "xmlns";
  private final static String SPACE_XMLNS_COLON = " " + XMLNS + ":";

  public static String asCdata(String data)
  {
    StringBuilder sb = new StringBuilder(data.length() + CDATA_LENGTH);
    sb.append(CDATA_OPEN).append(data).append(CDATA_CLOSE);
    return sb.toString();
  }

  public static String asXml(Throwable t)
  {
    Element elt = Dom4jHelper.asElement(t);
    Document doc = DocumentHelper.createDocument(elt);
    return doc.asXML();
  }

  public static final String encodeCdata(String xml)
  {
    return xml.replace(CDATA_CLOSE, CDATA_CLOSE_ENCODE);
  }

  public static Object fromXml(String xml)
  {
    InputStream is = IOUtils.toInputStream(xml);
    XMLDecoder decoder = new XMLDecoder(is);
    Object o = decoder.readObject();
    decoder.close();
    return o;
  }

  public static final String getCloseTag(String name)
  {
    return "</" + name + ">";
  }

  public static final String getElementWithNamespace(String element, String prefix)
  {
    return getElementWithNamespace(element, prefix, null);
  }

  public static final String getElementWithNamespace(String element, String prefix, String url)
  {
    String equalsUrl = null;
    if (url != null)
    {
      equalsUrl = EQUALS_OPEN_QUOTE + url + END_QUOTE;
    }
    else
    {
      equalsUrl = DUMMY_EQUALS_URL;
    }
    return element + SPACE_XMLNS_COLON + prefix + equalsUrl;
  }

  public static final String getEmptyTag(String name)
  {
    return "<" + name + "/>";
  }

  private static EntityUnreplacer getEntityUnreplacer()
  {
    return s_entityUnreplacer;
  }

  public static final String getOpenTag(String name)
  {
    return "<" + name + ">";
  }

  /**
   * used in character() method of DefaultHandler subclasses to skip carriage
   * return type data
   * 
   * @param data
   * @param length
   * @return
   */
  public static boolean isWhiteSpace(char[] data, int length)
  {
    return isWhiteSpace(data, 0, length);
  }

  /**
   * used in character() method of DefaultHandler subclasses to skip carriage
   * return type data
   * 
   * @param data
   * @param start
   * @param length
   * @return
   */
  public static boolean isWhiteSpace(char[] data, int start, int length)
  {
    boolean result = true;
    for (int i = 0; i < length; i++)
    {
      if (!Character.isWhitespace(data[start + i]))
      {
        result = false;
        break;
      }
    }
    return result;
  }

  public static void prettyPrint(String xml)
  {
    prettyPrint(xml, System.out);
  }

  public static void prettyPrint(String xml, PrintStream printStream)
  {
    try
    {
      Dom4jHelper.prettyPrint(DocumentHelper.parseText(xml), printStream);
    }
    catch (DocumentException e)
    {
      throw new RuntimeException(e);
    }
  }

  public static String replaceEntities(String s)
  {
    StringBuilder sb = new StringBuilder(s);
    int len = s.length();
    int skip = 0;
    int iSkip = 0;
    for (int i = 0; i < len; i++)
    {
      iSkip = i + skip;
      char c = sb.charAt(iSkip);
      if (c == '&')
      {
        sb.replace(iSkip, iSkip + 1, "&amp;");
        skip += 4;
      }
      else if (c == '\'')
      {
        sb.replace(iSkip, iSkip + 1, "&apos;");
        skip += 5;
      }
      else if (c == '<')
      {
        sb.replace(iSkip, iSkip + 1, "&lt;");
        skip += 3;
      }
      else if (c == '>')
      {
        sb.replace(iSkip, iSkip + 1, "&gt;");
        skip += 3;
      }
      else if (c == '"')
      {
        sb.replace(iSkip, iSkip + 1, "&quot;");
        skip += 5;
      }
    }
    return sb.toString();
  }

  public static String toXml(Object o) throws IOException
  {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    XMLEncoder e = new XMLEncoder(baos);
    e.writeObject(o);
    e.close();
    return baos.toString();
  }

  public static String unreplaceEntities(String xml)
  {
    return getEntityUnreplacer().replaceAll(xml);
  }
}
