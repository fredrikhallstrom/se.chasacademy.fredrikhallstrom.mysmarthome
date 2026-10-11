package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.demonstration;

public class MenuDemonstration {
    public void menu() {

        IO.println("""
                Welcome to the demonstration menu!
                This menu will demonstrate the functionality your MySmartHome.
                Write "YES" exactly to continue...""");

        String confirmDemo = IO.readln("> ");

        if (confirmDemo.equals("YES")) {
            IO.println("\nStarting demonstration...\n");


        } else {
            IO.println("\nAborting demonstration...\n");
        }
    }
}
