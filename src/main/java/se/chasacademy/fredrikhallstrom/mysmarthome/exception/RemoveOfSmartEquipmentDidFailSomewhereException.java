package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class RemoveOfSmartEquipmentDidFailSomewhereException extends RuntimeException {
    public RemoveOfSmartEquipmentDidFailSomewhereException() {
        String message = String.format("Something went wrong when trying to remove SmartEquipment");
        super(message);
    }
}
