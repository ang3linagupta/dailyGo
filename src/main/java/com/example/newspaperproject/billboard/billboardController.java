package com.example.newspaperproject.billboard;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.newspaperproject.jdbcc.MysqlDBConnection;
import com.example.newspaperproject.billboard.billboardBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class billboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dtfrom;

    @FXML
    private DatePicker dtto;

    @FXML
    private ComboBox<String> selcombostatus;

    @FXML
    private TableView<billboardBean> tblviewbillboard;

    @FXML
    private Label txtamnt;

    @FXML
    void dofind(ActionEvent event) {

        tblviewbillboard.getColumns().clear();

        TableColumn<billboardBean, String> mobC=new TableColumn<billboardBean, String>("MOBILE NUMBER");
        mobC.setCellValueFactory(new PropertyValueFactory<billboardBean,String>("mobilenumber"));
        mobC.setMinWidth(100);

        TableColumn<billboardBean, String> dosC=new TableColumn<billboardBean, String>("DATE OF START");
        dosC.setCellValueFactory(new PropertyValueFactory<billboardBean,String>("doss"));
        dosC.setMinWidth(100);

        TableColumn<billboardBean, String> doeC=new TableColumn<billboardBean, String>("DATE OF END");
        doeC.setCellValueFactory(new PropertyValueFactory<billboardBean,String>("doee"));
        doeC.setMinWidth(100);

        TableColumn<billboardBean, String> billC=new TableColumn<billboardBean, String>("TOTAL BILL");
        billC.setCellValueFactory(new PropertyValueFactory<billboardBean,String>("bill"));
        billC.setMinWidth(100);

        tblviewbillboard.getColumns().addAll(mobC,dosC,doeC,billC);
        tblviewbillboard.setItems(null);
        tblviewbillboard.setItems(getArrayOfObjects());

    }

    ObservableList<billboardBean> getArrayOfObjects()
    {
        String selectedStatus = selcombostatus.getValue();

        ObservableList<billboardBean> list= FXCollections.observableArrayList();
        try {
            String query = "select  mobilenumber,doss, doee, bill from billing where doss >= ? and doee <= ?";
            if (selectedStatus.equals("Paid")) {
                query += " and status = 0";
            } else if (selectedStatus.equals("Unpaid")) {
                query += " and status = 1";
            }

            PreparedStatement pst = con.prepareStatement(query);
            pst.setDate(1, Date.valueOf(dtfrom.getValue()));
            pst.setDate(2, Date.valueOf(dtto.getValue()));
            ResultSet res = pst.executeQuery();
            float billl=0;
            while (res.next()) {
                String mobilenumber=res.getString("mobilenumber");
                String doss = res.getString("doss");
                String doee = res.getString("doee");
                String bill = String.valueOf(res.getFloat("bill"));
                billboardBean bil=new billboardBean(doss,doee,bill,mobilenumber);
                list.add(bil);
                billl += res.getFloat("bill");
            }

            tblviewbillboard.setItems(list);
            txtamnt.setText("Rs " + billl);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  list;

    }

//    @FXML
//    void doexporttoexcel(ActionEvent event) {
//        try {
//
//            FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle("Save CSV File");
//            fileChooser.getExtensionFilters().add(
//                    new FileChooser.ExtensionFilter("CSV Files", "*.csv")
//            );
//
//            File selectedFile = fileChooser.showSaveDialog(null);
//
//            if (selectedFile != null) {
//
//                FileWriter writer = new FileWriter(selectedFile);
//
//
//                writer.write("MOBILE NUMBER,DATE OF START,DATE OF END ,TOTAL AMOUNT\n");
//
//
//                ObservableList<billboardBean> data = tblviewbillboard.getItems();
//                for (billboardBean bean : data) {
//                    writer.write("\"" + bean.getMobilenumber() + "\","
//                            + "\"" + bean.getDoss() + "\","
//                            + "\"" + bean.getDoee() + "\","
//                            + "\"" + bean.getBill() + "\"\n");
//                }
//
//                writer.close();
//
//
//                if (Desktop.isDesktopSupported()) {
//                    Desktop.getDesktop().open(selectedFile);
//                } else {
//                    System.out.println("Desktop not supported.");
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @FXML
    void doexporttoexcel(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save CSV File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv")
            );

            File selectedFile = fileChooser.showSaveDialog(null);
            if (selectedFile != null) {
                FileWriter writer = new FileWriter(selectedFile);

                writer.write("MOBILE NUMBER,DATE OF START,DATE OF END,TOTAL AMOUNT\n");

                ObservableList<billboardBean> data = tblviewbillboard.getItems();
                for (billboardBean bean : data) {
                    writer.write("=\"" + bean.getMobilenumber() + "\","    // keep as text
                            + "'" + bean.getDoss() + "',"                 // force date text
                            + "'" + bean.getDoee() + "',"
                            + bean.getBill() + "\n");
                }

                writer.close();

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(selectedFile);
                } else {
                    System.out.println("Desktop not supported.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    Connection con;
    @FXML
    void initialize() {

        selcombostatus.getItems().addAll("All","Paid","Unpaid");

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
