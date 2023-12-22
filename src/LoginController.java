package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.*;

public class LoginController {

    @FXML
    private TextField useR;

    @FXML
    private PasswordField pass;


    Parent root;
    Stage primaryStage = new Stage();
    String username = "";

    @FXML
    protected void onLoginButtonClick(ActionEvent Event) throws SQLException {
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/OOPS", "root", "lucky911");
            String sql = "SELECT count(*) FROM OOPS.USERS where UserName = ? and Password = ? ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, useR.getText());
            pst.setString(2, pass.getText());
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {

                User u = new User();
                u.name = useR.getText();
                UserHolder userholder = UserHolder.getInstance();
                userholder.setUser(u);
                userholder.setName(useR.getText());
                userholder.setUid(getuidData());
                this.username = useR.getText();
                BorderPane pane = (BorderPane)FXMLLoader.load(getClass().getResource("MainPage.fxml"));
                System.out.println(getClass().getResource("MainPage.fxml"));
                Scene scene = new Scene(pane, 815, 700);
                primaryStage.setScene(scene);
                primaryStage.show();

            }
        }catch(Exception e){
            System.out.println(e);
        }

    }

    public void onSignUpButton(ActionEvent e)throws Exception{
        FXMLLoader pane = new FXMLLoader(getClass().getResource("SignUp.fxml"));
        Scene scene = new Scene(pane.load(), 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public int getuidData() throws SQLException {
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/OOPS", "root",
                    "lucky911");
            String sql = "SELECT * FROM OOPS.USERS where UserName = (?) ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, useR.getText());
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                return rs.getInt("uid");
            }
        }catch (Exception e){
            System.out.println(e);
        }
            return 0;
    }
}
