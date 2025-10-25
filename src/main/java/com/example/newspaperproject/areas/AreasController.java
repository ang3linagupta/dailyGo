package com.example.newspaperproject.areas;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.example.newspaperproject.jdbcc.MysqlDBConnection;
import java.sql.PreparedStatement;
import javafx.scene.control.TextField;

public class AreasController {

    Connection con;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtFillArea;


    @FXML
    void doSaveArea(ActionEvent event) {

        String query = "insert into areas values(?)";
        try {
            PreparedStatement pst = con.prepareStatement(query);

            String area = txtFillArea.getText();

            if (area.length() == 0) {
                System.out.println("Please enter an area name.");
                return;
            }

            pst.setString(1, area);

            int count = pst.executeUpdate();
            if (count > 0) {
                System.out.println("Area saved successfully.");
            } else {
                System.out.println("Area not saved.");
            }
        }

        catch (Exception exp) {
            System.out.println(exp);
            exp.printStackTrace();
        }

    }

    @FXML
    void initialize() {
        con= MysqlDBConnection.getMySQLDBConnection();
        if(con==null)
        {
            System.out.println("Connection Error");
            return;
        }
    }

}
