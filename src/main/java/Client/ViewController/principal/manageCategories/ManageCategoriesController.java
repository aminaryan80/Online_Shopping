package Client.ViewController.principal.manageCategories;

import Client.Control.Manager;
import Client.Control.Principal.ManageCategories.ManageCategoriesManager;
import Client.ViewController.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageCategoriesController extends Controller implements Initializable {

    protected static String categoryName;
    public TextField categoryNameField;
    public TreeView<String> categoriesTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialize();
    }

    private void initialize() {
        categoriesTableView.setRoot(getCategoriesInTable());
    }

    public void addCategory(ActionEvent actionEvent) {
        categoryName = categoryNameField.getText();
        loadFxml(Manager.Addresses.ADD_CATEGORY, true);
    }

    public void editCategory(ActionEvent actionEvent) {
        ((ManageCategoriesManager) manager).openEditCategory(categoryNameField.getText());
        categoryName = categoryNameField.getText();
        loadFxml(Manager.Addresses.EDIT_CATEGORY, true);
    }

    public void back() {
        loadFxml(Manager.Addresses.PRINCIPAL_MENU);
    }
}
