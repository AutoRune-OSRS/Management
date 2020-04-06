package io.autorune.osrs.api.user;

import io.autorune.osrs.api.Client;
import java.lang.Comparable;

public interface BasicUser extends Comparable {
	Client getClientInstance();
}
