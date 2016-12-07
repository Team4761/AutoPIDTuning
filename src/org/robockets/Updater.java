package org.robockets;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

class Updater implements Runnable{

    NetworkTable table;

    Updater(NetworkTable table) {
        this.table = table;
    }

    @Override
    public void run() {

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
    }
}
