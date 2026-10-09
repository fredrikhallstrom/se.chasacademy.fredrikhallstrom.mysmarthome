package se.chasacademy.fredrikhallstrom.mysmarthome.ui;

import se.chasacademy.fredrikhallstrom.mysmarthome.exception.ui.MenuChoiceNumberNotFoundException;
import se.chasacademy.fredrikhallstrom.mysmarthome.exception.ui.MenuChoiceMustBeANumberException;

import java.util.List;
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
            for (Map.Entry<String, Runnable> entryOfKeyAndValue : menuItemsMap.entrySet()) {
                if (menuIndex == 0) {
                    menuIndex++;
                } else {
                    IO.println(String.format("%d. %s", menuIndex, entryOfKeyAndValue.getKey()));
                    menuIndex++;
                }

            }
            IO.println(String.format("%d. Exit/Return", menuItemsMap.size()));

            try {
                running = menuChoiceHelper();
            } catch (MenuChoiceMustBeANumberException | MenuChoiceNumberNotFoundException error) {
                IO.println(error.getMessage() + "\n");
            }

        }
    }

    private boolean menuChoiceHelper() throws MenuChoiceMustBeANumberException, MenuChoiceNumberNotFoundException {
        // return boolean controls exit/return to previous menu

        String choiceString = IO.readln("\nMake a menu choice: ");
        IO.println("");

        int choiceInt;

        if (choiceString == null) {
            return false;
        }

        if (choiceString.isBlank()) {
            IO.println("Can't be blank! Try again!\n");
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

        if (choiceInt < 1 || choiceInt > menuItemsMap.size()) {
            throw new MenuChoiceNumberNotFoundException();
        }

        List<Runnable> menuActions = List.copyOf(menuItemsMap.values());
        menuActions.get(choiceInt).run();
        return true;
    }
}
