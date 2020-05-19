package View.Seller;

import Control.Manager;
import Control.Seller.OffsManager;
import Models.Account.Seller;
import Models.Shop.Off.Auction;
import Models.Shop.Request.AddOffRequest;
import Models.Shop.Request.EditOffRequest;
import View.ErrorProcessor;
import View.Menu;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class OffsMenu extends Menu {
    public OffsMenu(Manager manager) {
        super(manager);
        offsMenu();
    }

    private void offsMenu() {
        Matcher matcher;
        while (true) {
            String command = scanner.nextLine();
            if ((matcher = getMatcher(command, "^view (\\ُS+)$")).find()) {
                viewOffById(matcher.group(1));
            } else if ((matcher = getMatcher(command, "^edit (\\S+)$")).find()) {
                editOffAttribute(matcher.group(1));
            } else if ((matcher = getMatcher(command, "^add off$")).find()) {
                addOff();
            } else if ((matcher = getMatcher(command, "^back$")).find()) {
                return;
            } else if ((matcher = getMatcher(command, "^help$")).find()) {
                help();
            } else {
                ErrorProcessor.invalidInput();
            }
        }
    }

    private void viewOffById(String id) {
        if (!((OffsManager) manager).isEnteredIdValid(id)) {
            ErrorProcessor.invalidAuctionId();
            return;
        }
        System.out.println(((OffsManager) manager).viewOffById(id));
    }

    private void editOffAttribute(String id) {
        if (!((OffsManager) manager).isEnteredIdValid(id)) {
            ErrorProcessor.invalidAuctionId();
            return;
        }
        System.out.println("enter the field you want to change");
        String field = scanner.nextLine();
        if (!((OffsManager) manager).isOffFieldValid(field)) {
            ErrorProcessor.invalidEditField();
            return;
        }
        System.out.println("enter the new value"); //yyyy-MM-dd for LocalDate
        String newValue = scanner.nextLine();
        Auction auction = ((OffsManager) manager).editOffAttribute(id, field, newValue);
        new EditOffRequest((Seller) manager.getAccount(), auction);
    }

    private void addOff() {
        System.out.println("enter off's beginningDate");
        String beginningDate = scanner.nextLine();
        System.out.println("enter off's endingDate");
        String endingDate = scanner.nextLine();
        System.out.println("enter off's amount");
        double discountAmount = scanner.nextDouble();
        ArrayList<String> products = new ArrayList<String>();
        System.out.println("enter off's products Ids(end to finish)");
        while (true) {
            String productId = scanner.nextLine();
            if (productId.equals("end")) {
                break;
            } else if (!((OffsManager) manager).hasProductWithId(productId)){
                ErrorProcessor.invalidProductId();
            } else if (((OffsManager) manager).hasAuction(productId)) {
                System.out.println("this product already has an auction");
            } else {
                products.add(productId);
            }
        }
        Auction auction = ((OffsManager) manager).addOff(beginningDate, endingDate, discountAmount, products);
        new AddOffRequest((Seller) manager.getAccount(), auction);
    }

    private void help() {
        System.out.println("view [off id]%n" +
                "edit [off id]%n" +
                "add off%n" +
                "back%n" +
                "help");
    }
}
