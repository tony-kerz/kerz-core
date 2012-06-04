package com.kerz.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.Assert;

public class MessageSourceEnumMapper extends DefaultEnumMapper
{
  @Autowired
  private MessageSource messageSource;

  public MessageSourceEnumMapper()
  {
  }

  public MessageSourceEnumMapper(MessageSource messageSource)
  {
    setMessageSource(messageSource);
  }

  public void setMessageSource(MessageSource messageSource)
  {
    this.messageSource = messageSource;
  }

  protected <T extends Enum<T>> String getValue(T enumConstant)
  {
    Assert.notNull(messageSource, "message source required");
    String code = getCode(enumConstant);
    return messageSource.getMessage(code, null, "{" + code + "}", null);
  }

  <T extends Enum<T>> String getCode(T enumConstant)
  {
    String simpleName = enumConstant.getClass().getSimpleName();
    return simpleName + "." + enumConstant.name();
  }
}
