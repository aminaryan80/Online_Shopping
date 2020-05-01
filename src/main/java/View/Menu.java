package View;

import Control.Manager;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
    protected Manager manager;
    protected static Scanner scanner = new Scanner(System.in);

    public Menu(Manager manager) {
        this.manager = manager;
    }

    protected Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
