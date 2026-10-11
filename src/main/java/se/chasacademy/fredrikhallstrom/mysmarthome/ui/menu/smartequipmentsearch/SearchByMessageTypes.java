package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentsearch;

public enum SearchByMessageTypes {
    NAME("name");

    private final String friendlyName;

    SearchByMessageTypes(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}
