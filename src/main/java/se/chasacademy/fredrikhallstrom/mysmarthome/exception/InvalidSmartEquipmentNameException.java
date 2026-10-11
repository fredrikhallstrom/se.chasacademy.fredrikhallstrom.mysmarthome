package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class InvalidSmartEquipmentNameException extends RuntimeException {
    public InvalidSmartEquipmentNameException(String smartEquipmentName) {
        String message = String.format("""
                   
                   Name ->
                   Invalid name: "%s"
                   \tCan't start with a number
                   \tCan't have same name as another SmartEquipment
                   \tCan't have same name as type
                   \tCan only: contain letters, numbers and spaces
                   \tMust not be blank""", smartEquipmentName);
        super(message);
    }
}
