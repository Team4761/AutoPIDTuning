package org.robockets;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.util.ArrayList;
import java.util.List;

class Tuner{

    private NetworkTable netTable;

    static ArrayList<Double> errors;

    private double averageError;

    private double setpoint;

    private double allowedError;

    private double pChange;
    private double iChange;
    private double dChange;

    Tuner(NetworkTable networkTable) {
        netTable = networkTable;

        // Clears existing values and sets up
        netTable.putNumber("P", 0);
        netTable.putNumber("I", 0);
        netTable.putNumber("D", 0);
        netTable.putNumber("Error", 0);
        netTable.putBoolean("Go", false);
        netTable.putNumber("Setpoint", 0);

        allowedError = netTable.getNumber("AllowedError", 5);

        pChange = netTable.getNumber("PChange", 0.01);
        pChange = netTable.getNumber("IChange", 0.001);
        pChange = netTable.getNumber("DChange", 0.0001);

        errors = new ArrayList<>();
    }

    void tune() {
        setpoint = netTable.getNumber("Setpoint", 0);
        while (true) {
            averageError = calculateAverage(errors);

            // Check if the average error is between the setpoint - 1 and the setpoint + 1
            if (averageError >= setpoint - allowedError && averageError <= setpoint + allowedError) {
                // I
                
            } else {
                // P
                netTable.putNumber("P", netTable.getNumber("P", 0)+pChange); // Add pChange to the current P value
            }
        }
    }

    private double calculateAverage(List<Double> list) {
        double sum = 0;
        if(!list.isEmpty()) {
            for (Double item : list) {
                sum += item;
            }
            return sum / list.size();
        }
        return sum;
    }
}
