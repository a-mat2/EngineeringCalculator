package com.am.calculator.waterCalculator;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.am.calculator.CalculationSupport.prepareStringToCalculate;
import static java.lang.Double.parseDouble;

public class WaterCalculatorService {
    private StringProperty washbasinProperty = new SimpleStringProperty(this, "washbasinAmount", "0");
    private StringProperty sinkProperty = new SimpleStringProperty(this, "sinkAmount", "0");
    private StringProperty bathProperty = new SimpleStringProperty(this, "bathAmount", "0");
    private StringProperty showerProperty = new SimpleStringProperty(this, "showerAmount", "0");
    private StringProperty washingMachineProperty = new SimpleStringProperty(this, "washingMachineAmount", "0");
    private StringProperty dishwasherProperty = new SimpleStringProperty(this, "dishwasherAmount", "0");
    private StringProperty urinalProperty = new SimpleStringProperty(this, "urinalAmount", "0");
    private StringProperty toiletBowlProperty = new SimpleStringProperty(this, "toiletBowlAmount", "0");
    private ObservableList<String> buildingTypesList = FXCollections.observableArrayList();
    private ObjectProperty<String> buildingType = new SimpleObjectProperty<>();
    private ObservableList<String> pipeTypesList = FXCollections.observableArrayList();
    private ObjectProperty<String> pipeType = new SimpleObjectProperty<>();
    private StringProperty maxSpeedProperty = new SimpleStringProperty(this, "maxSpeed", "1.50");
    private StringProperty normativeOutflow = new SimpleStringProperty(this, "normativeOutflow", "0.00");
    private StringProperty normativeOutflowDisplay = new SimpleStringProperty(this, "normativeOutflowDisplay", "0.00 dm³/s");
    private StringProperty waterFlux = new SimpleStringProperty(this, "waterFlux", "0.00 dm³/s = 0,00 m³/h");
    private StringProperty waterFluxDisplay = new SimpleStringProperty(this, "waterFluxDisplay", "0.00");
    private StringProperty diameterProperty = new SimpleStringProperty(this, "diameter", "-");
    private StringProperty flowSpeedProperty = new SimpleStringProperty(this, "flowSpeed", "0.00 m/s");

    private final List<String> PEX_PIPES = List.of(
            "dn 16x2,0|16.0|2.0",
            "dn 20x2,0|20.0|2.0",
            "dn 25x2,5|25.0|2.5",
            "dn 32x3,0|32.0|3.0",
            "dn 40x3,5|40.0|3.5",
            "dn 50x4,0|50.0|4.0",
            "dn 63x4,5|63.0|4.5");

    private final List<String> PP_PIPES = List.of(
            "dn 16x2,7|16.0|2.7",
            "dn 20x3,4|20.0|3.4",
            "dn 25x4,2|25.0|4.2",
            "dn 32x5,4|32.0|5.4",
            "dn 40x6,7|40.0|6.7",
            "dn 50x8,3|50.0|8.3",
            "dn 63x10,5|63.0|10.5",
            "dn 75x12,5|75.0|12.5",
            "dn 90x15,0|90.0|15.0",
            "dn 110x18,3|110.0|18.3");

    private final List<String> STEEL_PIPES = List.of(
            "DN 15|21.3|2.0",
            "DN 20|26.9|2.3",
            "DN 25|33.7|2.6",
            "DN 32|42.4|2.6",
            "DN 40|48.3|2.6",
            "DN 50|60.3|2.9",
            "DN 65|76.1|2.9",
            "DN 80|88.9|3.2",
            "DN 100|114.3|3.6",
            "DN 125|139.7|4.0");

    StringProperty getWashbasinProperty() {
        return washbasinProperty;
    }

    StringProperty getSinkProperty() {
        return sinkProperty;
    }

    StringProperty getBathProperty() {
        return bathProperty;
    }

    StringProperty getShowerProperty() {
        return showerProperty;
    }

    StringProperty getWashingMachineProperty() {
        return washingMachineProperty;
    }

    StringProperty getDishwasherProperty() {
        return dishwasherProperty;
    }

    StringProperty getUrinalProperty() {
        return urinalProperty;
    }

    StringProperty getToiletBowlProperty() {
        return toiletBowlProperty;
    }

    ObservableList<String> getBuildingTypesList() {
        return buildingTypesList;
    }

    void setBuildingType(String buildingType) {
        this.buildingType.set(buildingType);
    }

    ObservableList<String> getPipeTypesList() {
        return pipeTypesList;
    }

    void setPipeType(String pipeType) {
        this.pipeType.set(pipeType);
    }

    StringProperty getMaxSpeedProperty() {
        return maxSpeedProperty;
    }

    StringProperty getDiameterProperty() {
        return diameterProperty;
    }

    StringProperty getFlowSpeedProperty() {
        return flowSpeedProperty;
    }

    void fulfillBuildingTypesList() {
        buildingTypesList.add(0, "Budynki mieszkalne");
        buildingTypesList.add(1, "Budynki biurowe i administracyjne");
        buildingTypesList.add(2, "Hotele");
        buildingTypesList.add(3, "Domy towarowe");
        buildingTypesList.add(4, "Szpitale");
        buildingTypesList.add(5, "Szkoły");
    }

    void fulfillPipeTypesList() {
        pipeTypesList.addAll(Arrays.asList("PEX", "PP", "Stal"));
    }

