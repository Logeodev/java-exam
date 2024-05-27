module com.tamagochisensoo.www {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.tamagochisensoo.www to javafx.fxml;
    exports com.tamagochisensoo.www;
}
