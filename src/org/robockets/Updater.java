package org.robockets;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

class Updater implements Runnable{

    NetworkTable table;

    Updater(NetworkTable table) {
        this.table = table;
    }

    @Override
    public void run() {
        Tuner.errors.add(table.getNumber("Error", 0));
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
