package edu.calpoly.csc.luna.turboplan.core.modelconverter;

import edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model.MobileModel;

public interface MobileModelConverter<T extends MobileModel> {
	public T convert2MobileModel();
}
