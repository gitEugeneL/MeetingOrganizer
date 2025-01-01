module com.euglihon.meetingorganizer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.euglihon.meetingorganizer to javafx.fxml;
    exports com.euglihon.meetingorganizer;
    exports com.euglihon.meetingorganizer.controller;
    opens com.euglihon.meetingorganizer.controller to javafx.fxml;
}