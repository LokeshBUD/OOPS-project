package com.example.demo1;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TextField useR;

    @FXML
    private PasswordField pass;
    @FXML
    private PasswordField checkPass;

    @FXML
    private ListView<String> posts = new ListView<>();


    @FXML
    private Label Ltitle;

    @FXML
    private Label LContent;

    @FXML
    private TextField pTitle;

    @FXML
    private TextArea content;

    @FXML
    private Label intro = new Label();

    Parent root;
    Stage primaryStage = new Stage();

    public void signUp(ActionEvent Event) throws Exception {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/OOPS", "root",
                    "lucky911");
            PreparedStatement pst = conn.prepareStatement("INSERT INTO OOPS.USERS(Username,password) values(?,?)");
            pst.setString(1, useR.getText());
            pst.setString(2, pass.getText());
            if (pass.getText().equals(checkPass.getText())) {
                pst.executeUpdate();
                System.out.println("Data Registerded");
            } else
                System.out.println("something went wrong");
        } catch (Exception e) {
            System.out.println("Exception: " + e.toString());
        }
    }

    @FXML
    protected void onchangeToLogin(ActionEvent Event) throws Exception {

        FXMLLoader pane = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(pane.load(), 480, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }



    String title = "";
    String content1 = "";
    String[] arr = {"k"};



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) throws NullPointerException{
        ArrayList<String> postNames = new ArrayList<>();
        ArrayList<String> postContent = new ArrayList<>();
        intro.setText(UserHolder.getInstance().getName());
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/OOPS", "root",
                    "lucky911");
            String sql = "SELECT * FROM OOPS.posts";
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                postNames.add(rs.getString("PostTitle"));
                postContent.add(rs.getString("PostContent"));
            }
            posts.getItems().addAll(postNames);
            posts.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                title = posts.getSelectionModel().getSelectedItem();
                int index = postNames.indexOf(title);
                Ltitle.setText(title);
                LContent.setText(postContent.get(index));
            }
            });
        }catch (SQLException e){
            System.out.println("this");
            System.out.println(e);
        }
    }

    @FXML
    protected void onchangeToPost(ActionEvent Event) throws Exception {

        FXMLLoader pane = new FXMLLoader(getClass().getResource("Post.fxml"));
        Scene scene = new Scene(pane.load(), 622, 450);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    @FXML
    public void addPost(ActionEvent Event) throws Exception {
        String T = pTitle.getText();
        String C = content.getText();


        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/OOPS", "root",
                    "lucky911");
            String sql = "SELECT * FROM OOPS.USERS where UserName = (?) ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, UserHolder.getInstance().getName());
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                PreparedStatement pst1 = conn.prepareStatement("INSERT INTO OOPS.POSTS(Uid,PostTitle,PostContent) values(?,?,?)");
                pst1.setInt(1, rs.getInt("uid"));
                pst1.setString(2, T);
                pst1.setString(3, C);
                System.out.println("executed");
                pst1.execute();

            }

        }catch (Exception E){
            System.out.println(E);
        }


    }

    @FXML
    protected void changeToMain(ActionEvent Event) throws IOException {
        FXMLLoader pane = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Scene scene = new Scene(pane.load(), 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    protected void changeToProfile(ActionEvent E) throws IOException {
        FXMLLoader pane = new FXMLLoader(getClass().getResource("Profile.fxml"));
        Scene scene = new Scene(pane.load(), 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void refresh(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
        try {
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            Scene scene = new Scene(root, 815, 700);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

