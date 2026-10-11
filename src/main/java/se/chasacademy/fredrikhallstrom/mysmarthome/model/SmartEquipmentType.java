package se.chasacademy.fredrikhallstrom.mysmarthome.model;

public enum SmartEquipmentType {
    LAMP("Lamp", Lamp::new),
    RADIATOR("Radiator", Radiator::new),
    CAMERA("Camera", Camera::new),
    ;

    private final String name;
    private final SmartEquipmentCreate creator;

    SmartEquipmentType(String name, SmartEquipmentCreate creator) {
        this.name = name;
        this.creator = creator;
    }

    public String getDisplayName() {
        return name;
    }

    public SmartEquipmentCreate getCreator() {
        return creator;
    }
}
