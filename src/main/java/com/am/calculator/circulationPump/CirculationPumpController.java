package com.am.calculator.circulationPump;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.regex.Pattern;

public class CirculationPumpController {
    CirculationPumpService circulationPumpService = new CirculationPumpService();
    @FXML
    private TextField installationPower;
    @FXML
    private TextField flowTemperature;
    @FXML
    private TextField returnTemperature;
    @FXML
    private Label pumpPerformance;
    @FXML
    private TextField linearPressureDrop;
    @FXML
    private TextField pipesLength;
    @FXML
    private TextField localResistanceFactor;
    @FXML
    private Label pumpHead;

    @FXML
    public void initialize() {
        addBindings();
        addListeners();
    }

    public void addBindings() {
        installationPower.textProperty().bindBidirectional(circulationPumpService.getInstallationPowerProperty());
        flowTemperature.textProperty().bindBidirectional(circulationPumpService.getFlowTemperatureProperty());
        returnTemperature.textProperty().bindBidirectional(circulationPumpService.getReturnTemperatureProperty());
        pumpPerformance.textProperty().bindBidirectional(circulationPumpService.getPumpPerformanceProperty());
        linearPressureDrop.textProperty().bindBidirectional(circulationPumpService.getLinearPressureDropProperty());
        pipesLength.textProperty().bindBidirectional(circulationPumpService.getPipesLengthProperty());
        localResistanceFactor.textProperty().bindBidirectional(circulationPumpService.getLocalResistanceFactorProperty());
        pumpHead.textProperty().bindBidirectional(circulationPumpService.getPumpHeadProperty());
    }

    public void addListeners() {
        List<TextField> pumpPerformanceFields = List.of(installationPower, flowTemperature, returnTemperature);
        List<TextField> pumpHeadFields = List.of(pipesLength, localResistanceFactor);

        for (TextField field : pumpPerformanceFields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.isEmpty() || !(Pattern.matches("\\d+", newValue))) {
                    field.setText("0");
                } else if (newValue.startsWith("0") && newValue.length() > 1) {
                    field.setText(String.valueOf(newValue.charAt(1)));
                }
                circulationPumpService.calculatePumpPerformance();
            });
        }

        linearPressureDrop.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() || !(Pattern.matches("\\d+", newValue))) {
                linearPressureDrop.setText("0");
            } else if (newValue.startsWith("0") && newValue.length() > 1) {
                linearPressureDrop.setText(String.valueOf(newValue.charAt(1)));
            }
            circulationPumpService.calculatePumpHead();
        });

        for (TextField field : pumpHeadFields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!Pattern.matches("(\\d+,\\d+)|(\\d+.\\d+)|\\d+|(\\d+,)|(\\d+\\.)|\\d+", newValue)) {
                    field.setText("0");
                } else if (newValue.startsWith("0") && newValue.length() > 1 && Pattern.matches("\\d+", newValue)) {
                    field.setText(String.valueOf(newValue.charAt(1)));
                }
                circulationPumpService.calculatePumpHead();
            });
        }
    }

}
