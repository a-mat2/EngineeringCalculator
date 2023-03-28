package com.am.calculator.expansionVessel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;

import static com.am.calculator.CalculationSupport.prepareStringToCalculate;
import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;

public class ExpansionVesselService {

    private final StringProperty installationPowerProperty = new SimpleStringProperty(this, "installationPower", "0");
    private final ObservableList<String> flowTemperaturesList = FXCollections.observableArrayList();
    private final ObservableList<String> finalPressuresListProperty = FXCollections.observableArrayList();
    private final StringProperty staticHeightProperty = new SimpleStringProperty(this, "staticHeight", "0");
    private final ObservableList<String> heatingTypesListProperty = FXCollections.observableArrayList();
    private final ObjectProperty<String> heatingType = new SimpleObjectProperty<>();
    private final StringProperty waterPerPowerProperty = new SimpleStringProperty(this, "waterPerPower", "-");
    private final StringProperty waterCapacityProperty = new SimpleStringProperty(this, "waterCapacity", "-");
    private final StringProperty initialPressureProperty = new SimpleStringProperty(this, "initialPressure", "0,5 bar");
    private final StringProperty expansionVolumeProperty = new SimpleStringProperty(this, "expansionVolume", "-");
    private final StringProperty effectivenessRatioProperty = new SimpleStringProperty(this, "effectivenessRatio", "-");
    private final StringProperty requiredVesselCapacityProperty = new SimpleStringProperty(this, "requiredVesselCapacity", "-");

    private double waterPerPower;
    private double volumeIncrease;
    private int flowTemperature;
    private double waterCapacity;
    private double initialPressure = 0.5;
    private double finalPressure = 3.0;
    private double effectivenessRatio = 0;
    private double expansionVolume = 0;

    void fillFlowTemperaturesList() {
        flowTemperaturesList.addAll(Arrays.asList("25", "35", "40", "45", "50", "55", "60",
                "70", "80", "90", "100", "110"));
    }

