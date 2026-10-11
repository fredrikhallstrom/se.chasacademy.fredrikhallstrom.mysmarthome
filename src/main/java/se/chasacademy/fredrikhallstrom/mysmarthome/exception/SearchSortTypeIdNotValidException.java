package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class SearchSortTypeIdNotValidException extends RuntimeException {
    public SearchSortTypeIdNotValidException() {
        String message = "Invalid sorting type id";
        super(message);
    }
}
