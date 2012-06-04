package com.kerz.text;

import java.text.MessageFormat;

public class FormattedException extends RuntimeException
{
  private static final long serialVersionUID = 1L;

  public FormattedException(String pattern, Object... arguments)
  {
    super(MessageFormat.format(pattern, arguments));
  }

  public FormattedException(Throwable t, String pattern, Object... arguments)
  {
    super(MessageFormat.format(pattern, arguments), t);
  }

}
