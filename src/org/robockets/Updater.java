package org.robockets;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.awt.*;

class Updater implements Runnable{

    NetworkTable table;

    Updater(NetworkTable table) {
        this.table = table;
    }

    @Override
    public void run() {

        try {
            // TODO: Check if Tuner.getError does not exist and ignore it if it does not
            if (Tuner.getError) {
                Tuner.errors.add(table.getNumber("Error", 0));
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (Tuner.count) {
                Tuner.timePassed++;
            }

            if (Tuner.errors.get(Tuner.errors.size() - 1) > table.getNumber("Setpoint", 90) + Tuner.dangerZone + 100) {
                Main.stop(0, true);
            } else if (Tuner.errors.get(Tuner.errors.size() - 1) > table.getNumber("Setpoint", 90) + Tuner.dangerZone) {
                Main.stop(0,false);
            }
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
