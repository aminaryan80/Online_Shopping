package Client.ViewController.principal.manageCategories;

import Client.Control.Principal.ManageCategories.ManageCategoriesManager;
import Client.ViewController.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

public class ManageCategoriesController extends Controller {

    @FXML
    private TextField categoryNameField;
    @FXML
    private TreeView<String> categoriesTableView;

    public void init() {
        categoriesTableView.setRoot(manager.getCategoriesInTable());
    }

    public void addCategory(ActionEvent actionEvent) {
        ((ManageCategoriesManager) manager).openAddCategory(categoryNameField.getText());
    }

    public void editCategory(ActionEvent actionEvent) {
        ((ManageCategoriesManager) manager).openEditCategory(categoryNameField.getText());
    }

}
