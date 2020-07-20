package Bank;

import java.time.LocalTime;
import java.util.*;

public class Token {

    private String token;
    private Timer timer;
    private Account account;
    private boolean expired;
    private static ArrayList<Token> tokens = new ArrayList<>();

    public Token(Account account) {
        this.account = account;
        this.token = UUID.randomUUID().toString();
        this.expired = false;
        this.timer = new Timer();
        Date date = new Date();
        date.setTime(System.currentTimeMillis() + 60 * 60 * 1000);
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setExpired();
            }
        }, date);
        tokens.add(this);
    }

    private void setExpired() {
        expired = true;
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
        return expired;
    }
}
