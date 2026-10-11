package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class InsertOfSmartEquipmentDidFailSomewhereException extends RuntimeException {
    public InsertOfSmartEquipmentDidFailSomewhereException() {
        String message = "Insert of smart equipment in repository failed somewhere";
        super(message);
    }
}
