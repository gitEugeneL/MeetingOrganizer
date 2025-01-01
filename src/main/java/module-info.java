module com.euglihon.meetingorganizer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.euglihon.meetingorganizer to javafx.fxml;
    exports com.euglihon.meetingorganizer;
}