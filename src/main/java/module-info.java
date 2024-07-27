module com.example.sweetsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.sweetsystem to javafx.fxml;
    exports com.example.sweetsystem;
}
