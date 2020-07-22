package Client.ViewController.products;

import Client.ViewController.Controller;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewCategoriesController extends Controller implements Initializable {

    public TreeView<String> categoriesTreeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoriesTreeView.setRoot(getCategoriesInTable());
    }
}
