package com.iluwatar.servicelayer.common;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * 
 * Base class for entities.
 *
 */
@MappedSuperclass
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class BaseEntity {
	
	@Version
	private Long version;
}
