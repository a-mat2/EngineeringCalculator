package com.am.calculator.gasCalculator;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

import static com.am.calculator.CalculationSupport.prepareStringToCalculate;

public class GasCalculatorService {
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
            "DN 125|139.7|4.0",
            "DN 150|168.3|4.5",
            "DN 200|219.1|6.3",
            "DN 250|273.0|6.3",
            "DN 300|323.9|7.1",
            "DN 350|355.6|8.0",
            "DN 400|406.4|8.8",
            "DN 450|457.2|10.0",
            "DN 500|508.0|11.0");
    private String appropriateDiameter;
    private StringProperty gasFlowProperty = new SimpleStringProperty(this, "gasFlow", "0");
    private StringProperty powerOfGasAppliancesProperty = new SimpleStringProperty(this, "powerOfGasAppliances", "0");
    private StringProperty resultsProperty = new SimpleStringProperty(this, "results", "");
    private StringProperty incorrectResultsProperty = new SimpleStringProperty(this, "incorrectResults", "");

    StringProperty getGasFlowProperty() {
        return gasFlowProperty;
    }

    StringProperty getPowerOfGasAppliancesProperty() {
        return powerOfGasAppliancesProperty;
    }

    StringProperty getResultsProperty() {
        return resultsProperty;
    }

    StringProperty getIncorrectResultsProperty() {
        return incorrectResultsProperty;
    }

    void calculatePowerOfAppliances() {
        double power = Double.parseDouble(prepareStringToCalculate(gasFlowProperty.getValue())) / 0.115;
        powerOfGasAppliancesProperty.setValue(String.format("%.2f", power));
    }

    void calculateGasFlow() {
        double flow = Double.parseDouble(prepareStringToCalculate(powerOfGasAppliancesProperty.getValue())) * 0.115;
        gasFlowProperty.setValue(String.format("%.2f", flow));
    }

    private double calculatePressureDrop(double diameter) {
        double gasFlow = Double.parseDouble(prepareStringToCalculate(gasFlowProperty.getValue()));
        return 0.776457 * Math.pow(10, -8) * 0.78 * ((Math.pow(gasFlow, 1.82) / Math.pow((diameter / 1000), 4.82)));
    }

    private double calculateGasSpeed(double diameter) {
        double gasFlow = Double.parseDouble(prepareStringToCalculate(gasFlowProperty.getValue()));
        double area = 0.785 * Math.pow((diameter / 1000), 2);
        return gasFlow / 3600 / area;
    }

    public void calculateDiameters() {
        for (String diameter : STEEL_PIPES) {
            String[] splitDiameter = diameter.split("\\|");
            float outerDiameter = Float.parseFloat(splitDiameter[1]);
            float wallThickness = Float.parseFloat(splitDiameter[2]);
            float innerDiameter = outerDiameter - (2 * wallThickness);
            if (calculateGasSpeed(innerDiameter) <= 6) {
                appropriateDiameter = diameter;
                break;
            }
        }
        displayResults();
    }

    private void displayResults() {
        int start;
        String results = "";
        String incorrectResults = "";
        int appropriateDiameterId = STEEL_PIPES.indexOf(appropriateDiameter);
        switch (appropriateDiameterId) {
            case 0 -> start = appropriateDiameterId;
            case 1 -> start = 0;
            default -> start = appropriateDiameterId - 2;
        }
        for (int i = start; i <= (appropriateDiameterId + 2); i++) {
            String currentDiameter = STEEL_PIPES.get(i);
            String[] splitCurrentDiameter = currentDiameter.split("\\|");
            float outerDiameter = Float.parseFloat(splitCurrentDiameter[1]);
            float wallThickness = Float.parseFloat(splitCurrentDiameter[2]);
            float innerDiameter = outerDiameter - (2 * wallThickness);
            double pressureDrop = calculatePressureDrop(innerDiameter);
            double gasSpeed = calculateGasSpeed(innerDiameter);
            if (i < appropriateDiameterId) {
                incorrectResults += String.format("%n%s - ΔP = %.3f Pa/m, v = %.2f m/s", splitCurrentDiameter[0], pressureDrop, gasSpeed);
            } else {
                results += String.format("%s - ΔP = %.3f Pa/m, v = %.2f m/s%n", splitCurrentDiameter[0], pressureDrop, gasSpeed);
            }
            if (i == STEEL_PIPES.size() - 1) {
                break;
            }
        }
        resultsProperty.setValue(results);
        incorrectResultsProperty.setValue(incorrectResults);
    }

}
