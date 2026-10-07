package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class CreationOfSmartEquipmentDidFailSomewhereException extends RuntimeException {
    public CreationOfSmartEquipmentDidFailSomewhereException() {
        String message = "Something went wrong when creation of a SmartEquipment";
        super(message);
    }
}
