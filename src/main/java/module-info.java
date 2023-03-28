module com.am.calculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.am.calculator to javafx.fxml;
    exports com.am.calculator;
    exports com.am.calculator.expansionVessel;
    opens com.am.calculator.expansionVessel to javafx.fxml;
    exports com.am.calculator.waterCalculator;
    opens com.am.calculator.waterCalculator to javafx.fxml;
    exports com.am.calculator.gasCalculator;
    opens com.am.calculator.gasCalculator to javafx.fxml;
    exports com.am.calculator.safetyValve;
    opens com.am.calculator.safetyValve to javafx.fxml;
    exports com.am.calculator.gasCabinetVentilation;
    opens com.am.calculator.gasCabinetVentilation to javafx.fxml;
    exports com.am.calculator.circulationPump;
    opens com.am.calculator.circulationPump to javafx.fxml;
}