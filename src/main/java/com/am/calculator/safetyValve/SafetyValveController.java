package com.am.calculator.safetyValve;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class SafetyValveController {
    SafetyValveService safetyValveService = new SafetyValveService();
    @FXML
    private TextField gasBoilerPower;
    @FXML
    private ComboBox<String> openingPressure;
    @FXML
    private Label safetyValveDiameter;

    @FXML
    public void initialize() {
        addOpeningPressures();
        addBindings();
        addGasBoilerPowerListener();
    }

    public void addBindings() {
        gasBoilerPower.textProperty().bindBidirectional(safetyValveService.gasBoilerPowerProperty());
        safetyValveDiameter.textProperty().bindBidirectional(safetyValveService.safetyValveDiameterProperty());
    }

    public void addGasBoilerPowerListener() {
        gasBoilerPower.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() || !(Pattern.matches("\\d+", newValue))) {
                gasBoilerPower.setText("0");
            } else if (newValue.startsWith("0") && newValue.length() > 1) {
                gasBoilerPower.setText(String.valueOf(newValue.charAt(1)));
            }
            safetyValveService.calculateSafetyValveDiameter();
        });
    }

    public void addOpeningPressures() {
        safetyValveService.fillOpeningPressuresList();
        openingPressure.setItems(safetyValveService.getOpeningPressuresListProperty());
        openingPressure.getSelectionModel().select(3);
        safetyValveService.setOpeningPressure(openingPressure.getSelectionModel().getSelectedItem());
    }

    public void selectOpeningPressure() {
        safetyValveService.setOpeningPressure(openingPressure.getSelectionModel().getSelectedItem());
        safetyValveService.calculateSafetyValveDiameter();
    }
}
