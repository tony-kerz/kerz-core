package com.kerz.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Widget extends AbstractPersistable<Long>
{
  private static final long serialVersionUID = 1375650825937082356L;

	@Min(value = 0)
	@Max(value = 1000)
	private BigDecimal cost;
	
	@Size(min = 3)
	private String name;
	
//	@Id
//	@GeneratedValue
//	private Long m_oid;
	
	public BigDecimal getCost()
	{
		return cost;
	}
	
	public String getName()
	{
		return name;
	}
	
//	public Long getOid()
//	{
//		return m_oid;
//	}
	
	public void setCost(BigDecimal cost)
	{
		this.cost = cost;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
//	public void setOid(Long oid)
//	{
//		m_oid = oid;
//	}
	
//	@Override
//	public String toString()
//	{
//		return ToStringBuilder.reflectionToString(this);
//	}
}
