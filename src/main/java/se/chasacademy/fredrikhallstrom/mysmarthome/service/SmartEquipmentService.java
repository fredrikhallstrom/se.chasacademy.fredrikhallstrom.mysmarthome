package se.chasacademy.fredrikhallstrom.mysmarthome.service;

import se.chasacademy.fredrikhallstrom.mysmarthome.model.SmartEquipment;
import se.chasacademy.fredrikhallstrom.mysmarthome.repository.SmartEquipmentRepository;

import java.time.Clock;

public class SmartEquipmentService {
    private final SmartEquipmentRepository smartEquipmentRepository;

    public SmartEquipmentService(SmartEquipmentRepository smartEquipmentRepository, Clock clock) {
        this.smartEquipmentRepository = smartEquipmentRepository;
    }

    public SmartEquipment addSmartEquipment(SmartEquipment smartEquipment) {
        return null;
    }

}
