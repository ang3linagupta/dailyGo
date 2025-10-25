package com.example.newspaperproject.hawkers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.newspaperproject.jdbcc.MysqlDBConnection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class HawkersController {

    Connection con;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> ComboArea;

    @FXML
    private ComboBox<String> ComboID;

    @FXML
    private ImageView prev;

    @FXML
    private TextField txtAadhar;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private DatePicker txtDOJ;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSelArea;

    String path;

    @FXML
    void doClr(ActionEvent event) {
        ComboID.setValue(null);
        ComboArea.setValue(null);
        txtAadhar.setText(null);
        txtName.setText(null);
        txtContact.setText(null);
        txtAddress.setText(null);
        txtSelArea.setText(null);
        txtDOJ.setValue(null);
        prev.setImage(img);

    }

    @FXML
    void doFetch(ActionEvent event) {

        try {
            PreparedStatement stmt = con.prepareStatement("select * from hawkers where hawkerid=?");
            stmt.setString(1,ComboID.getValue());
            ResultSet res= stmt.executeQuery();

            if(res.next()==true)
            {
                String name=res.getString("name");
                String contact=res.getString("contact");
                String address=res.getString("address");
                String adhaar=res.getString("adhaar");
                Date doj=res.getDate("doj");
                String picPath=res.getString("picpath");
                String selareas=res.getString("selareas");

                String[] sel=selareas.split(",");

                txtName.setText(name);
                txtName.setDisable(true);
                txtContact.setText(contact);
                txtContact.setDisable(true);
                txtAddress.setText(address);
                txtAadhar.setText(adhaar);
                txtAadhar.setDisable(true);
                txtDOJ.setValue(doj.toLocalDate());
                prev.setImage(new Image(new FileInputStream(new File(picPath))));
                path=picPath;
                txtSelArea.setText(selareas);

            }
            else
                System.out.println("Invalid ID");

        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }


    }

    @FXML
    void doFire(ActionEvent event) {

        String query="delete from hawkers where hawkerid=?";
        try {
            PreparedStatement pst= con.prepareStatement(query);

            pst.setString(1,ComboID.getValue());

            int count=pst.executeUpdate();
            if(count==0)
                System.out.println("Invalid Id");
            else
                System.out.println("Record Deleted");

        }
        catch(Exception exp)
        {
            System.out.println(exp);
        }
        ComboID.getItems().remove(ComboID.getValue());

    }

    @FXML
    void doModify(ActionEvent event) {

        String query="update hawkers set  address=?,doj=?, picpath=?,selareas=? where hawkerid=?";
        try {
            PreparedStatement pst= con.prepareStatement(query);
            pst.setString(1,txtAddress.getText());

            LocalDate lcl=  txtDOJ.getValue();
            java.sql.Date dt=java.sql.Date.valueOf(lcl);
            pst.setDate(2, dt);

            pst.setString(3,path);
            pst.setString(4,txtSelArea.getText());
            pst.setString(5,ComboID.getValue());

            int count=pst.executeUpdate();
            if(count==0)
                System.out.println("Inavlid Id");
            else
                System.out.println("Record Updated");

        }
        catch(Exception exp)
        {
            System.out.println(exp);
        }

    }

    @FXML
    void doRecruit(ActionEvent event) {

        String query="insert into hawkers values(?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement pst = con.prepareStatement(query);

            String name=txtName.getText();
            String contact=txtContact.getText();
            String hawkerID;
            if(name.length()>=5) {
                hawkerID = name.substring(0, 5) + contact.substring(contact.length()-5,contact.length());
            }
            else {
                hawkerID=name.substring(0,name.length())+contact.substring(contact.length()-5,contact.length());
            }
            pst.setString(1,hawkerID);
            pst.setString(2,name);
            pst.setString(3,contact);
            pst.setString(4,txtAddress.getText());
            pst.setString(5,txtAadhar.getText());


            LocalDate lcl= txtDOJ.getValue();
            java.sql.Date dt=java.sql.Date.valueOf(lcl);
            pst.setDate(6, dt);

            pst.setString(7,path);
            pst.setString(8,txtSelArea.getText());
            pst.executeUpdate();
            ComboID.getItems().add(hawkerID);

            System.out.println("Hawker Recruited");
        }
        catch (Exception exp){
            System.out.println(exp);
        }


    }

    @FXML
    void doSelArea(ActionEvent event)
    {
        txtSelArea.setText(txtSelArea.getText()+ComboArea.getValue()+",");
    }

    @FXML
    void doBrowse(ActionEvent event) {
        FileChooser chooser=new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*") );
        File file= chooser.showOpenDialog(null);
        path=(file.getAbsolutePath());

        try {

            prev.setImage(new Image(new FileInputStream(file)));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    Image img;

    @FXML
    void initialize() {
        con= MysqlDBConnection.getMySQLDBConnection();
        if(con==null)
        {
            System.out.println("Connection Error");
            return;
        }

        img=prev.getImage();

        ArrayList<String> lstAreas=getAllAreas();
        for(String str: lstAreas){
            ComboArea.getItems().add(str);
        }
        ArrayList<String> lstHawkerID=getHawkerID();
        for(String str: lstHawkerID){
            ComboID.getItems().add(str);
        }

    }

    ArrayList<String> getAllAreas(){
        ArrayList<String> Areas=new ArrayList<String>();

        try
        {
            PreparedStatement stmt = con.prepareStatement("select area from areas");
            ResultSet res= stmt.executeQuery();

            while(res.next())
            {
                String area=res.getString("area");
                Areas.add(area);

            }
            System.out.println(Areas);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        return Areas;
    }

    ArrayList<String> getHawkerID(){
        ArrayList<String> HawkerIDs=new ArrayList<String>();

        try
        {
            PreparedStatement stmt = con.prepareStatement("select hawkerid from hawkers");
            ResultSet res= stmt.executeQuery();

            while(res.next())
            {
                String hawkerID=res.getString("hawkerid");
                HawkerIDs.add(hawkerID);

            }
            System.out.println(HawkerIDs);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        return HawkerIDs;
    }

    ResultSet getsinglehawker(String id){
        ResultSet res=null;
        try {
            PreparedStatement stmt = con.prepareStatement("select * from hawkers where hawkerid=?");
            stmt.setString(1,ComboID.getValue());
            res= stmt.executeQuery();

        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        return res;
    }

    }


