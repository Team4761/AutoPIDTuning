package org.robockets;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.util.ArrayList;
import java.util.List;

public class Tuner implements Runnable{

    private NetworkTable netTable;

    private ArrayList<Double> errors;

    private double averageError;

    public Tuner(NetworkTable networkTable) {
        netTable = networkTable;

        // Clears existing values and sets up
        netTable.putNumber("P", 0);
        netTable.putNumber("I", 0);
        netTable.putNumber("D", 0);
        netTable.putNumber("Error", 0);
        netTable.putBoolean("Go", false);

        errors = new ArrayList<>();
    }

    public static void tune() {

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

    @Override
    public void run() {
        tune();
    }
}
