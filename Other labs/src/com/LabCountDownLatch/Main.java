package com.LabCountDownLatch;

import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String args[]) {
        CountDownLatch cdl = new CountDownLatch(3);

        System.out.println("Starting");
        new DEApplication(cdl);
        new PMApplication(cdl);
        new SAApplication(cdl);

        try {
            cdl.await();
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }

        System.out.println("All applications finished their work. Job is Done");
    }
}
