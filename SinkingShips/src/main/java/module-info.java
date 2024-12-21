module com.example.sinkingships {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sinkingships to javafx.fxml;
    exports com.example.sinkingships;
}