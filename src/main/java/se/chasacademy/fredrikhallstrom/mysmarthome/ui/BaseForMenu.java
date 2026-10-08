package se.chasacademy.fredrikhallstrom.mysmarthome.ui;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.ui.MenuBuildErrorException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.ui.MenuChoiceMustBeANumberException;

import java.util.Map;

public abstract class BaseForMenu implements Menu {
    protected Map<String, Runnable> menuItemsMap;

    protected void menu(Map<String, Runnable> menuItemsMap) {
        this.menuItemsMap = menuItemsMap;
        boolean running = true;
        while (running) {
            String line = menuItemsMap.keySet().iterator().next() +
                    "\n" +
                    "*".repeat(menuItemsMap.keySet().iterator().next().length()) +
                    "\n";

            IO.println(line);

            int menuIndex = 0;
            for (Map.Entry<String, Runnable> entryOfKeyAndValue : menuItemsMap.entrySet() ) {
                if (menuIndex == 0) {
                    menuIndex++;
                } else {
                    IO.println(String.format("%d. %s", menuIndex, entryOfKeyAndValue.getKey()));
                    menuIndex++;
                }

            }
            IO.println(String.format("%d. Avsluta/Återgå", menuItemsMap.size()));

            running = menuChoiceHelper();

        }
    }

    private boolean menuChoiceHelper() throws MenuChoiceMustBeANumberException, MenuBuildErrorException {
        // return boolean controls exit/return to previous menu

        String choiceString = IO.readln("\nMake a menu choice: ");
        int choiceInt;

        if (choiceString.isBlank()) {
            return true;
        }

        try {
            choiceInt = Integer.parseInt(choiceString);
        } catch (NumberFormatException e) {
            throw new MenuChoiceMustBeANumberException();
        }

        if (choiceInt == menuItemsMap.size()) {
            return false;
        }

        int menuIndex = 0;
        for (Runnable menuRunnable : menuItemsMap.values()) {
            if (menuIndex == choiceInt) {
                menuRunnable.run();
                return true;
            } else {
                menuIndex++;
            }
        }

        throw new MenuBuildErrorException(menuItemsMap.toString());
    }
}
