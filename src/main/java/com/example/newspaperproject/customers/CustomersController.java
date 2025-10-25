package com.example.newspaperproject.customers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.newspaperproject.jdbcc.MysqlDBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;


import com.example.newspaperproject.jdbcc.MysqlDBConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

public class CustomersController {

    Connection con;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> Comboareas;

    @FXML
    private ComboBox<String> CombohawkerID;

    @FXML
    private DatePicker dateofstart;

    @FXML
    private ListView<String> lstSelPapers;

    @FXML
    private ListView<String> lstSelPrices;

    @FXML
    private ListView<String> lstpaper;

    @FXML
    private ListView<String> lstprices;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtmobileno;

    @FXML
    void showMsg(Alert alert, String title, String msg) {

        alert.setTitle(title);

        alert.setContentText(msg);

        alert.showAndWait();
    }

    @FXML
    void doaddlstselprices(MouseEvent event) {
        if(event.getClickCount()==2)
        {
            lstSelPapers.getItems().add(lstpaper.getSelectionModel().getSelectedItem());
            int ind=lstpaper.getSelectionModel().getSelectedIndex();
            lstprices.getSelectionModel().select(ind);
            lstSelPrices.getItems().add(lstprices.getSelectionModel().getSelectedItem());
        }

    }

    @FXML
    void dochange(ActionEvent event) {
        String query="update customers set emailid=?, address=?,dos=?,area=?,hawkerid=?,papers=?,prices=?  where mobile=?";
        try {
            PreparedStatement pst= con.prepareStatement(query);
            pst.setString(1,txtemail.getText());
            pst.setString(2,txtAddress.getText());
            LocalDate lcl= dateofstart.getValue();
            java.sql.Date dt=java.sql.Date.valueOf(lcl);
            pst.setDate(3, dt);
            pst.setString(4,Comboareas.getValue());
            pst.setString(5,CombohawkerID.getValue());
            ObservableList<String> items = lstSelPapers.getItems();
            String paper="";
            for(String s:items)
            {
                paper=paper+","+s;

            }
            pst.setString(6,paper);
            String price="";
            ObservableList<String> itemprice = lstSelPrices.getItems();
            for(String s:itemprice)
            {
                price=price+","+s;

            }
            pst.setString(7,price);
            pst.setString(8,txtmobileno.getText());


            int count=pst.executeUpdate();
            if(count==0)
                showMsg(new Alert(Alert.AlertType.ERROR),"Error","invalid mobiono");
            else
                showMsg(new Alert(Alert.AlertType.CONFIRMATION),"Confirmation","Record updated successfully");

        }
        catch(Exception exp)
        {
            System.out.println(exp);
        }

    }

    @FXML
    void doclear(ActionEvent event) {
        txtmobileno.setText("");
        txtName.setText("");
        txtemail.setText("");
        txtAddress.setText("");
        dateofstart.setValue(null);
        Comboareas.setValue(null);
        CombohawkerID.setValue(null);
        lstSelPrices.getItems().clear();
        lstSelPapers.getItems().clear();
        showMsg(new Alert(Alert.AlertType.CONFIRMATION),"Confirmation","Record cleared successfully");


    }

    @FXML
    void doremovecustomer(ActionEvent event) {
        try {
            String query = "delete from customers where mobile=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,txtmobileno.getText());

            int c= pst.executeUpdate();
            if(c==0)
            {
                showMsg(new Alert(Alert.AlertType.ERROR),"Error","invalid mobileno");
            }
            else{
                showMsg(new Alert(Alert.AlertType.CONFIRMATION),"Confirmation","Record deleted successfully");
                doclear(event);
            }

        }
        catch(Exception exp)
        {
            System.out.println(exp);
        }

    }

    @FXML
    void dosavecustomer(ActionEvent event) {
        try {
            String query = "insert into customers values(?,?,?,?,?,?,?,?,?,1)";
            PreparedStatement pst = con.prepareStatement(query);


            pst.setString(1,txtmobileno.getText());

            pst.setString(2,txtName.getText());
            pst.setString(3,txtemail.getText());

            pst.setString(4,txtAddress.getText());
            LocalDate lcl=dateofstart.getValue();
            Date dt= Date.valueOf(lcl);
            pst.setDate(5,dt);

            pst.setString(6,Comboareas.getValue());

            pst.setString(7,CombohawkerID.getValue());

            ObservableList<String> items = lstSelPapers.getItems();
            String paper="";
            for(String s:items)
            {
                paper=paper+s+",";

            }
            pst.setString(8,paper);
            String price="";
            ObservableList<String> itemprice = lstSelPrices.getItems();
            for(String s:itemprice)
            {
                price=price+s+",";

            }
            pst.setString(9,price);
            pst.executeUpdate();
            showMsg(new Alert(Alert.AlertType.CONFIRMATION),"Confirmation","Record Saved successfully");

        }
        catch(Exception exp)
        {

            showMsg(new Alert(Alert.AlertType.ERROR),"Error",exp.toString());

        }


    }

