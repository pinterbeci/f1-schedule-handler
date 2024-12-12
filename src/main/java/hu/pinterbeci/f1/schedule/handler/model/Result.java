package main.java.hu.pinterbeci.f1.schedule.handler.model;

public class Result {

    //todo maga a result-ban egy pilóta adatait és egy verseny adatait tárolom, v1-ben működjön helyesen
    //  v1.1-ben legyenek test-ek írva
    //  v1.2-ben az adatstruktúrát gondoljam át

    private int number;

    private String driverName;

    private String driverTeam;

    private boolean isFastest;

    private float odds;

    public Result() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverTeam() {
        return driverTeam;
    }

    public void setDriverTeam(String driverTeam) {
        this.driverTeam = driverTeam;
    }

    public boolean isFastest() {
        return isFastest;
    }

    public void setFastest(boolean fastest) {
        isFastest = fastest;
    }

    public float getOdds() {
        return odds;
    }

    public void setOdds(float odds) {
        this.odds = odds;
    }
}
