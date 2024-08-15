package com.example.sweetsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import javafx.scene.control.Label;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextInputDialog;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.*;

import javafx.scene.layout.GridPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;


import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;



import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;




public class HelloController {
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();
    @FXML
    private TextField emailtxt;

    @FXML
    private Button forgot;

    @FXML
    private TextField passtxt;


    @FXML
    private Button signin;

    @FXML
    private Button signup;

    @FXML
    public void initialize() {
        // Set up the columns in the table
        useridc.setCellValueFactory(new PropertyValueFactory<>("userId"));
        usernamec.setCellValueFactory(new PropertyValueFactory<>("username"));
        userpassc.setCellValueFactory(new PropertyValueFactory<>("password"));
        useremailc.setCellValueFactory(new PropertyValueFactory<>("email"));
        userrolec.setCellValueFactory(new PropertyValueFactory<>("role"));
        userinfoc.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));
        useraddressc.setCellValueFactory(new PropertyValueFactory<>("address"));
        userimagec.setCellValueFactory(new PropertyValueFactory<>("image"));

        // Load data from the database
        try {
            Usertable.setItems(getUsers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function to get data from the database
    private ObservableList<User> getUsers() throws SQLException {
        ObservableList<User> users = FXCollections.observableArrayList();

        // Database connection
        Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM sweet_system.users");

        while (rs.next()) {
            users.add(new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getString("contact_info"),
                    rs.getString("address"),
                    rs.getString("image")
            ));
        }

        return users;
    }

    @FXML
    void forgotPassButton(ActionEvent event) {

    }

    @FXML
    void signinButton(ActionEvent event) {
        String email = emailtxt.getText();
        String password = passtxt.getText();

        User user = getUser(email, password);
        if (user != null && "Admin".equals(user.getRole())) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPage.fxml"));
                AnchorPane root = loader.load();

                // الحصول على التحكم من المشهد الجديد
                HelloController adminController = loader.getController();
                adminController.AdName.setText(user.getUsername());
                adminController.Ademail.setText(user.getEmail());
                adminController.AdPass.setText(user.getPassword());
                adminController.AdInfo.setText(user.getContactInfo());
                adminController.AdAddress.setText(user.getAddress());
                adminController.profileimage.setImage(new Image(user.getImage()));

                Stage stage = (Stage) signin.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Show error message or handle invalid login
            System.out.println("Invalid email or password");
        }
    }
    @FXML
    void signupButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
            AnchorPane root = loader.load();
            Stage stage = (Stage) signup.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private Label AdInfo = new Label();

    @FXML
    private Label AdName=new Label();

    @FXML
    private Button AdOrder;

    @FXML
    private Label AdPass =new Label();

    @FXML
    private Button AdProduct;

    @FXML
    private Button AdReport;

    @FXML
    private Button AdStores;

    @FXML
    private Button AdUsers;

    @FXML
    private Label Ademail = new Label();
    @FXML
    private Label AdAddress = new Label();

    @FXML
    private Button Adlogout;

    @FXML
    private Button AdminEdit;

    @FXML
    private ImageView profileimage = new ImageView();

    @FXML
    void AdminLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            AnchorPane root = loader.load();
            Stage stage = (Stage) Adlogout.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void AdminReports(ActionEvent event) {

    }

    @FXML
    void Adminedit(ActionEvent event) {

    }

    @FXML
    void Adminorder(ActionEvent event) {

    }

    @FXML
    void Adminproduct(ActionEvent event) {

    }

    @FXML
    void Adminstores(ActionEvent event) {

    }

    @FXML
    void Adminusers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UsersAdmin.fxml"));
            AnchorPane root = loader.load();
            initialize();
            Stage stage = (Stage) AdUsers.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private User getUser(String email, String password) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String query = "SELECT * FROM sweet_system.users WHERE email = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("role"),
                        resultSet.getString("contact_info"),
                        resultSet.getString("Address"),
                        resultSet.getString("image")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @FXML
    private Button selectimg;

    @FXML
    private Button sign;

    @FXML
    private TextField signaddresstxt;

    @FXML
    private TextField signcontacttxt;

    @FXML
    private TextField signemailtxt;

    @FXML
    private TextField signnametxt;

    @FXML
    private TextField signpasstxt;

    @FXML
    private ComboBox<?> signroletxt;



    @FXML
    void selectphoto(ActionEvent event) {

    }

    @FXML
    void signupbuttonn(ActionEvent event) {

    }


    @FXML
    private TableView<User> Usertable =new TableView<>();

    @FXML
    private Button addb;

    @FXML
    private Button backarrow;

    @FXML
    private Button deleteb;

    @FXML
    private Button editb;

    @FXML
    private ChoiceBox<?> searchby;

    @FXML
    private TextField searchuser;

    @FXML
    private TableColumn<User, Integer> useridc=new TableColumn<>();

    @FXML
    private TableColumn<User, String> usernamec=new TableColumn<>();

    @FXML
    private TableColumn<User, String> userpassc =new TableColumn<>();

    @FXML
    private TableColumn<User, String> useremailc =new TableColumn<>();

    @FXML
    private TableColumn<User, String> userrolec =new TableColumn<>();

    @FXML
    private TableColumn<User, String> userinfoc =new TableColumn<>();

    @FXML
    private TableColumn<User, String> useraddressc =new TableColumn<>();

    @FXML
    private TableColumn<User, String> userimagec =new TableColumn<>();


    @FXML
    void adduser(ActionEvent event) {

    }

    @FXML
    void deleteuser(ActionEvent event) {

    }

    @FXML
    void edituser(ActionEvent event) {

    }

}