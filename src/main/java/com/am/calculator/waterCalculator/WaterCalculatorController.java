package com.am.calculator.waterCalculator;

import com.am.calculator.waterCalculator.WaterCalculatorService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class WaterCalculatorController {
    WaterCalculatorService waterCalculatorService = new WaterCalculatorService();
    List<TextField> listOfFields = new ArrayList<>();
    @FXML
    private TextField washbasin;
    @FXML
    private TextField sink;
    @FXML
    private TextField bath;
    @FXML
    private TextField shower;
    @FXML
    private TextField washingMachine;
    @FXML
    private TextField dishwasher;
    @FXML
    private TextField urinal;
    @FXML
    private TextField toiletBowl;
    @FXML
    private ComboBox<String> buildingType;
    @FXML
    private ComboBox<String> pipeType;
    @FXML
    private TextField maxSpeed;
    @FXML
    private Label normativeOutflow;
    @FXML
    private Label waterFlux;
    @FXML
    private Label diameter;
    @FXML
    private Label flowSpeed;

    @FXML
    public void initialize() {
        addBuildingTypes();
        addPipeTypes();
        addBindings();
        addListenersToTheFields();
    }

    public void addBindings() {
        normativeOutflow.textProperty().bindBidirectional(waterCalculatorService.getNormativeOutflow());
        waterFlux.textProperty().bindBidirectional(waterCalculatorService.getWaterFluxProperty());
        diameter.textProperty().bindBidirectional(waterCalculatorService.getDiameterProperty());
        flowSpeed.textProperty().bindBidirectional(waterCalculatorService.getFlowSpeedProperty());
        washbasin.textProperty().bindBidirectional(waterCalculatorService.getWashbasinProperty());
        sink.textProperty().bindBidirectional(waterCalculatorService.getSinkProperty());
        bath.textProperty().bindBidirectional(waterCalculatorService.getBathProperty());
        shower.textProperty().bindBidirectional(waterCalculatorService.getShowerProperty());
        washingMachine.textProperty().bindBidirectional(waterCalculatorService.getWashingMachineProperty());
        dishwasher.textProperty().bindBidirectional(waterCalculatorService.getDishwasherProperty());
        urinal.textProperty().bindBidirectional(waterCalculatorService.getUrinalProperty());
        toiletBowl.textProperty().bindBidirectional(waterCalculatorService.getToiletBowlProperty());
        maxSpeed.textProperty().bindBidirectional(waterCalculatorService.getMaxSpeedProperty());
    }

    public void addListenersToTheFields() {
        listOfFields.addAll(Arrays.asList(washbasin, sink, bath, shower, washingMachine, dishwasher, urinal, toiletBowl));

        for (TextField field : listOfFields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.isEmpty() || !(Pattern.matches("\\d+", newValue))) {
                    field.setText("0");
                } else if (newValue.startsWith("0") && newValue.length() > 1) {
                    field.setText(String.valueOf(newValue.charAt(1)));
                }
                waterCalculatorService.calculateNormativeOutflow();
                waterCalculatorService.calculateWaterFlux();
                waterCalculatorService.calculateDiameter();
            });
        }

        maxSpeed.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Pattern.matches("(\\d+,\\d+)|(\\d+.\\d+)|\\d+|(\\d+,)|(\\d+\\.)|\\d+", newValue) && !newValue.isEmpty()) {
                maxSpeed.setText("1.5");
            } else if (newValue.startsWith("0") && newValue.length() > 1 && Pattern.matches("\\d+", newValue)) {
                maxSpeed.setText(String.valueOf(newValue.charAt(1)));
            }
            if (!newValue.isEmpty()) {
                waterCalculatorService.calculateDiameter();
            }
        });
    }

    public void addBuildingTypes() {
        waterCalculatorService.fulfillBuildingTypesList();
        buildingType.setItems(waterCalculatorService.getBuildingTypesList());
        buildingType.getSelectionModel().select(0);
        waterCalculatorService.setBuildingType(buildingType.getSelectionModel().getSelectedItem());
    }

    public void addPipeTypes() {
        waterCalculatorService.fulfillPipeTypesList();
        pipeType.setItems(waterCalculatorService.getPipeTypesList());
        pipeType.getSelectionModel().select(0);
        waterCalculatorService.setPipeType(pipeType.getSelectionModel().getSelectedItem());
    }

    public void resetAmount() {
        listOfFields.forEach(f -> f.setText("0"));
    }

    public void selectBuildingType() {
        String selectedBuildingType = buildingType.getSelectionModel().getSelectedItem();
        waterCalculatorService.setBuildingType(selectedBuildingType);
        waterCalculatorService.calculateWaterFlux();
    }

    public void selectPipeType() {
        String selectedPipeType = pipeType.getSelectionModel().getSelectedItem();
        waterCalculatorService.setPipeType(selectedPipeType);
        waterCalculatorService.calculateDiameter();
    }

}