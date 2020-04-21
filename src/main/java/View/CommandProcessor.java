package View;

import Control.Manager;

import java.util.Scanner;
import java.util.regex.Matcher;

public class CommandProcessor {
    protected Manager manager;
    protected String command;
    protected Scanner scanner = new Scanner(System.in);

    public CommandProcessor(Manager manager) {
        this.manager = manager;
    }

    protected Matcher getMatcher(String input, String regex) {

    }
}
