package com.am.calculator.gasCabinetVentilation;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GasCabinetVentilationService {
    private StringProperty cabinetWidthProperty = new SimpleStringProperty(this, "cabinetWidth", "0");
    private StringProperty cabinetDepthProperty = new SimpleStringProperty(this, "cabinetDepth", "0");
    private StringProperty holeDiameterProperty = new SimpleStringProperty(this, "holeDiameter", "0");
    private StringProperty numberOfHoles = new SimpleStringProperty(this, "numberOfHoles", "0");

    StringProperty getCabinetWidthProperty() {
        return cabinetWidthProperty;
    }

    StringProperty getCabinetDepthProperty() {
        return cabinetDepthProperty;
    }

    StringProperty getHoleDiameterProperty() {
        return holeDiameterProperty;
    }

    StringProperty getNumberOfHolesProperty() {
        return numberOfHoles;
    }

    void calculateNumberOfHoles() {
        double width = Double.parseDouble(cabinetWidthProperty.getValue()) / 1000;
        double depth = Double.parseDouble(cabinetDepthProperty.getValue()) / 1000;
        double holeDiameter = Double.parseDouble(holeDiameterProperty.getValue()) / 1000;
        double holeArea = Math.PI * Math.pow(holeDiameter / 2, 2);
        double horizontalCrossSection = width * depth;
        int numberOfHoles = (int)(0.02 * horizontalCrossSection / holeArea) + 1;
        if (holeDiameter > 0) {
            this.numberOfHoles.setValue(String.valueOf(numberOfHoles));
        } else {
            this.numberOfHoles.setValue("0");
        }
    }
}
