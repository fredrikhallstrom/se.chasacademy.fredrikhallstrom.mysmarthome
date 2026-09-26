package se.chasacademy.fredrikhallstrom.mysmarthome.model;

import java.time.LocalDateTime;

public class Camera extends SmartEquipment {
    private String cameraName;
    private LocalDateTime installationTime;
    private LocalDateTime lastActivationTime;
    private LocalDateTime lastDeactivationTime;
    private boolean isStateActive = false;

    public Camera(
            String cameraName,
            LocalDateTime installationTime) {

        this.cameraName = cameraName;
        this.installationTime = installationTime;

        super(cameraName, installationTime);
    }
}
