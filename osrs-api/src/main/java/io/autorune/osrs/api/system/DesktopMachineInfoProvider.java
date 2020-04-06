package io.autorune.osrs.api.system;

import io.autorune.osrs.api.Client;

public interface DesktopMachineInfoProvider extends MachineInfoProvider {
	Client getClientInstance();
}
