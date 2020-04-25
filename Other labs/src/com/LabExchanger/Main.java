package com.LabExchanger;

import com.LabExchanger.DriveExplorer.DEApplication;

import java.util.concurrent.Exchanger;

public class Main {
    public static void main(String[] args) {

        Exchanger<String> ex = new Exchanger<String>();
        new DEApplication(ex);
        new SAApplication(ex);
    }
}
