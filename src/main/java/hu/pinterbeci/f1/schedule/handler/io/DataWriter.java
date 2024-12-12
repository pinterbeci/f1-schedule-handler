package main.java.hu.pinterbeci.f1.schedule.handler.io;

import java.util.Map;

public class DataWriter {

    public static void writeRankingToConsole(final Map<String, Float> ranking, final int year, final int raceNumber) {
        if (raceNumber > 0) {
            System.out.println("F1 ranking of " + year + " year, after " + raceNumber + " race:");
        } else {
            System.out.println("F1 ranking of " + year + " year:");
        }
        int rankingCnt = 1;
        for (Map.Entry<String, Float> rankingItem : ranking.entrySet()) {
            String pointStr = String.valueOf(rankingItem.getValue());
            if (pointStr.endsWith(".0")) {
                pointStr = pointStr.substring(0, pointStr.length() - 2);
            }
            System.out.println(rankingCnt + ". " + rankingItem.getKey() + " - " + pointStr + " points");
            rankingCnt++;
        }
        System.out.println("____________________________________________________");
    }
}
