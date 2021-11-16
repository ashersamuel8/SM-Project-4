module com.project4.project_4_group_81 {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens com.project4.project_4_group_81 to javafx.fxml;
    exports com.project4.project_4_group_81;
}