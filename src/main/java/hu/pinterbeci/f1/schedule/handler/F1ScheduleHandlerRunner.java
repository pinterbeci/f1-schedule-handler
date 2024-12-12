package main.java.hu.pinterbeci.f1.schedule.handler;

import main.java.hu.pinterbeci.f1.schedule.handler.io.DataReader;

public class F1ScheduleHandlerRunner {
    public static void main(final String[] args) {
        new DataReader().readAndSaveData();
    }
}