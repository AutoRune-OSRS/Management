package io.autorune.osrs.api.system;

import io.autorune.osrs.api.Client;
import io.autorune.osrs.api.collection.Node;
import java.lang.String;

public interface MachineInfo extends Node {
	int getOsType();

	void setOsType(int value);

	boolean getIsOS64Bit();

	void setIsOS64Bit(boolean value);

	int getOsVersion();

	void setOsVersion(int value);

	int getJavaVendor();

	void setJavaVendor(int value);

	int getJavaVersionMajor();

	void setJavaVersionMajor(int value);

	int getJavaVersionMinor();

	void setJavaVersionMinor(int value);

	int getJavaVersionBuild();

	void setJavaVersionBuild(int value);

	boolean getUsingConsole();

	void setUsingConsole(boolean value);

	int getMaxMemory();

	void setMaxMemory(int value);

	int getAvailableProcessors();

	void setAvailableProcessors(int value);

	int getAvailableMemory();

	void setAvailableMemory(int value);

	int getProcessorClockSpeed();

	void setProcessorClockSpeed(int value);

	String getGraphicsString1();

	void setGraphicsString1(String value);

	String getGraphicsString2();

	void setGraphicsString2(String value);

	String getGraphicsString3();

	void setGraphicsString3(String value);

	String getGraphicsString4();

	void setGraphicsString4(String value);

	int getGraphicsDriverYear();

	void setGraphicsDriverYear(int value);

	int getGraphicsDriverMonth();

	void setGraphicsDriverMonth(int value);

	String getProcessorVendor();

	void setProcessorVendor(String value);

	String getProcessorBrandString();

	void setProcessorBrandString(String value);

	int getProcessorCount();

	void setProcessorCount(int value);

	int getProcessorBrandId();

	void setProcessorBrandId(int value);

	int[] getProcessorFeatures();

	void setProcessorFeatures(int[] value);

	int getProcessorModel();

	void setProcessorModel(int value);

	String getUnknownString();

	void setUnknownString(String value);

	Client getClientInstance();
}
