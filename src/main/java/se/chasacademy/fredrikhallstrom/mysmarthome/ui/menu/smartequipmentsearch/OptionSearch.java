package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.smartequipmentsearch;

public class OptionSearch {

    public static void searchByName() {
    public void searchByName() {
        IO.print("Search by name");

        IO.print("Enter name: ");
        String name = IO.readln();
        IO.print("Search results:");
        IO.print("No results found");
    }

    public void searchByType() {
        IO.print("Search by type");
        IO.print("Enter type: ");
        String type = IO.readln();
        IO.print("Search results:");
        IO.print("No results found");
    }
}
