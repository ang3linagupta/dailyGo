package com.example.newspaperproject.customerboard;
import com.example.newspaperproject.jdbcc.MysqlDBConnection;
import com.example.newspaperproject.billboard.billboardBean;
import com.example.newspaperproject.billcollector.billcollectorBean;
import com.example.newspaperproject.customerboard.customerboardBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.newspaperproject.jdbcc.MysqlDBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class customerboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> selcomboarea;

    @FXML
    private ComboBox<String> selcombohawker;

    @FXML
    private ComboBox<String> selcombopaper;

    @FXML
    private TableView<customerboardBean> tblviewcustomer;

    void showerrmsg(String msg)
    {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Oops!! Here's a problem detected");
        alert.setHeaderText("ERROR !!");
        alert.setContentText(msg);
        alert.showAndWait();
    }


    @FXML
    void doshowdata(ActionEvent event) {

        tblviewcustomer.getColumns().clear();

        TableColumn<customerboardBean, String> mobC=new TableColumn<customerboardBean, String>("MOBILE NUMBER");
        mobC.setCellValueFactory(new PropertyValueFactory<customerboardBean,String>("mobile"));
        mobC.setMinWidth(200);

        TableColumn<customerboardBean, String> cnameC=new TableColumn<customerboardBean, String>("NAME");
        cnameC.setCellValueFactory(new PropertyValueFactory<customerboardBean,String>("cname"));
        cnameC.setMinWidth(100);

        TableColumn<customerboardBean, String> emailC=new TableColumn<customerboardBean, String>("EMAIL ID");
        emailC.setCellValueFactory(new PropertyValueFactory<customerboardBean,String>("emailid"));
        emailC.setMinWidth(100);

        TableColumn<customerboardBean, String> addC=new TableColumn<customerboardBean, String>("ADDRESS");
        addC.setCellValueFactory(new PropertyValueFactory<customerboardBean,String>("address"));
        addC.setMinWidth(100);

        TableColumn<customerboardBean, String> doeC=new TableColumn<customerboardBean, String>("DATE OF START");
        doeC.setCellValueFactory(new PropertyValueFactory<customerboardBean,String>("dos"));
        doeC.setMinWidth(100);

        TableColumn<customerboardBean, String> areaC=new TableColumn<customerboardBean, String>("AREA");
        areaC.setCellValueFactory(new PropertyValueFactory<customerboardBean,String>("area"));
        areaC.setMinWidth(100);

        TableColumn<customerboardBean, String> hwkdC=new TableColumn<customerboardBean, String>("HAWKID");
        hwkdC.setCellValueFactory(new PropertyValueFactory<customerboardBean,String>("hawkerid"));
        hwkdC.setMinWidth(100);

        TableColumn<customerboardBean, String> paperC=new TableColumn<customerboardBean, String>("PAPERS");
        paperC.setCellValueFactory(new PropertyValueFactory<customerboardBean,String>("papers"));
        paperC.setMinWidth(100);

        TableColumn<customerboardBean, String> pricesC=new TableColumn<customerboardBean, String>("PRICES");
        pricesC.setCellValueFactory(new PropertyValueFactory<customerboardBean,String>("prices"));
        pricesC.setMinWidth(100);

        tblviewcustomer.getColumns().addAll(mobC,cnameC,emailC,addC,doeC,areaC,hwkdC,paperC,pricesC);
        tblviewcustomer.setItems(null);
        tblviewcustomer.setItems(getArrayOfObjects());

    }


