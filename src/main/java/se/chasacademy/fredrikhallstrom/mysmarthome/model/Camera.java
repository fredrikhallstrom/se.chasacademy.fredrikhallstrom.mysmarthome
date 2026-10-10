package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantActiveAlreadyActivatedStateException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.CantDeactivateAlreadyDeactivatedStateException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Camera extends SmartEquipment implements Recordable, Lightable, ExtraStateControllable {
    private boolean cameraLightState;

    public Camera(
            String name,
            LocalDateTime installationTime) {
        super(name, installationTime);
    }

    @Override
    public List<String> getStateMessages() {
        List<String> states = new ArrayList<>();

        if (isStateActive) {
            states.add(recordOn());
        } else {
            states.add(recordOff());
        }

        if (cameraLightState) {
            states.add(lightOn());
        } else {
            states.add(lightOff());
        }

        return states;
    }

    @Override
    public String setStateActive(LocalDateTime activationTime) {
        super.setStateActive(activationTime);
        return recordOn();
    }

    @Override
    public String setStateInactive(LocalDateTime deactivationTime) {
        super.setStateInactive(deactivationTime);
        return recordOff();
    }

    @Override
    public String setExtraStateOn() throws CantActiveAlreadyActivatedStateException {
        if (cameraLightState) {
            throw new CantActiveAlreadyActivatedStateException();
        }

        cameraLightState = true;
        return lightOn();
    }

    @Override
    public String setExtraStateOff() throws CantDeactivateAlreadyDeactivatedStateException {
        if (!cameraLightState) {
            throw new CantDeactivateAlreadyDeactivatedStateException();
        }

        cameraLightState = false;
        return lightOff();
    }

    @Override
    public String getExtraStateString() {
        if (cameraLightState) {
            return lightOn();
        } else {
            return lightOff();
        }
    }
}
