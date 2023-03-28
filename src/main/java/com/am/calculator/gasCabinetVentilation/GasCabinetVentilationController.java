package com.am.calculator.gasCabinetVentilation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.regex.Pattern;


public class GasCabinetVentilationController {
    GasCabinetVentilationService gasCabinetVentilationService = new GasCabinetVentilationService();
    @FXML
    private TextField cabinetWidth;
    @FXML
    private TextField cabinetDepth;
    @FXML
    private TextField holeDiameter;
    @FXML
    private Label numberOfHoles;

    @FXML
    public void initialize() {
        addBindings();
        addListeners();
    }

    private void addBindings() {
        cabinetWidth.textProperty().bindBidirectional(gasCabinetVentilationService.getCabinetWidthProperty());
        cabinetDepth.textProperty().bindBidirectional(gasCabinetVentilationService.getCabinetDepthProperty());
        holeDiameter.textProperty().bindBidirectional(gasCabinetVentilationService.getHoleDiameterProperty());
        numberOfHoles.textProperty().bindBidirectional(gasCabinetVentilationService.getNumberOfHolesProperty());
    }

    private void addListeners() {
        List<TextField> listOfFields = List.of(cabinetWidth, cabinetDepth, holeDiameter);
        for (TextField field : listOfFields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.isEmpty() || !(Pattern.matches("\\d+", newValue))) {
                    field.setText("0");
                } else if (newValue.startsWith("0") && newValue.length() > 1 && Pattern.matches("\\d+", newValue)) {
                    field.setText(String.valueOf(newValue.charAt(1)));
                }
                gasCabinetVentilationService.calculateNumberOfHoles();
            });
        }
    }

}
