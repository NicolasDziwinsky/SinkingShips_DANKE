module com.example.sinkingships {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.compiler;


    opens com.example.sinkingships to javafx.fxml;
    exports com.example.sinkingships;
}