ObservableList<customerboardBean> getArrayOfObjects() {
    ObservableList<customerboardBean> list = FXCollections.observableArrayList();

    try {
        PreparedStatement pst = null;

        // Safely get combo values (avoid NullPointerException)
        String areaValue = selcomboarea.getValue() == null ? "none" : selcomboarea.getValue();
        String paperValue = selcombopaper.getValue() == null ? "none" : selcombopaper.getValue();
        String hawkerValue = selcombohawker.getValue() == null ? "none" : selcombohawker.getValue();

        // Handle different filter conditions
        if (areaValue.equals("none") && paperValue.equals("none") && hawkerValue.equals("none")) {
            pst = con.prepareStatement("SELECT * FROM customers");
        }
        else if (!areaValue.equals("none") && paperValue.equals("none") && hawkerValue.equals("none")) {
            pst = con.prepareStatement("SELECT * FROM customers WHERE area = ?");
            pst.setString(1, areaValue);
        }
        else if (areaValue.equals("none") && !paperValue.equals("none") && hawkerValue.equals("none")) {
            pst = con.prepareStatement("SELECT * FROM customers WHERE papers LIKE ?");
            pst.setString(1, "%" + paperValue + "%");
        }
        else if (areaValue.equals("none") && paperValue.equals("none") && !hawkerValue.equals("none")) {
            pst = con.prepareStatement("SELECT * FROM customers WHERE hawkerid = ?");
            pst.setString(1, hawkerValue);
        }
        else if (!areaValue.equals("none") && !paperValue.equals("none") && hawkerValue.equals("none")) {
            pst = con.prepareStatement("SELECT * FROM customers WHERE area = ? AND papers LIKE ?");
            pst.setString(1, areaValue);
            pst.setString(2, "%" + paperValue + "%");
        }
        else if (areaValue.equals("none") && !paperValue.equals("none") && !hawkerValue.equals("none")) {
            pst = con.prepareStatement("SELECT * FROM customers WHERE papers LIKE ? AND hawkerid = ?");
            pst.setString(1, "%" + paperValue + "%");
            pst.setString(2, hawkerValue);
        }
        else if (!areaValue.equals("none") && paperValue.equals("none") && !hawkerValue.equals("none")) {
            pst = con.prepareStatement("SELECT * FROM customers WHERE area = ? AND hawkerid = ?");
            pst.setString(1, areaValue);
            pst.setString(2, hawkerValue);
        }
        else if (!areaValue.equals("none") && !paperValue.equals("none") && !hawkerValue.equals("none")) {
            pst = con.prepareStatement("SELECT * FROM customers WHERE area = ? AND papers LIKE ? AND hawkerid = ?");
            pst.setString(1, areaValue);
            pst.setString(2, "%" + paperValue + "%");
            pst.setString(3, hawkerValue);
        }

        ResultSet res = pst.executeQuery();
        boolean hasData = false;

        while (res.next()) {
            String mobile = res.getString("mobile");
            String cname = res.getString("cname");
            String emailid = res.getString("emailid");
            String address = res.getString("address");
            String dos = res.getString("dos");
            String area = res.getString("area");
            String hawkerid = res.getString("hawkerid");
            String papers = res.getString("papers");
            String prices = res.getString("prices");

            // Safely handle selected paper filter
            if (paperValue != null && !paperValue.equalsIgnoreCase("none")) {
                String[] paperList = papers.split(",");
                String[] priceList = prices.split(",");

                for (int i = 0; i < paperList.length; i++) {
                    if (paperList[i].trim().equalsIgnoreCase(paperValue.trim())) {
                        papers = paperList[i].trim();
                        prices = priceList[i].trim();
                        break;
                    }
                }
            }

            customerboardBean cust = new customerboardBean(mobile, cname, emailid, address, dos, area, hawkerid, papers, prices);
            list.add(cust);
            hasData = true;
        }

        if (!hasData) {
            showerrmsg("No customer records found for the selected filters.");
        }

    } catch (Exception exp) {
        exp.printStackTrace();
        showerrmsg("An error occurred while fetching customer data. Please try again.");
    }

    return list;
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

        try {
            PreparedStatement stmt = con.prepareStatement("select area from areas");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                selcomboarea.getItems().add(rs.getString("area"));
            }
            selcomboarea.getItems().add("none");
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            PreparedStatement stmt = con.prepareStatement("select paper from newspapers");
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                selcombopaper.getItems().add(res.getString("paper"));
            }
            selcombopaper.getItems().add("none");
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            PreparedStatement stmt = con.prepareStatement("select hawkerid from hawkers");
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                selcombohawker.getItems().add(res.getString("hawkerid"));
            }
            selcombohawker.getItems().add("none");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
