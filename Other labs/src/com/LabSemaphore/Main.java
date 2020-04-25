package com.LabSemaphore;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        Semaphore sem = new Semaphore(1);

        new DEApplication(sem);
        new PMApplication(sem);
        new SAApplication(sem);
    }

}
