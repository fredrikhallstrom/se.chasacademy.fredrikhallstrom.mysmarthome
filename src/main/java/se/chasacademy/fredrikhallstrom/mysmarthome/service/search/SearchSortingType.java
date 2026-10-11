package se.chasacademy.fredrikhallstrom.mysmarthome.service.search;

public enum SearchSortingType {
    ASCENDING("Ascending"),
    DESCENDING("Descending"),
    NONE("None");

    private final String friendlyName;

    SearchSortingType(String friendlyName) {
        this.friendlyName = friendlyName;
    };

    public String getFriendlyName() {
        return friendlyName;
    }
}
