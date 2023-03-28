package com.am.calculator.circulationPump;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import static com.am.calculator.CalculationSupport.prepareStringToCalculate;

public class CirculationPumpService {
    private StringProperty installationPowerProperty = new SimpleStringProperty(this, "installationPower", "0");
    private StringProperty flowTemperatureProperty = new SimpleStringProperty(this, "flowTemperature", "0");
    private StringProperty returnTemperatureProperty = new SimpleStringProperty(this, "returnTemperature", "0");
    private StringProperty pumpPerformanceProperty = new SimpleStringProperty(this, "pumpPerformance", "-");
    private StringProperty linearPressureDropProperty = new SimpleStringProperty(this, "linearPressureDrop", "0");
    private StringProperty pipesLengthProperty = new SimpleStringProperty(this, "pipesLength", "0");
    private StringProperty localResistanceFactorProperty = new SimpleStringProperty(this, "localResistanceFactor", "0");
    private StringProperty pumpHeadProperty = new SimpleStringProperty(this, "pumpHeadProperty", "-");

    StringProperty getInstallationPowerProperty() {
        return installationPowerProperty;
    }

    StringProperty getFlowTemperatureProperty() {
        return flowTemperatureProperty;
    }

    StringProperty getReturnTemperatureProperty() {
        return returnTemperatureProperty;
    }

    StringProperty getPumpPerformanceProperty() {
        return pumpPerformanceProperty;
    }

    StringProperty getLinearPressureDropProperty() {
        return linearPressureDropProperty;
    }

    StringProperty getPipesLengthProperty() {
        return pipesLengthProperty;
    }

    StringProperty getLocalResistanceFactorProperty() {
        return localResistanceFactorProperty;
    }

    StringProperty getPumpHeadProperty() {
        return pumpHeadProperty;
    }

    void calculatePumpPerformance() {
        double installationPower = Double.parseDouble(installationPowerProperty.getValue());
        double flowTemperature = Double.parseDouble(flowTemperatureProperty.getValue());
        double returnTemperature = Double.parseDouble(returnTemperatureProperty.getValue());
        double tempAmplitude = flowTemperature - returnTemperature;
        if (installationPower > 0 && flowTemperature > 0 && returnTemperature > 0 && tempAmplitude > 0) {
            double pumpPerformance = 0.86 * (installationPower / tempAmplitude);
            pumpPerformanceProperty.setValue(String.format("%.2f", pumpPerformance));
        } else {
            pumpPerformanceProperty.setValue("-");
        }
    }

    void calculatePumpHead() {
        double linearPressureDrop = Double.parseDouble(linearPressureDropProperty.getValue());
        double pipesLength = Double.parseDouble(prepareStringToCalculate(pipesLengthProperty.getValue()));
        double localResistanceFactor = Double.parseDouble(prepareStringToCalculate(localResistanceFactorProperty.getValue()));
        if (linearPressureDrop > 0 && pipesLength > 0 && localResistanceFactor > 0) {
            double pumpHead = (linearPressureDrop * pipesLength * localResistanceFactor) / 10000;
            pumpHeadProperty.setValue(String.format("%.2f", pumpHead));
        } else {
            pumpHeadProperty.setValue("-");
        }
    }

}
