package org.robockets;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Tuner{

    private NetworkTable netTable;

    static ArrayList<Double> errors;

    private double averageError;

    private double setpoint;

    private double allowedError;

    private double kU;
    private double tU;

    private double pChange;
    private double iChange;
    private double dChange;

    public static boolean getError;

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

        getError = false;

        errors = new ArrayList<>();
    }

    void tune() throws InterruptedException{
        setpoint = netTable.getNumber("Setpoint", 0);
        boolean getKu = true;
        getError = true;


        // Get the Ku number
        while (getKu) {
            double lastMaxError = Collections.max(errors);
            double lastP = netTable.getNumber("P", 0);
            netTable.putNumber("P", netTable.getNumber("P", 0)+0.01);
            Thread.sleep(100);
            if (Collections.max(errors) <= lastMaxError) {
                netTable.putNumber("P", lastP);
                kU = lastMaxError;
                getKu = false;
                getError = false;
                errors.clear();
            }
        }

        // Get the Tu number

        getError = true;

    }
}