    double calculateNormativeOutflow() {
        double normativeOutflow = parseDouble(getWashbasinProperty().getValue()) * 0.07 +
                parseDouble(getSinkProperty().getValue()) * 0.07 +
                parseDouble(getBathProperty().getValue()) * 0.15 +
                parseDouble(getShowerProperty().getValue()) * 0.15 +
                parseDouble(getWashingMachineProperty().getValue()) * 0.25 +
                parseDouble(getDishwasherProperty().getValue()) * 0.15 +
                parseDouble(getUrinalProperty().getValue()) * 0.30 +
                parseDouble(getToiletBowlProperty().getValue()) * 0.13;
        this.normativeOutflow.set(String.valueOf(normativeOutflow));
        this.normativeOutflowDisplay.set(String.format("%.2f dm³/s", normativeOutflow));
        return normativeOutflow;
    }

    StringProperty getNormativeOutflow() {
        calculateNormativeOutflow();
        return this.normativeOutflowDisplay;
    }

    void calculateWaterFlux() {
        double waterFlux = 0;
        double normativeOutflow = parseDouble(prepareStringToCalculate(this.normativeOutflow.getValue()));
        switch (buildingType.getValue()) {
            case "Budynki mieszkalne":
                if (normativeOutflow <= 20) {
                    waterFlux = (0.682 * Math.pow(normativeOutflow, 0.45)) - 0.14;
                } else {
                    waterFlux = (1.7 * Math.pow(normativeOutflow, 0.21)) - 0.7;
                }
                break;
            case "Budynki biurowe i administracyjne":
                if (normativeOutflow <= 20) {
                    waterFlux = (0.682 * Math.pow(normativeOutflow, 0.45)) - 0.14;
                } else {
                    waterFlux = (0.4 * Math.pow(normativeOutflow, 0.54)) + 0.48;
                }
                break;
            case "Hotele":
                if (normativeOutflow <= 20) {
                    waterFlux = (0.698 * Math.pow(normativeOutflow, 0.5)) - 0.12;
                } else {
                    waterFlux = (1.08 * Math.pow(normativeOutflow, 0.5)) - 1.83;
                }
                break;
            case "Domy towarowe":
                if (normativeOutflow <= 20) {
                    waterFlux = (0.698 * Math.pow(normativeOutflow, 0.5)) - 0.12;
                } else {
                    waterFlux = (4.3 * Math.pow(normativeOutflow, 0.27)) - 6.65;
                }
                break;
            case "Szpitale":
                if (normativeOutflow <= 20) {
                    waterFlux = (0.698 * Math.pow(normativeOutflow, 0.5)) - 0.12;
                } else {
                    waterFlux = (0.25 * Math.pow(normativeOutflow, 0.65)) + 1.25;
                }
                break;
            case "Szkoły":
                if (normativeOutflow <= 1.5) {
                    waterFlux = normativeOutflow;
                } else if (normativeOutflow > 1.5 && normativeOutflow <= 20) {
                    waterFlux = (4.4 * Math.pow(normativeOutflow, 0.27)) - 3.41;
                } else {
                    waterFlux = (-22.5 * Math.pow(normativeOutflow, -0.5)) + 11.5;
                }
                break;
        }
        if (waterFlux < 0) waterFlux = 0;
        this.waterFlux.set(String.valueOf(waterFlux));
        this.waterFluxDisplay.set(String.format("%.2f dm³/s = %.2f m³/h", waterFlux, (waterFlux * 3600 / 1000)));
    }

    StringProperty getWaterFluxProperty() {
        calculateWaterFlux();
        return waterFluxDisplay;
    }

    void calculateDiameter() {
        String currentDiameter = null;
        double currentFlowSpeed = 0;
        List<String> pipeDiameters =  switch(pipeType.getValue()) {
            case "PEX" -> PEX_PIPES;
            case "PP" -> PP_PIPES;
            case "Stal" -> STEEL_PIPES;
            default -> throw new IllegalStateException("Unexpected value: " + pipeType.getValue());
        };
        if (maxSpeedProperty.getValue().isEmpty()) {
            maxSpeedProperty.setValue("1.5");
        }
        if (parseDouble(normativeOutflow.getValue()) > 0) {
            for (String diameter : pipeDiameters) {
                String[] splitDiameter = diameter.split("\\|");
                double innerDiameter = calculateInnerSection(splitDiameter[1], splitDiameter[2]);
                double flowSpeed = (parseDouble(waterFlux.getValue()) / 1000) / innerDiameter;
                if (flowSpeed <= parseDouble(prepareStringToCalculate(maxSpeedProperty.getValue()))) {
                    currentDiameter = splitDiameter[0];
                    currentFlowSpeed = flowSpeed;
                    break;
                }
            }
        }
        diameterProperty.set(Objects.requireNonNullElse(currentDiameter, "-"));
        if (currentFlowSpeed != 0) {
            flowSpeedProperty.set(String.format("%.2f m/s", currentFlowSpeed));
        } else {
            flowSpeedProperty.set("0,00 m/s");
        }
    }

    double calculateInnerSection(String diameter, String wallThickness) {
            double innerDiameter = (parseDouble(diameter) - 2 * parseDouble(wallThickness)) / 1000;
        return 3.14 * Math.pow(innerDiameter, 2) / 4;
    }

}
