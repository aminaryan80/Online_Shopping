package ViewController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    @FXML
    private GridPane comments;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            AnchorPane comment = FXMLLoader.load(getClass().getResource("../view/products/Comment.fxml"));
            AnchorPane comment2 = FXMLLoader.load(getClass().getResource("../view/products/Comment.fxml"));
            comments.add(comment, 0, 0);
            comments.add(comment2, 0, 1);
            comments.setPrefHeight(500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
