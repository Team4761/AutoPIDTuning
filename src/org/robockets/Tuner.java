package org.robockets;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Tuner{

    private NetworkTable netTable;

    static ArrayList<Double> errors;

    private double kU;
    private double tU;

    static boolean getError;
    static boolean count;

    static int timePassed;

    Tuner(NetworkTable networkTable) {
        netTable = networkTable;

        // Clears existing values and sets up
        netTable.putNumber("P", 0);
        netTable.putNumber("I", 0);
        netTable.putNumber("D", 0);
        netTable.putNumber("Error", 0);
        netTable.putBoolean("Go", false);
        netTable.putNumber("Setpoint", 90); // Setpoint should be changed on the robot based on how much it moves after each cycle in order to reset it

        getError = false;

        count = false;
        timePassed = 0;

        errors = new ArrayList<>();
    }

    void tune() throws InterruptedException{
        boolean getKu = true;
        getError = true;

        // Get the Ku number
        while (getKu) {
            double lastMaxError = Collections.max(errors);
            double lastP = netTable.getNumber("P", 0);
            netTable.putNumber("P", netTable.getNumber("P", 0)+0.01);
            netTable.putBoolean("Go", true);
            Thread.sleep(300);
            if (Collections.max(errors) <= lastMaxError) {
                netTable.putNumber("P", lastP);
                kU = lastMaxError;
                getKu = false;
                getError = false;
                errors.clear();
            }
            netTable.putBoolean("Go", false);
            // TODO: Reset setpoint/location
        }

        // Get the Tu number

        // TODO: Reset setpoint/location

        getError = true;

        count = true;

        netTable.putBoolean("Go", true);

        Thread.sleep(300);

        // Remove all values before the max
        for (int i=0; i<errors.indexOf(Collections.max(errors));i++) {
            errors.remove(0);
        }

        boolean findOsc = true;

        while (findOsc) {
            if (errors.indexOf(Collections.max(errors)) != errors.size()-1) { // Check if the max is the last number
                count = false;
                getError = false;
                findOsc = false;
            }
        }

        tU = timePassed;

        // Set P I D according to the Ziegler-Nichols Method

        netTable.putNumber("P", 0.60 * kU);
        netTable.putNumber("I", (1.2 * kU)/tU);
        netTable.putNumber("D", (3 * kU*tU)/40);


        System.out.println("Done!");
        System.out.println("Check if the PID has been tuned correctly and make any adjustments if needed");
    }
}
