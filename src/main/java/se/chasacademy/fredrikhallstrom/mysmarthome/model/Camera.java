package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.Clock;
import java.time.LocalDateTime;

public class Camera extends SmartEquipment implements Recordable, Lightable {
    private boolean cameraLightState;

      public Camera(
            String name,
            LocalDateTime installationTime, Clock clock) {
        super(name, installationTime, clock);
    }

    @Override
    public String getStateString() {
          String statusMessageString;
          if (isStateActive) {
              statusMessageString = recordOn();
          } else {
              statusMessageString = recordOff();
          }

          statusMessageString += ", ";

          if (getCameraLightState()) {
              statusMessageString += lightOn();
          } else {
              statusMessageString += lightOff();
          }

          return statusMessageString;
    }

    @Override
    public String setStateActive() {
        super.setStateActive();
        return recordOn();
    }

    @Override
    public String setStateInactive() {
        super.setStateInactive();
        return recordOff();
    }

    public String setCameraLightOn() {
          cameraLightState = true;
          return lightOn();
    }

    public String setCameraLightOff() {
          cameraLightState = false;
          return lightOff();
    }

    public String getCameraLightStateString() {
          if (cameraLightState) {
              return lightOn();
          } else {
              return lightOff();
          }
    }

    public boolean getCameraLightState() {
          return cameraLightState;
    }
}
