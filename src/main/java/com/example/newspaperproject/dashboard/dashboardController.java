package com.example.newspaperproject.dashboard;
import com.example.newspaperproject.jdbcc.MysqlDBConnection;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class dashboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    void onclickareamaster(ActionEvent event) {

        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/com/example/newspaperproject/areas/AreasView.fxml"));
            Scene scene = new Scene(fxmlLoader, 550, 450);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.setTitle("AREA MASTER");
            System.out.println("Successfully opened");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void onclickbillboardmanger(ActionEvent event) {

        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/com/example/newspaperproject/billboard/billboardView.fxml"));
            Scene scene = new Scene(fxmlLoader, 610, 538);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.setTitle("BILL BOARD");
            System.out.println("Successfully opened");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void onclickcustomerboard(ActionEvent event) {

        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/com/example/newspaperproject/customerboard/customerboardView.fxml"));
            Scene scene = new Scene(fxmlLoader, 730, 444);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.setTitle("CUSTOMER BOARD");
            System.out.println("Successfully opened");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void onclickcustomermanager(ActionEvent event){

        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/com/example/newspaperproject/customers/CustomersView.fxml"));
            Scene scene = new Scene(fxmlLoader, 750, 570);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.setTitle("CUSTOMER ENROLLMENT");
            System.out.println("Successfully opened");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void onclickgeneratebill(ActionEvent event) {

        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/com/example/newspaperproject/billing/billingView.fxml"));
            Scene scene = new Scene(fxmlLoader, 645, 550);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.setTitle("BILLING PAGE");
            System.out.println("Successfully opened");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void onclickhawkerconsole(ActionEvent event) {

        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/com/example/newspaperproject/hawkers/HawkersView.fxml"));
            Scene scene = new Scene(fxmlLoader, 600, 520);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.setTitle("HAWKER CONSOLE");
            System.out.println("Successfully opened");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void onclickpapermaster(ActionEvent event) {

        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/com/example/newspaperproject/papermaster/PaperMasterView.fxml"));
            Scene scene = new Scene(fxmlLoader, 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.setTitle("PAPER MASTER");
            System.out.println("Successfully opened");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    @FXML
    void onclickpaybill(ActionEvent event) {

        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/com/example/newspaperproject/billcollector/billcollectorView.fxml"));
            Scene scene = new Scene(fxmlLoader, 598, 430);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.setTitle("BILL COLLECTOR");
            System.out.println("Successfully opened");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    Connection con;
    @FXML
    void initialize() {

        con= MysqlDBConnection.getMySQLDBConnection();
        if(con==null)
        {
            System.out.println("Connection Error");
            return;
        }else{
            System.out.println("Connection Established");
        }

    }

}
