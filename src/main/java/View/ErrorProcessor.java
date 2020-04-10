package View;

public class ErrorProcessor {
    public void wrongPassword(){
        System.out.println("Wrong password");
    }
    public void wrongUsername(){
        System.out.println("Wrong username");
    }
    public void invalidInput(){
        System.out.println("Invalid input");
    }
    public void invalidEditField(){
        System.out.println("You can't change this field");
    }
    public void invalidProductId() {
        System.out.println("Wrong product id");
    }
    public void invalidDiscountId() {
        System.out.println("Wrong discount id");
    }
    public void invalidCategoryName() {
        System.out.println("Wrong Category Name");
    }
    public void invalidRequestId() {
        System.out.println("Wrong request id");
    }
    public void noCustomerWithUsername() {
        System.out.println("Selected username doesn't belong to a customer");
    }
    public void invalidAuctionId(){System.out.println("Wrong off id");}
}
