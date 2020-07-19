package Bank;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

public class Token {

    private String token;
    private LocalTime time;
    private Account account;
    private static ArrayList<Token> tokens = new ArrayList<>();

    public Token(Account account) {
        this.account = account;
        this.token = UUID.randomUUID().toString();
        this.time = LocalTime.now();
        tokens.add(this);
    }

    public String getToken() {
        return token;
    }

    public static boolean isThereToken(String token) {
        for (Token token1 : tokens) {
            if (token1.token.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public Account getAccount() {
        return account;
    }

    public static Token getToken(String token) {
        for (Token token1 : tokens) {
            if (token1.token.equals(token)) {
                return token1;
            }
        }
        return null;
    }

    public boolean isExpired() {
        if (LocalTime.now().minusHours(1).isBefore(time)) {
            return false;
        }
        return true;
    }
}
