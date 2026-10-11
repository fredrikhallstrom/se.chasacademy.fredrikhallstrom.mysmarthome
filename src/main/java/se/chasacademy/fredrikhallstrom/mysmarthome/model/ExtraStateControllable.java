package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedStateException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedStateException;

public interface ExtraStateControllable {
    String setExtraStateOn() throws CantActiveAlreadyActivatedStateException;
    String setExtraStateOff() throws CantDeactivateAlreadyDeactivatedStateException;
    String getExtraStateString();
}
