package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProfileController {
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextArea bio;

    @FXML
    protected void update_profile(ActionEvent Event){
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/OOPS", "root", "lucky911");
            String sql = "UPDATE OOPS.Users SET UserName = (?), email = (?) , bio = (?) WHERE UID = (?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,name.getText());
            pst.setString(2,email.getText());
            pst.setString(3,bio.getText());
            pst.setInt(4, UserHolder.getInstance().getUid());
            pst.execute();
            UserHolder.getInstance().setName(name.getText());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
