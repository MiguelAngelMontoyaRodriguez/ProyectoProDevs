module com.example.login {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.base;



    opens com.example.login.controllers to javafx.fxml;

    opens com.example.login to javafx.fxml;
    exports com.example.login;
}