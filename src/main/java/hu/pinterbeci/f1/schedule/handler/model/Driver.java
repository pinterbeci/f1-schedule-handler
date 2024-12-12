package main.java.hu.pinterbeci.f1.schedule.handler.model;

public class Driver {
    private String name;

    private String team;

    private boolean isFastest;

    public Driver(String name, String team) {
        this.name = name;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public boolean isFastest() {
        return isFastest;
    }

    public void setFastest(boolean fastest) {
        isFastest = fastest;
    }
}
