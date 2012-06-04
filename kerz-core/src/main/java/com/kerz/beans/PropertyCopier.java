package com.kerz.beans;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

public class PropertyCopier
{
	private static final Logger log = LoggerFactory.getLogger(PropertyCopier.class);

	private PropertyProcessor propertyProcessor;

	public void copyProperties(Object sourceBean, final Object targetBean)
	{
		final BeanWrapper sourceBeanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(sourceBean);
		final BeanWrapper targetBeanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(targetBean);
		final PropertyAccessor targetPropertyAccessor = PropertyAccessorFactory.forDirectFieldAccess(targetBean);
		propertyProcessor.process(sourceBean.getClass(), new PropertyCallback()
		{
			@Override
			public void doWithProperty(String propertyName)
			{
				Object value = sourceBeanWrapper.getPropertyValue(propertyName);
				log.debug("copying property=[{}] with value=[{}]", propertyName, value);
				if (targetBeanWrapper.isWritableProperty(propertyName))
				{
					targetBeanWrapper.setPropertyValue(propertyName, value);
				}
				else if (targetPropertyAccessor.isWritableProperty(propertyName))
				{
					targetPropertyAccessor.setPropertyValue(propertyName, value);
					log.debug("resorted to field level access");
				}
				else
				{
					throw new RuntimeException(MessageFormat.format("unable to access property=[{}] on bean-class=[{}]", propertyName,
							targetBean.getClass()));
				}
			}
		});
	}

	public void setPropertyProcessor(PropertyProcessor propertyProcessor)
	{
		this.propertyProcessor = propertyProcessor;
	}
}
