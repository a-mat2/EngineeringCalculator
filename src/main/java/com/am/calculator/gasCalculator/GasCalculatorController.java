package com.am.calculator.gasCalculator;

import com.am.calculator.gasCalculator.GasCalculatorService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.regex.Pattern;

public class GasCalculatorController {

    GasCalculatorService gasCalculatorService = new GasCalculatorService();
    @FXML
    private TextField gasFlow;
    @FXML
    private TextField powerOfGasAppliances;
    @FXML
    private Label results;
    @FXML
    private Label incorrectResults;

    @FXML
    public void initialize() {
        addBindings();
        addListeners();
        incorrectResults.setStyle(
                "-fx-text-fill: " + toRgbString(Color.valueOf("red"))
        );
    }

    private void addBindings() {
        gasFlow.textProperty().bindBidirectional(gasCalculatorService.getGasFlowProperty());
        powerOfGasAppliances.textProperty().bindBidirectional(gasCalculatorService.getPowerOfGasAppliancesProperty());
        results.textProperty().bindBidirectional(gasCalculatorService.getResultsProperty());
        incorrectResults.textProperty().bindBidirectional(gasCalculatorService.getIncorrectResultsProperty());
    }

    public void addListeners() {
        addSingleListener(gasFlow);
        addSingleListener(powerOfGasAppliances);
    }

    private void addSingleListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() || !(Pattern.matches("(\\d+,\\d+)|(\\d+.\\d+)|\\d+|(\\d+,)|(\\d+\\.)", newValue))) {
                textField.setText("0");
            } else if (newValue.startsWith("0") && newValue.length() > 1 && Pattern.matches("\\d+", newValue))
                textField.setText(String.valueOf(newValue.charAt(1)));
        });
    }

    public void gasFlow() {
        gasCalculatorService.calculatePowerOfAppliances();
        gasCalculatorService.calculateDiameters();
    }

    public void powerOfGasAppliances() {
        gasCalculatorService.calculateGasFlow();
        gasCalculatorService.calculateDiameters();
    }

    private String toRgbString(Color c) {
        return "rgb("
                + to255Int(c.getRed())
                + "," + to255Int(c.getGreen())
                + "," + to255Int(c.getBlue())
                + ")";
    }

    private int to255Int(double d) {
        return (int) (d * 255);
    }
}