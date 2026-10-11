package se.chasacademy.fredrikhallstrom.mysmarthome.ui.menu.demonstration;

import se.chasacademy.fredrikhallstrom.mysmarthome.demomysmarthome.DemoMySmartHome;

import java.time.Clock;

public class MenuDemonstration {
    private final Clock clock;

    public MenuDemonstration(Clock clock) {
        this.clock = clock;
    }

    public void menu() {
        IO.println("""
                Welcome to the demonstration menu!
                This menu will demonstrate the functionality your MySmartHome.
                Write "YES" exactly to continue...""");

        String confirmDemo = IO.readln("> ");

        if ("YES".equals(confirmDemo)) {
            IO.println("\nStarting demonstration...\n");
            DemoMySmartHome demoMySmartHome = new DemoMySmartHome(clock);
            demoMySmartHome.run();

        } else {
            IO.println("\nAborting demonstration...\n");
        }
    }
}
