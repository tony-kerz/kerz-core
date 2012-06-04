package com.kerz.beans;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @author tony
 * 
 *         <p>
 *         there can be multiple of these filters applying to a single bean via
 *         a global filter, superclasses and interfaces, so there will be a
 *         cumulative effect described by the following rules:
 *         </p>
 * 
 *         <ol>
 *         <li>if beanClass is null, this filter will apply to all classes</li>
 *         <li>if includeProperties is null, <b>all</b> properties will be
 *         included</li>
 *         <li>if excludeProperties is null, <b>no</b> properties will be
 *         excluded</li>
 *         <li>w.r.t. superclasses and interfaces, included properties are
 *         cumulative</li>
 *         <li>w.r.t. superclasses and interfaces, once a property is excluded
 *         it will always be excluded</li>
 *         </ol>
 */
public class PropertyProcessor
{
	private Map<Class<?>, PropertyFilter> filterMap = new HashMap<Class<?>, PropertyFilter>();

	private Map<Class<?>, Set<String>> includedPropertiesMap = new HashMap<Class<?>, Set<String>>();
	private Logger log = LoggerFactory.getLogger(PropertyProcessor.class);

	public void addPropertyFilter(PropertyFilter propertyFilter)
	{
		Class<?> beanClass = propertyFilter.getBeanClass();
		PropertyFilter existingPropertyFilter = filterMap.get(beanClass);
		if (existingPropertyFilter != null)
		{
			// create new filter as merge of existing and current
			// this code should be DRY-ed up!
			PropertyFilter mergedPropertyFilter = new PropertyFilter();
			mergedPropertyFilter.setBeanClass(beanClass);

			Set<String> mergedProperties = null;
			Set<String> mergeTarget = propertyFilter.getIncludeProperties();
			if (mergeTarget != null)
			{
				mergedProperties = new HashSet<String>(mergeTarget);
			}
			mergeTarget = existingPropertyFilter.getIncludeProperties();
			if (mergeTarget != null)
			{
				if (mergedProperties != null)
				{
					mergedProperties.addAll(mergeTarget);

				}
				else
				{
					mergedProperties = new HashSet<String>(mergeTarget);
				}
			}
			mergedPropertyFilter.setIncludeProperties(mergedProperties);

			mergedProperties = null;
			mergeTarget = propertyFilter.getExcludeProperties();
			if (mergeTarget != null)
			{
				mergedProperties = new HashSet<String>(mergeTarget);
			}
			mergeTarget = existingPropertyFilter.getExcludeProperties();
			if (mergeTarget != null)
			{
				if (mergedProperties != null)
				{
					mergedProperties.addAll(mergeTarget);

				}
				else
				{
					mergedProperties = new HashSet<String>(mergeTarget);
				}
			}
			mergedPropertyFilter.setExcludeProperties(mergedProperties);
			
			filterMap.put(beanClass, mergedPropertyFilter);
		}
		else
		{
			filterMap.put(beanClass, propertyFilter);
		}
	}

	public Set<String> getIncludedProperties(Class<?> beanClass)
	{
		Set<String> includedProperties = includedPropertiesMap.get(beanClass);
		if (includedProperties == null)
		{
			includedProperties = new HashSet<String>();

			List<Class<?>> classes = new ArrayList<Class<?>>();
			classes.add(null); // global property-filter
			classes.add(beanClass);
			classes.addAll(ClassUtils.getAllSuperclasses(beanClass));
			classes.addAll(ClassUtils.getAllInterfaces(beanClass));

			List<PropertyFilter> propertyFilters = new ArrayList<PropertyFilter>();
			for (Class<?> clazz : classes)
			{
				PropertyFilter propertyFilter = filterMap.get(clazz);
				if (propertyFilter != null)
				{
					log.debug("encountered property-filter for class/interface=[{}] in bean hierarchy, adding to list...", clazz);
					propertyFilters.add(propertyFilter);
				}
			}

			BeanWrapper beanWrapper = new BeanWrapperImpl(beanClass);
			List<PropertyDescriptor> propertyDescriptors = Arrays.asList(beanWrapper.getPropertyDescriptors());
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors)
			{
				PropertyStatus cumulativePropertyStatus = new PropertyStatus();
				String propertyName = propertyDescriptor.getName();
				log.debug("processing property=[{}]", propertyName);

				for (PropertyFilter propertyFilter : propertyFilters)
				{
					PropertyStatus propertyStatus = propertyFilter.getPropertyStatus(propertyName);
					cumulativePropertyStatus.setIncluded(propertyStatus.isIncluded());
					cumulativePropertyStatus.setExcluded(propertyStatus.isExcluded());
					if (propertyStatus.isExcluded())
					{
						log.debug("property=[{}] excluded by property-filter for bean-class=[{}]", propertyName, propertyFilter.getBeanClass());
						break;
					}
				}

				if (cumulativePropertyStatus.isIncluded() && !cumulativePropertyStatus.isExcluded())
				{
					log.debug("property=[{}] included, adding to included-properties", propertyName);
					includedProperties.add(propertyName);
				}
			}

			log.debug("caching included-properties=[{}] for bean-class=[{}]", includedProperties, beanClass);
			includedPropertiesMap.put(beanClass, includedProperties);
		}

		return includedProperties;
	}

	public void process(Class<?> beanClass, PropertyCallback propertyCallback)
	{
		log.debug("processing bean with class=[{}]", beanClass);

		Set<String> includedProperties = getIncludedProperties(beanClass);

		for (String propertyName : includedProperties)
		{
			log.debug("property=[{}] found in included-properties, calling doWithProperty", propertyName);
			propertyCallback.doWithProperty(propertyName);
		}
	}

	public void setPropertyFilters(Set<PropertyFilter> propertyFilters)
	{
		for (PropertyFilter propertyFilter : propertyFilters)
		{
			addPropertyFilter(propertyFilter);
		}
	}
}
