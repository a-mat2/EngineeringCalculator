package com.am.calculator.safetyValve;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SafetyValveService {
    private final List<String> OPENING_PRESSURE_15 = List.of(
            "1/2\"|37",
            "3/4\"|73",
            "1\"|147",
            "1 1/4\"|238",
            "1 1/2\"|216",
            "2\"|564");
    private final List<String> OPENING_PRESSURE_20 = List.of(
            "1/2\"|44",
            "3/4\"|87",
            "1\"|174",
            "1 1/4\"|283",
            "1 1/2\"|257",
            "2\"|671");
    private final List<String> OPENING_PRESSURE_25 = List.of(
            "1/2\"|72",
            "3/4\"|101",
            "1\"|228",
            "1 1/4\"|348",
            "1 1/2\"|803",
            "2\"|892");
    private final List<String> OPENING_PRESSURE_30 = List.of(
            "1/2\"|64",
            "3/4\"|118",
            "1\"|284",
            "1 1/4\"|394",
            "1 1/2\"|910",
            "2\"|1011");
    private final List<String> OPENING_PRESSURE_35 = List.of(
            "1/2\"|64",
            "3/4\"|127",
            "1\"|256",
            "1 1/4\"|414",
            "1 1/2\"|769",
            "2\"|983");
    private final List<String> OPENING_PRESSURE_40 = List.of(
            "1/2\"|71",
            "3/4\"|140",
            "1\"|282",
            "1 1/4\"|457",
            "1 1/2\"|848",
            "2\"|922");
    private final List<String> OPENING_PRESSURE_45 = List.of(
            "1/2\"|78",
            "3/4\"|153",
            "1\"|308",
            "1 1/4\"|499",
            "1 1/2\"|926",
            "2\"|1182");
    private final List<String> OPENING_PRESSURE_50 = List.of(
            "1/2\"|84",
            "3/4\"|166",
            "1\"|395",
            "1 1/4\"|540",
            "1 1/2\"|1003",
            "2\"|1281");
    private final List<String> OPENING_PRESSURE_55 = List.of(
            "1/2\"|150",
            "3/4\"|221",
            "1\"|439",
            "1 1/4\"|582",
            "1 1/2\"|1426",
            "2\"|1980");
    private final List<String> OPENING_PRESSURE_60 = List.of(
            "1/2\"|171",
            "3/4\"|192",
            "1\"|434",
            "1 1/4\"|623",
            "1 1/2\"|1157",
            "2\"|1729");
    private StringProperty gasBoilerPowerProperty = new SimpleStringProperty(this, "gasBoilerPower", "0");
    private ObservableList<String> openingPressuresListProperty = FXCollections.observableArrayList();
    private ObjectProperty<String> openingPressure = new SimpleObjectProperty<>();
    private StringProperty safetyValveDiameterProperty = new SimpleStringProperty(this, "safetyValveDiameter", "-");

    StringProperty gasBoilerPowerProperty() {
        return gasBoilerPowerProperty;
    }

    ObservableList<String> getOpeningPressuresListProperty() {
        return openingPressuresListProperty;
    }

    StringProperty safetyValveDiameterProperty() {
        return safetyValveDiameterProperty;
    }

    void setOpeningPressure(String openingPressure) {
        this.openingPressure.set(openingPressure);
    }

    void fillOpeningPressuresList() {
        openingPressuresListProperty.addAll(Arrays.asList("1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0", "5.5", "6.0"));
    }

    void calculateSafetyValveDiameter() {
        String currentDiameter = null;
        List<String> selectedDiametersList = switch (openingPressure.getValue()) {
            case "1.5" -> OPENING_PRESSURE_15;
            case "2.0" -> OPENING_PRESSURE_20;
            case "2.5" -> OPENING_PRESSURE_25;
            case "3.0" -> OPENING_PRESSURE_30;
            case "3.5" -> OPENING_PRESSURE_35;
            case "4.0" -> OPENING_PRESSURE_40;
            case "4.5" -> OPENING_PRESSURE_45;
            case "5.0" -> OPENING_PRESSURE_50;
            case "5.5" -> OPENING_PRESSURE_55;
            case "6.0" -> OPENING_PRESSURE_60;
            default -> throw new IllegalStateException("Unexpected value: " + openingPressure.getValue());
        };
        if (Integer.parseInt(gasBoilerPowerProperty.getValue()) > 0) {
            for (String diameter : selectedDiametersList) {
                String[] splitDiameter = diameter.split("\\|");
                if (Integer.parseInt(gasBoilerPowerProperty.getValue()) < Integer.parseInt(splitDiameter[1])) {
                    currentDiameter = splitDiameter[0];
                    break;
                }
            }
        }
        safetyValveDiameterProperty.setValue(Objects.requireNonNullElse(currentDiameter, "-"));
    }

}
