package Client.Control.RequestProcessor;

import Client.Control.UserPanel.LoginToExistingAccountManager;
import Models.Account.Account;
import Models.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestProcessor {

    public static String processRequest(String request) {
        Matcher matcher;
        String response = null;
        if ((matcher = getMatcher(request, "LOGIN (\\S+) (\\S+)")).find()) {
            response = login(matcher);
        } else if ((matcher = getMatcher(request, "GET_ACCOUNT (\\S+)")).find()) {
            response = getAccount(matcher);
        } else if (getMatcher(request, "GET_ALL_PRINCIPALS").find()) {
           // TODO
        } else if (getMatcher(request, "GET_ALL_CUSTOMERS").find()) {
           // TODO
        } else if (getMatcher(request, "GET_ALL_SELLERS").find()) {
           // TODO
        } else if (getMatcher(request, "GET_ALL_DISCOUNTS").find()) {
           // TODO
        } else if (getMatcher(request, "GET_ALL_REQUESTS").find()) {
           // TODO
        }
        return response;
    }

    private static String getAccount(Matcher matcher) {
        return Gson.INSTANCE.get().toJson(Account.getAccountByUsername(matcher.group(1)));
    }

    private static String login(Matcher matcher) {
        LoginToExistingAccountManager l = new LoginToExistingAccountManager(null);
        return l.login(matcher.group(1), matcher.group(2));
    }



    private static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
