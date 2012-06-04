package com.kerz.mail;

import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.Assert;

import com.kerz.text.FormattedException;

public class VelocityMailSender extends AbstractMailSender
{
	Map<String, String> templateMap;

	VelocityEngine velocityEngine;

	@Override
	protected String getText(String bodyCode, Map<String, Object> model)
	{
		String result = null;
		Assert.notNull(templateMap, "template map required");
		Assert.notNull(velocityEngine, "velocity engine required");
		String templateLocation = templateMap.get(bodyCode);
		if (templateLocation != null)
		{
			result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, model);
		}
		else
		{
			throw new FormattedException("unable to obtain template for code={0}", bodyCode);
		}
		return result;
	}
	public void setTemplateMap(Map<String, String> templateMap)
  {
  	this.templateMap = templateMap;
  }
	
	public void setVelocityEngine(VelocityEngine velocityEngine)
  {
  	this.velocityEngine = velocityEngine;
  }
	
}
