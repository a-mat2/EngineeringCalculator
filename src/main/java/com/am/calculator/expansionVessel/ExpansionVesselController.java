package com.am.calculator.expansionVessel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class ExpansionVesselController {

    ExpansionVesselService expansionVesselService = new ExpansionVesselService();
    @FXML
    private TextField installationPower;
    @FXML
    private ComboBox<String> flowTemperature;
    @FXML
    private ComboBox<String> finalPressure;
    @FXML
    private TextField staticHeight;
    @FXML
    private ComboBox<String> heatingType;
    @FXML
    private Label waterPerPower;
    @FXML
    private Label waterCapacity;
    @FXML
    private Label initialPressure;
    @FXML
    private Label expansionVolume;
    @FXML
    private Label effectivenessRatio;
    @FXML
    private Label requiredVesselCapacity;

    @FXML
    public void initialize() {
        addFlowTemperatures();
        addOpeningPressures();
        addHeatingTypes();
        addBindings();
        addListeners();
    }

    private void addBindings() {
        installationPower.textProperty().bindBidirectional(expansionVesselService.getInstallationPowerProperty());
        staticHeight.textProperty().bindBidirectional(expansionVesselService.getStaticHeightProperty());
        waterPerPower.textProperty().bindBidirectional(expansionVesselService.getWaterPerPowerProperty());
        waterCapacity.textProperty().bindBidirectional(expansionVesselService.getWaterCapacityProperty());
        initialPressure.textProperty().bindBidirectional(expansionVesselService.getInitialPressureProperty());
        expansionVolume.textProperty().bindBidirectional(expansionVesselService.getExpansionVolumeProperty());
        effectivenessRatio.textProperty().bindBidirectional(expansionVesselService.getEffectivenessRatioProperty());
        requiredVesselCapacity.textProperty().bindBidirectional(expansionVesselService.getRequiredVesselCapacityProperty());
    }

    private void addListeners() {
        installationPower.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() || !(Pattern.matches("\\d+", newValue))) {
                installationPower.setText("0");
            } else if (newValue.startsWith("0") && newValue.length() > 1) {
                installationPower.setText(String.valueOf(newValue.charAt(1)));
            }
            expansionVesselService.calculateWaterCapacity();
            expansionVesselService.calculateExpansionVolume();
        });

        staticHeight.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Pattern.matches("(\\d+,\\d+)|(\\d+.\\d+)|\\d+|(\\d+,)|(\\d+\\.)|\\d+", newValue)) {
                staticHeight.setText("0");
            } else if (newValue.startsWith("0") && newValue.length() > 1 && Pattern.matches("\\d+", newValue)) {
                staticHeight.setText(String.valueOf(newValue.charAt(1)));
            }
            expansionVesselService.setInitialPressureProperty();
            expansionVesselService.calculateEffectivenessRatio();
        });
    }

    private void addFlowTemperatures() {
        expansionVesselService.fillFlowTemperaturesList();
        flowTemperature.setItems(expansionVesselService.getFlowTemperaturesListProperty());
        flowTemperature.getSelectionModel().select(8);
        expansionVesselService.setFlowTemperature(flowTemperature.getSelectionModel().getSelectedItem());
    }

    private void addOpeningPressures() {
        expansionVesselService.fillFinalPressuresList();
        finalPressure.setItems(expansionVesselService.getFinalPressuresListProperty());
        finalPressure.getSelectionModel().select(3);
        expansionVesselService.setFinalPressure(finalPressure.getSelectionModel().getSelectedItem());
        expansionVesselService.calculateEffectivenessRatio();
    }

    private void addHeatingTypes() {
        expansionVesselService.fillHeatingTypesList();
        heatingType.setItems(expansionVesselService.getHeatingTypesListProperty());
        heatingType.getSelectionModel().select(0);
        expansionVesselService.setHeatingType(heatingType.getSelectionModel().getSelectedItem());
        expansionVesselService.setWaterPerPowerProperty();
    }

    public void selectFlowTemperature() {
        String selectedFlowTemperature = flowTemperature.getSelectionModel().getSelectedItem();
        expansionVesselService.setFlowTemperature(selectedFlowTemperature);
        expansionVesselService.setVolumeIncrease();
        expansionVesselService.calculateExpansionVolume();
    }

    public void selectFinalPressure() {
        String selectedFinalPressure = finalPressure.getSelectionModel().getSelectedItem();
        expansionVesselService.setFinalPressure(selectedFinalPressure);
        expansionVesselService.calculateEffectivenessRatio();
    }

    public void selectHeatingType() {
        String selectedHeatingType = heatingType.getSelectionModel().getSelectedItem();
        expansionVesselService.setHeatingType(selectedHeatingType);
        expansionVesselService.setWaterPerPowerProperty();
        expansionVesselService.calculateWaterCapacity();
        expansionVesselService.calculateExpansionVolume();
    }

}