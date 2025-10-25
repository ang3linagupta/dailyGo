package com.example.newspaperproject.papermaster;
import com.example.newspaperproject.jdbcc.MysqlDBConnection;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PaperMasterController {

    Connection con;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> ComboTitle;

    @FXML
    private TextField txtLang;

    @FXML
    private TextField txtPrice;

    @FXML
    void doAvail(ActionEvent event) {

        String query = "insert into newspapers values(?,?,?)";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, String.valueOf(ComboTitle.getValue()));
            pst.setString(2, txtLang.getText());
            pst.setFloat(3, Float.parseFloat(txtPrice.getText()));

            pst.executeUpdate();

            if (!ComboTitle.getItems().contains(ComboTitle.getValue().toString())) {
                ComboTitle.getItems().add(ComboTitle.getValue().toString());
            }


            System.out.println("Record Saved");

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

// ****************************************************************************************

    @FXML
    void doClrNew(ActionEvent event) {

        txtLang.setText(null);
        ComboTitle.setValue(null);
        txtPrice.setText(null);

    }
    // ****************************************************************************************

    @FXML
    void doDelete(ActionEvent event) {

        String query="delete from newspapers where paper=?";
        try {
            PreparedStatement pst= con.prepareStatement(query);

            pst.setString(1,String.valueOf(ComboTitle.getValue()));

            int count=pst.executeUpdate();

            if(count==0)
                System.out.println("Deletion failed: Newspaper not found in the database.");
            else
                System.out.println("Record Deleted");

        }
        catch(Exception exp)
        {
            System.out.println(exp);
        }

    }

    // ****************************************************************************************


    @FXML
    void doFind(ActionEvent event) {

        String query="select * from newspapers where paper=?";
        try{
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,String.valueOf(ComboTitle.getValue()));
            ResultSet set=pst.executeQuery();
            if(set.next()==true){
                String language=set.getString("language");
                float price=set.getFloat("price");
                txtLang.setText(language);
                txtPrice.setText( String.valueOf( price));
            }
        }
        catch (Exception exp){
            System.out.println(exp);
        }

    }

    //**************************************************************************************************

    @FXML
    void doUpdate(ActionEvent event) {

        String query="update newspapers set price=? where paper=? and language=?";
         try {
             PreparedStatement pst = con.prepareStatement(query);
             pst.setFloat(1,Float.parseFloat(txtPrice.getText()));
             pst.setString(2,String.valueOf(ComboTitle.getValue()));
             pst.setString(3,txtLang.getText());

             int count=pst.executeUpdate();

            if(count==0)
                System.out.println("No matching newspaper found in the database.");
            else
                System.out.println("Record Updated");

        }
        catch(Exception exp)
        {
            System.out.println(exp);
        }

    }

    //****************************************************************************************************
    @FXML
    void initialize() {
        con= MysqlDBConnection.getMySQLDBConnection();
        if(con==null)
        {
            System.out.println("Connection Error");
            return;
        }

        ArrayList<String> papersLst= getAllpapers();
        for(String  s : papersLst){
            ComboTitle.getItems().add(s);
        }

    }

    ArrayList<String> getAllpapers()
    {

        ArrayList<String> papers=new ArrayList<String>();

        try
        {
            PreparedStatement stmt = con.prepareStatement("select paper from newspapers");
            ResultSet res= stmt.executeQuery();

            while(res.next())
            {
                String paper=res.getString("paper");
                papers.add(paper);

            }
            System.out.println(papers);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        return papers;


    }
}
