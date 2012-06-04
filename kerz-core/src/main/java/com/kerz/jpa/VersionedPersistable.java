package com.kerz.jpa;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class VersionedPersistable<PK extends Serializable> extends
    org.springframework.data.jpa.domain.AbstractPersistable<PK>
{
  private static final long serialVersionUID = -1517637682073884771L;

  @Version
  private Integer version;

  public Integer getVersion()
  {
    return version;
  }
}
