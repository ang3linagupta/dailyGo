package com.example.newspaperproject.billing;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import com.example.newspaperproject.jdbcc.MysqlDBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class billingController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dateofend;

    @FXML
    private DatePicker dtdoss;

    @FXML
    private TextField lessdays;

    @FXML
    private TextField totalbillamount;

    @FXML
    private TextField txtmobile;

    @FXML
    private TextField txtnamee;

    @FXML
    private TextField txttotprice;

    void success(String msg)
    {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("SUCCESS!!");
        alert.setContentText(msg);
        alert.showAndWait();
    }


    void showerrmsg(String msg)
    {

        Alert alert = new Alert(Alert.AlertType.ERROR);


        alert.setTitle("Information Dialog");
        alert.setHeaderText("ERROR!!");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    Connection con;

    float pricePerDay=0;

    @FXML
    void dofetch(ActionEvent event) {
        String mobile = txtmobile.getText();
        if (mobile.isEmpty()) {
            showerrmsg("Please enter mobile number.");
            return;
        }

        try {
            String query = "select cname, dos, prices from customers where mobile = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, mobile);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txtnamee.setText(rs.getString("cname"));
                dtdoss.setValue(rs.getDate("dos").toLocalDate());

                String[] prices = rs.getString("prices").split(",");
                float sum = 0;
                for (String p : prices) {
                    sum += Float.parseFloat(p.trim());
                }

                pricePerDay = sum;
                txttotprice.setText(String.valueOf(sum));
            } else {
                showerrmsg("No customer found with this mobile number.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showerrmsg("Error fetching customer data.");
        }
    }

    @FXML
    void dogeneratebill(ActionEvent event) {
        try {
            LocalDate dos = dtdoss.getValue();
            LocalDate doe = dateofend.getValue();

            if (dos == null || doe == null) {
                showerrmsg( "Please select both DOS and DOE.");
                return;
            }

            long totalDays = ChronoUnit.DAYS.between(dos, doe)+1;
            int less = 0;

            if (!lessdays.getText().isEmpty()) {
                less = Integer.parseInt(lessdays.getText().trim());
            }

            long billableDays = totalDays - less;
            if (billableDays < 0) {
                showerrmsg( "Less days cannot be more than total duration.");
                return;
            }

            float totalBill = billableDays * pricePerDay;
            totalbillamount.setText(String.valueOf(totalBill));

        } catch (Exception e) {
            e.printStackTrace();
            showerrmsg(e.getMessage());
        }
    }

    void doupdate(){
        String query="update customers set dos =? where mobile =? ";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            LocalDate doe = dateofend.getValue();
            Date dt=Date.valueOf(doe.plusDays(1));

            pst.setDate(1,dt);
            pst.setString(2,txtmobile.getText());
            pst.executeUpdate();
            System.out.println("Date updated in customer table");
        }
        catch(Exception exp){
            System.out.println(exp.getMessage());
            showerrmsg(exp.getMessage());

        }
    }
    @FXML
    void dosavebill(ActionEvent event) {
        try {
            String mobile = txtmobile.getText();
            LocalDate dos = dtdoss.getValue();
            LocalDate doe = dateofend.getValue();

            if (mobile.isEmpty() || dos == null || doe == null || totalbillamount.getText().isEmpty()) {
                showerrmsg("Fill in all required fields before saving.");
                return;
            }

            int less = 0;
            if (!lessdays.getText().isEmpty()) {
                less = Integer.parseInt(lessdays.getText());
            }

            int days = (int) ChronoUnit.DAYS.between(dos, doe) - less;
            float bill = Float.parseFloat(totalbillamount.getText());

            // First check if record exists
            PreparedStatement pst1 = con.prepareStatement("SELECT * FROM billing WHERE mobilenumber = ?");
            pst1.setString(1, mobile);
            ResultSet rs = pst1.executeQuery();

            if (rs.next()) {

                String updateQuery = "UPDATE billing SET doss = ?, doee = ?, dayss = ?, bill = ? WHERE mobilenumber = ?";
                PreparedStatement pst = con.prepareStatement(updateQuery);
                pst.setDate(1, Date.valueOf(dos));
                pst.setDate(2, Date.valueOf(doe));
                pst.setInt(3, days);
                pst.setFloat(4, bill);
                pst.setString(5, mobile);
                pst.executeUpdate();
                success("Record Updated");
            } else {

                String insertQuery = "INSERT INTO billing (mobilenumber, doss, doee, dayss, bill) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(insertQuery);
                pst.setString(1, mobile);
                pst.setDate(2, Date.valueOf(dos));
                pst.setDate(3, Date.valueOf(doe));
                pst.setInt(4, days);
                pst.setFloat(5, bill);
                pst.executeUpdate();
                success("Record Inserted");
            }
            doupdate();

        } catch (Exception e) {
            e.printStackTrace();
            showerrmsg("Exception: " + e.getMessage());
        }
    }

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
