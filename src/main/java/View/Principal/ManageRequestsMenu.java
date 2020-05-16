package View.Principal;

import Control.Manager;
import Control.Principal.ManageRequestsManager;
import Models.Shop.Request.Request;
import View.ErrorProcessor;
import View.Menu;

import java.util.regex.Matcher;

public class ManageRequestsMenu extends Menu {
    public ManageRequestsMenu(Manager manager) {
        super(manager);
        manageRequestsMenu();
    }

    private void manageRequestsMenu() {
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if ((matcher = getMatcher(input, "^details (\\S+)$")).find()) {
                showRequestDetails(matcher.group(1));
            } else if ((matcher = getMatcher(input, "^accept (\\S+)$")).find()) {
                acceptRequest(matcher.group(1));
            } else if ((matcher = getMatcher(input, "^decline (\\S+)$")).find()) {
                declineRequest(matcher.group(1));
            } else if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^back$").find()) {
                return;
            } else ErrorProcessor.invalidInput();
        }
    }

    private void showRequestDetails(String id) {
        if (Request.hasRequestById(id)) {
            System.out.println(((ManageRequestsManager) manager).showRequestDetails(id));
        } else ErrorProcessor.invalidRequestId();
    }

    private void acceptRequest(String id) {
        if (Request.hasRequestById(id)) {
            // TODO
        } else ErrorProcessor.invalidRequestId();
    }

    private void declineRequest(String id) {
        if (Request.hasRequestById(id)) {
            // TODO
        } else ErrorProcessor.invalidRequestId();
    }

    private void help() {
        System.out.println("details [requestId]\n" +
                "accept [requestId]\n" +
                "decline [requestId]\n" +
                "help\n" +
                "back");
    }
}