    @FXML
    void dosearch(ActionEvent event) {
        try {
            String query = "select * from customers where mobile=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, txtmobileno.getText());
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                txtName.setText(res.getString("cname"));
                txtemail.setText(res.getString("emailid"));
                txtAddress.setText(res.getString("address"));
                dateofstart.setValue(res.getDate("dos").toLocalDate());

                Comboareas.setValue(res.getString("area"));
                CombohawkerID.setValue(res.getString("hawkerid"));
                String s=res.getString("papers");
                String[] ary=s.split(",");
                for(String m:ary)
                {
                    lstSelPapers.getItems().add(m);
                }
                String spr=res.getString("prices");
                String[] arypr=spr.split(",");
                for(String m:arypr)
                {
                    lstSelPrices.getItems().add(m);
                }

            }
            else{
                showMsg(new Alert(Alert.AlertType.ERROR),"Error","Mobile number does not exist in our records ");
            }
        }
        catch(Exception exp)
        {
            System.out.println(exp);
        }

    }

//    @FXML
//    void dofillhawkerid(ActionEvent event) {
//        try {
//            String query = "select hawkerid from hawkers where selareas=?";
//            PreparedStatement pst = con.prepareStatement(query);
//            pst.setString(1,Comboareas.getValue());
//            ResultSet res=pst.executeQuery();
//            while(res.next())
//            {
//                String id=res.getString("hawkerid");
//                CombohawkerID.getItems().add(id);
//            }
//        }
//        catch(Exception exp)
//        {
//            System.out.println(exp);
//        }
//
//    }

    @FXML
    void dofillhawkerid(ActionEvent event) {
        try {
            CombohawkerID.getItems().clear(); // clear old data
            String selectedArea = Comboareas.getValue();
            System.out.println("Selected Area: " + selectedArea);

            String query = "SELECT hawkerid FROM hawkers WHERE selareas LIKE ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, "%" + selectedArea + "%"); // partial match
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                String id = res.getString("hawkerid");
                CombohawkerID.getItems().add(id);
            }

            System.out.println("Fetched Hawker IDs: " + CombohawkerID.getItems());
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    ArrayList<String> aryprices=new ArrayList<String>();
    ArrayList<String>arypaper=new ArrayList<String>();



    @FXML
    void initialize() {

        con= MysqlDBConnection.getMySQLDBConnection();
        if(con==null)
        {
            System.out.println("Connection error");
            return;
        }
        CombohawkerID.getItems().add(null);
        lstSelPrices.getItems().removeAll();
        lstSelPapers.getItems().removeAll();
        ArrayList<String> arlst = getareas();
        for(String s:arlst)
        {
            Comboareas.getItems().add(s);
        }
        getpapersandprices();
        for(int i=0;i<aryprices.size();i++)
        {
            String paper= arypaper.get(i);
            lstpaper.getItems().add(paper);
            String prices=aryprices.get(i);
            lstprices.getItems().add(prices);
        }

    }
    void  getpapersandprices ()
    {

        try
        {
            PreparedStatement stmt = con.prepareStatement("select paper,price  from newspapers");
            ResultSet res= stmt.executeQuery();

            while(res.next())
            {
                String paper=res.getString("paper");
                Float price=res.getFloat("price");
                arypaper.add(paper);
                aryprices.add(String.valueOf(price));

            }
            System.out.println(arypaper);
            System.out.println(aryprices);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }

    }

    ArrayList<String> getareas()
    {

        ArrayList<String> pt=new ArrayList<String>();

        try
        {
            PreparedStatement stmt = con.prepareStatement("select distinct area from areas");
            ResultSet res= stmt.executeQuery();

            while(res.next())
            {
                String area=res.getString("area");
                pt.add(area);

            }
            System.out.println(pt);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        return pt;


    }

}
