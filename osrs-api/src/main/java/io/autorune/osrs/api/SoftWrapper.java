package io.autorune.osrs.api;

import java.lang.Object;
import java.lang.ref.SoftReference;

public interface SoftWrapper extends Wrapper {
	SoftReference getReference();

	void setReference(SoftReference value);

	Client getClientInstance();

	Object instance();

	boolean isSoft();
}
