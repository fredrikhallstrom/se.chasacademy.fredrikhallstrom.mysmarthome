package se.chasacademy.fredrikhallstrom.mysmarthome.exception;

public class SearchSortingTypeCantBeNullException extends RuntimeException {
    public SearchSortingTypeCantBeNullException() {
        String message = "Search sorting type can't be null, use NONE for no sorting";
        super(message);
    }
}
