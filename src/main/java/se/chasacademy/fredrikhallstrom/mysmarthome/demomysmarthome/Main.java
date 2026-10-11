package se.chasacademy.fredrikhallstrom.mysmarthome.demomysmarthome;

import java.time.Clock;

public class Main  {
    void main (String[] args) {
        Clock clock = Clock.systemUTC();
        DemoMySmartHome demoMySmartHome = new DemoMySmartHome(clock);
        demoMySmartHome.run();
    }
}
