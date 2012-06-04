package com.kerz.dom4j;

import java.io.PrintStream;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.XPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.core.Conventions;

import com.kerz.text.FormattedException;
import com.kerz.xml.XmlHelper;

/**
 * @author A716948
 * 
 */
public class Dom4jHelper
{
  public static Element addExceptionElement(Element element, Throwable t)
  {
    Element exceptionElement = element.addElement("exception");
    exceptionElement.addText(ExceptionUtils.getStackTrace(t));
    return element;
  }

  public static Element asElement(Element elt, Throwable t)
  {
    return asElement(elt, t, 0);
  }

  private static Element asElement(Element elt, Throwable t, int nestLevel)
  {
    elt.addAttribute("message", t.getMessage());
    elt.addAttribute("class", t.getClass().getName());

    Element stackElt = elt.addElement("stack");
    StackTraceElement[] elts = t.getStackTrace();
    for (int i = 0; i < elts.length; i++)
    {
      stackElt.addElement("entry").addText(elts[i].toString());
    }
    Throwable cause = t.getCause();
    if (cause != null)
    {
      nestLevel++;
      Element causeElt = elt.addElement("cause");
      causeElt.addAttribute("nest", Integer.toString(nestLevel));
      asElement(causeElt, cause, nestLevel);
    }
    return elt;
  }

  public static Element asElement(Throwable t)
  {
    Element elt = DocumentHelper.createElement(Conventions.getVariableName(t));
    return asElement(elt, t, 0);
  }

  public static boolean getAttributeAsBoolean(Element elt, String attrName)
  {
    return Boolean.parseBoolean(elt.attributeValue(attrName));
  }

  public static long getAttributeAsLong(Element elt, String attrName, long dflt)
  {
    long result = 0;
    try
    {
      result = Long.parseLong(elt.attributeValue(attrName));
    }
    catch (NumberFormatException nfe)
    {
      result = dflt;
    }
    return result;
  }

  public static final String getElementWithNamespace(QName element, String prefix)
  {
    return getElementWithNamespace(element, prefix, null);
  }

  public static final String getElementWithNamespace(QName element, String prefix, String url)
  {
    return XmlHelper.getElementWithNamespace(element.getQualifiedName(), prefix, url);
  }

  public static final String getEmptyTag(QName element)
  {
    return XmlHelper.getEmptyTag(element.getQualifiedName());
  }

  public static final String getOpenTag(QName name)
  {
    return XmlHelper.getOpenTag(name.getQualifiedName());
  }

  public static String getRequiredAttributeValue(Element elt, String attrName)
  {
    String value = elt.attributeValue(attrName);
    if (StringUtils.isEmpty(value))
    {
      throw new FormattedException("required attribute not found: attrName={0}, element={1}", attrName, elt.asXML());
    }
    return value;
  }

  public static void prettyPrint(Document document)
  {
    prettyPrint(document, System.out);
  }

  public static void prettyPrint(Document document, PrintStream printStream)
  {
    try
    {
      OutputFormat format = OutputFormat.createPrettyPrint();
      XMLWriter writer = new XMLWriter(printStream, format);
      writer.write(document);
    }
    catch (Throwable t)
    {
      throw new RuntimeException(t);
    }
  }

  public static Node selectSingleNode(Map<String, String> uriMap, String xpathExp, Node node)
  {
    XPath xpath = DocumentHelper.createXPath(xpathExp);
    xpath.setNamespaceURIs(uriMap);
    Node result = xpath.selectSingleNode(node);

    if (result == null)
    {
      throw new FormattedException("xpath expression yielded no results: uriMap={0}, xpath={1}, xml={2}", uriMap,
          xpathExp, node.asXML());
    }

    return result;
  }

  /**
   * @param o
   * @return if o is a Node return asXML() else return o.toString()
   */
  public static String toString(Object o)
  {
    String result = null;
    if (o instanceof Node)
    {
      Node n = (Node) o;
      result = n.asXML();
    }
    else
    {
      result = o.toString();
    }
    return result;
  }
}
