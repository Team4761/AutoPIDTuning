package org.robockets;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.util.ArrayList;

public class Tuner {

    private NetworkTable netTable;

    private ArrayList<Double> errors;

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
}
