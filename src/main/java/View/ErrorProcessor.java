package View;

public class ErrorProcessor {
    public static void wrongPassword() {
        System.out.println("Wrong password");
    }

    public static void userExistsWithThisUsername() {
        System.out.println("User exists with this username");
    }

    public static void wrongUsername() {
        System.out.println("Wrong username");
    }

    public static void principalExists() {
        System.out.println("You can't register as a new principal.");
    }

    public static void cantDeleteYourAccount() {
        System.out.println("You can't delete your account.");
    }

    public static void invalidInput() {
        System.out.println("Invalid input");
    }

    public static void invalidType() {
        System.out.println("Invalid type. valid types are (customer|seller|principal)");
    }

    public static void invalidEditField() {
        System.out.println("You can't change this field");
    }

    public static void invalidEnteredInEditField() {
        System.out.println("What you have entered is not suitable for this field");
    }
    public static void invalidProductId() {
        System.out.println("Wrong product id");
    }
    public static void invalidDiscountId() {
        System.out.println("Wrong discount id");
    }
    public static void invalidCategoryName() {
        System.out.println("Wrong Category Name");
    }
    public static void invalidRequestId() {
        System.out.println("Wrong request id");
    }
    public static void noCustomerWithUsername() {
        System.out.println("Selected username doesn't belong to a customer");
    }
    public static void invalidAuctionId() {
        System.out.println("Wrong off id");
    }
    public static void notEnoughMoney() {
        System.out.println("You don't have enough money to purchase");
    }
    public static void notYourProduct() {
        System.out.println("this is not your product");
    }

    public static void somethingWentWrong() {
        System.out.println("Oops something went wrong");
    }

    public static void invalidPhoneNumber() {
        System.out.println("Invalid phone number");
    }

    public static void emptyCart() {
        System.out.println("there is nothing to purchase select a product first");
    }
}
