package org.robockets;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Main {

    static NetworkTable netTable;
    static Robot keyBot;

    public static void main(String[] args) {
        netTable = NetworkTable.getTable("AutoPID");
        try {
            keyBot = new Robot();
            waitForStart();
            stop(); // For testing
        } catch (InterruptedException | AWTException e) {
            e.printStackTrace();
        }
    }

    public static void waitForStart() throws InterruptedException{
        while (netTable.getBoolean("IsStarted", false)) {
            System.out.println("Waiting...");
            Thread.sleep(100);
        }

        System.out.println("Running!");
        System.out.println("Starting...");
    }

    public static void stop() throws AWTException, InterruptedException{
        stop(0, false);
    }

    public static void stop(double seconds, boolean emergency) throws AWTException, InterruptedException{
        Thread.sleep((long)seconds*100);
        for (int i=0; i<5;i++) {
            if (emergency) {
                keyBot.keyPress(KeyEvent.VK_SPACE);
            } else {
                keyBot.keyPress(KeyEvent.VK_ENTER);
            }
        }
    }
}
