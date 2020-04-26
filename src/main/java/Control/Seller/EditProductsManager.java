package Control.Seller;

import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.Category;
import Models.Shop.Product;
import Models.Shop.SellingLog;

import java.util.ArrayList;

public class EditProductsManager extends SellerManager {
    public EditProductsManager(Account account) {
        super(account);
    }

    public String viewProductDetails(String id) {
        return Product.getProductById(id).toString();
    }

    public ArrayList<String> viewProductBuyers(String id) {
        ArrayList<SellingLog> allLogs = ((Seller) account).getAllLogs();
        ArrayList<String> allBuyers = new ArrayList<String>();
        for (SellingLog log : allLogs) {
            allBuyers.add(log.getBuyerName());
        }
        return allBuyers;
    }

    public boolean isEnteredProductFieldValid(String field) {
        if (field.equals("status") || field.equals("name") || field.equals("companyName") || field.equals("price")
                || field.equals("seller") || field.equals("isAvailable") || field.equals("category")
                || field.equals("description") || field.equals("features")) {
            return true;
        }
        return false;
    }

    public boolean hasProductWithId(String id) {
        return Product.hasProductWithId(id);
    }

    public Product editProduct(String id, String field, String newValue) {
        Product product = Product.getProductById(id);
        if (field.equals("name")) {
            product.setName(newValue);
        } else if (field.equals("companyName")) {
            product.setCompanyName(newValue);
        } else if (field.equals("description")) {
            product.setDescription(newValue);
        } else if (field.equals("seller")) {
            product.setSeller((Seller) Seller.getAccountByUsername(newValue));
        } else if (field.equals("isAvailable")) {
            product.setAvailable(Boolean.parseBoolean(newValue));
        } else if (field.equals("price")) {
            product.setPrice(Double.parseDouble(newValue));
        } else if (field.equals("category")) {
            product.setCategory(Category.getCategoryByName(newValue));
        } else {
            //TODO introducing feature class
        }
        product.setStatus(Product.ProductStatus.UNDER_REVIEW_FOR_EDITING);
        return product;
    }
}