    void fillFinalPressuresList() {
        finalPressuresListProperty.addAll(Arrays.asList("1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5",
                "5.0", "5.5", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5", "9.0", "9.5", "10.0"));
    }

    void fillHeatingTypesList() {
        heatingTypesListProperty.addAll(Arrays.asList(
                "Konwektory i/lub nagrzewnice powietrza",
                "Urządzenia indukcyjne",
                "Instalacje uzdatniania powietrza",
                "Grzejniki płytowe",
                "Kombinowane instalacje centralnego ogrzewania",
                "Grzejniki członowe",
                "Kombinowane instalacje chłodzenia",
                "Ogrzewanie sufitowe i/lub ogrzewanie podłogowe",
                "Rozległe systemy rur (sieć ciepłownicza)"
        ));
    }

    StringProperty getInstallationPowerProperty() {
        return installationPowerProperty;
    }

    StringProperty getStaticHeightProperty() {
        return staticHeightProperty;
    }

    StringProperty getWaterPerPowerProperty() {
        return waterPerPowerProperty;
    }

    StringProperty getWaterCapacityProperty() {
        return waterCapacityProperty;
    }

    StringProperty getInitialPressureProperty() {
        return initialPressureProperty;
    }

    StringProperty getExpansionVolumeProperty() {
        return expansionVolumeProperty;
    }

    StringProperty getEffectivenessRatioProperty() {
        return effectivenessRatioProperty;
    }

    StringProperty getRequiredVesselCapacityProperty() {
        return requiredVesselCapacityProperty;
    }

    ObservableList<String> getFinalPressuresListProperty() {
        return finalPressuresListProperty;
    }

    ObservableList<String> getHeatingTypesListProperty() {
        return heatingTypesListProperty;
    }

    void setFinalPressure(String finalPressure) {
        try {
            this.finalPressure = parseFloat(finalPressure);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Unexpected Value: " + finalPressure + ". Error message: " + e.getMessage());
        }

    }

    void setHeatingType(String heatingType) {
        this.heatingType.set(heatingType);
    }

    void setFlowTemperature(String flowTemperature) {
        this.flowTemperature = Integer.parseInt(flowTemperature);
        setVolumeIncrease();
    }

    void setInitialPressure(Double initialPressure) {
        this.initialPressure = initialPressure;
    }

    ObservableList<String> getFlowTemperaturesListProperty() {
        return flowTemperaturesList;
    }

    void setWaterPerPowerProperty() {
        double heatingTypeValue = switch (this.heatingType.getValue()) {
            case "Konwektory i/lub nagrzewnice powietrza" -> 5.2;
            case "Urządzenia indukcyjne" -> 5.5;
            case "Instalacje uzdatniania powietrza" -> 6.9;
            case "Grzejniki płytowe" -> 8.8;
            case "Kombinowane instalacje centralnego ogrzewania" -> 10.0;
            case "Grzejniki członowe" -> 12.0;
            case "Kombinowane instalacje chłodzenia" -> 20.0;
            case "Ogrzewanie sufitowe i/lub ogrzewanie podłogowe" -> 18.5;
            case "Rozległe systemy rur (sieć ciepłownicza)" -> 25.8;
            default -> throw new IllegalStateException("Unexpected value: " + heatingType.getValue());
        };
        this.waterPerPowerProperty.set(String.format("%.1f dm³/kW", heatingTypeValue));
        this.waterPerPower = heatingTypeValue;
    }

    void calculateWaterCapacity() {
        int installationPower = Integer.parseInt(installationPowerProperty.getValue());
        if (installationPower > 0) {
            double waterCapacity = installationPower * waterPerPower;
            this.waterCapacityProperty.setValue(String.format("%.1f dm³", waterCapacity));
            this.waterCapacity = waterCapacity;
        } else {
            this.waterCapacityProperty.setValue("-");
            this.waterCapacity = 0;
        }
    }

    private final int[] pressureBoundaryValues = {3, 8, 13, 18, 23, 27, 32, 38, 43, 48, 53, 58, 63, 68, 73};
    private final double[] initialPressureValues = {0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0,
            4.5, 5.0, 5.5, 6.0, 6.5, 7.0, 7.5};
    void setInitialPressureProperty() {
        double staticHeight = parseDouble(prepareStringToCalculate(staticHeightProperty.getValue()));
        double initialPressure = 0.5;
        for (int i = 0; i < pressureBoundaryValues.length; i++) {
            if (staticHeight <= pressureBoundaryValues[i]) {
                initialPressure = initialPressureValues[i];
                break;
            } else if (i == pressureBoundaryValues.length - 1) {
                initialPressure = 8.0;
            }
        }
        initialPressureProperty.setValue(String.format("%.1f bar", initialPressure));
        setInitialPressure(initialPressure);
    }

    void setVolumeIncrease() {
        this.volumeIncrease = switch (this.flowTemperature) {
            case 25 -> 0.35;
            case 35 -> 0.63;
            case 40 -> 0.75;
            case 45 -> 0.96;
            case 50 -> 1.18;
            case 55 -> 1.42;
            case 60 -> 1.68;
            case 70 -> 2.25;
            case 80 -> 2.89;
            case 90 -> 3.58;
            case 100 -> 4.34;
            case 110 -> 5.16;
            default -> throw new IllegalStateException("Unexpected value: " + heatingType.getValue());
        };
    }

    void calculateExpansionVolume() {
        int installationPower = Integer.parseInt(installationPowerProperty.getValue());
        if (installationPower > 0) {
            double expansionVolume = (waterCapacity * volumeIncrease * 0.9997) / 100;
            expansionVolumeProperty.setValue(String.format("%.1f dm³", expansionVolume));
            this.expansionVolume = expansionVolume;
        } else {
            this.expansionVolumeProperty.setValue("-");
            this.expansionVolume = 0;
        }
        calculateRequiredVesselCapacity();
    }

    void calculateEffectivenessRatio() {
        double effectivenessRatio = ((finalPressure + 1) - (initialPressure + 1)) / (finalPressure + 1);
        if (effectivenessRatio > 0) {
            effectivenessRatioProperty.setValue(String.format("%.3f", effectivenessRatio));
            this.effectivenessRatio = effectivenessRatio;
        } else {
            effectivenessRatioProperty.setValue("-");
            this.effectivenessRatio = 0;
        }
        calculateRequiredVesselCapacity();
    }

    void calculateRequiredVesselCapacity() {
        if (expansionVolume > 0 && effectivenessRatio > 0) {
            double requiredVesselCapacity = expansionVolume / effectivenessRatio;
            requiredVesselCapacityProperty.setValue(String.format("%.1f dm³", requiredVesselCapacity));
        } else {
            requiredVesselCapacityProperty.setValue("-");
        }
    }
}
