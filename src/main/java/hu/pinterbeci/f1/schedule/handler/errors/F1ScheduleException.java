package main.java.hu.pinterbeci.f1.schedule.handler.errors;

public class F1ScheduleException extends Exception {
    public F1ScheduleException(final String cause, final String message) {
        super(message);
        System.err.printf("Exception occurred by: %s", cause);
    }
}
