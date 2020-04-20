package View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ConsoleCommand {
    EXIT("(?i)exit");

    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);
    }

    ConsoleCommand(String commandPatternString) {
        this.commandPattern = Pattern.compile(commandPatternString);
    }
}