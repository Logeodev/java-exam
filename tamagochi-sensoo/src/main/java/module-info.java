module com.tamagochisensoo.www {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.tamagochisensoo.www to javafx.fxml;
    exports com.tamagochisensoo.www;
}
