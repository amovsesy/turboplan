package edu.calpoly.csc.luna.turboplan.core.modelconverter;

import edu.calpoly.csc.luna.turboplan.core.entity.BaseEntity;

public interface EntityModelConverter<T extends BaseEntity> {
	public T toEntityModel();
}
