package com.LabRunAtSameTime;


import com.LabExceptions.DriveExplorer.DEApplication;
import com.LabExceptions.PatternMatcher.PMApplication;
import com.LabExceptions.SortArray.SAApplication;

import java.lang.reflect.Executable;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        List<Runnable> appsToRun = List.of(new DEApplication(), new PMApplication(), new SAApplication());
        RunTogether(appsToRun);
    }

    private static void RunTogether(List<Runnable> tasks) {
        if(tasks.isEmpty()) {
            return;
        }

        try {
            var executorService = Executors.newFixedThreadPool(tasks.size());
            tasks.forEach(executorService::submit);

            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.HOURS);
        }
        catch (InterruptedException e) {
            throw new RuntimeException("Tasks could not be finished", e);
        }
    }

}

