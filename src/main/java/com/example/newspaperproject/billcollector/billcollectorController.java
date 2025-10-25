package com.example.newspaperproject.billcollector;
import com.example.newspaperproject.jdbcc.MysqlDBConnection;
import com.example.newspaperproject.billcollector.billcollectorBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class billcollectorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<billcollectorBean> tblviewbillcollect;

    @FXML
    private TextField txtfillmobile;

    @FXML
    private Label txtpendingamt;

    void success(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Operation Successfull!");
       alert.setHeaderText("Success");
        alert.setContentText(msg);
        alert.showAndWait();
    }


    void showerrmsg(String msg)
    {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Oops!! Here's a problem detected");
        alert.setHeaderText("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    void dopaybill(ActionEvent event) {

        try {

            String updateQuery = "update billing set status = 0 where mobilenumber = ? and status = 1";
            PreparedStatement pst = con.prepareStatement(updateQuery);
            pst.setString(1, txtfillmobile.getText());
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                success("Bill Payed Successfully");
            } else {
                showerrmsg("No pending bills found or Mobile Number not registered.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showerrmsg(ex.getMessage());
        }

    }

    @FXML
    void doshowpendingbill(ActionEvent event) {


        tblviewbillcollect.getColumns().clear();


        TableColumn<billcollectorBean, String> dosC=new TableColumn<billcollectorBean, String>("DATE OF START");
        dosC.setCellValueFactory(new PropertyValueFactory<billcollectorBean,String>("doss"));
        dosC.setMinWidth(100);

        TableColumn<billcollectorBean, String> doeC=new TableColumn<billcollectorBean, String>("DATE OF END");
        doeC.setCellValueFactory(new PropertyValueFactory<billcollectorBean,String>("doee"));
        doeC.setMinWidth(100);

        TableColumn<billcollectorBean, String> billC=new TableColumn<billcollectorBean, String>("TOTAL BILL");
        billC.setCellValueFactory(new PropertyValueFactory<billcollectorBean,String>("bill"));
        billC.setMinWidth(100);

        tblviewbillcollect.getColumns().addAll(dosC,doeC,billC);
        tblviewbillcollect.setItems(null);
        tblviewbillcollect.setItems(getArrayOfObjects());

    }

    ObservableList<billcollectorBean> getArrayOfObjects()
    {

        ObservableList<billcollectorBean> list= FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = con.prepareStatement("select * from billing where mobilenumber = ? and status = 1");
            stmt.setString(1,txtfillmobile.getText());
            ResultSet res= stmt.executeQuery();
            boolean hasData = false;
            while(res.next())
            {
                String doss = res.getString("doss");
                String doee = res.getString("doee");
                String bill = String.valueOf(res.getFloat("bill"));
                billcollectorBean bil=new billcollectorBean(doss,doee,bill);
                list.add(bil);
                hasData=true;
            }
            float total = 0;
            for (billcollectorBean bean : list) {
                try {
                    total += Float.parseFloat(bean.getBill());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!hasData) {
                showerrmsg("No pending bills or Mobile Number not found.");
            }
            txtpendingamt.setText(String.format("%.2f", total));

        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        return  list;

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
