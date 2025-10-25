package com.example.newspaperproject.admin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class adminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblStatus;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void handlelogin(ActionEvent event) {

        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.equals("Admin") && password.equals("Admin@123")) {
            try {
                Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/com/example/newspaperproject/dashboard/dashboardView.fxml"));
                Scene scene = new Scene(fxmlLoader, 860, 530);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                stage.setTitle("DASHBOARD");
                System.out.println("Successfully opened");
                ((Stage) txtUsername.getScene().getWindow()).close();

            } catch (Exception e) {
                e.printStackTrace();
                lblStatus.setText("Failed to load dashboard.");
            }
        } else {
            lblStatus.setText("Invalid username or password.");
        }

    }

    @FXML
    void initialize() {
         }

}
