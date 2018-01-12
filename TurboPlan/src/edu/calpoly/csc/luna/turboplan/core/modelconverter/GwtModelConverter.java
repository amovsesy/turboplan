package edu.calpoly.csc.luna.turboplan.core.modelconverter;

import edu.calpoly.csc.luna.turboplan.web.client.model.GwtModel;

public interface GwtModelConverter<T extends GwtModel> {
	public T convert2GwtModel();
}
