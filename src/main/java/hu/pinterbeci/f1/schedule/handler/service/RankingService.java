package main.java.hu.pinterbeci.f1.schedule.handler.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import main.java.hu.pinterbeci.f1.schedule.handler.data.DataHolder;
import main.java.hu.pinterbeci.f1.schedule.handler.io.DataWriter;
import main.java.hu.pinterbeci.f1.schedule.handler.model.Result;

public class RankingService {

    private final DataHolder dataHolder;

    public RankingService(final DataHolder dataHolder) {
        this.dataHolder = dataHolder;
    }

    protected void determineRanking(final int year, final List<Integer> awardedPoints, final boolean canGiveExtraPoint) {
        DataWriter.writeRankingToConsole(
            calculateFinallyRanking(year, awardedPoints, -1, canGiveExtraPoint), year, -1
        );
    }

    protected void determineRanking(final int year, final List<Integer> awardedPoints, final int raceNumber, final boolean canGiveExtraPoint) {
        DataWriter.writeRankingToConsole(
            calculateFinallyRanking(year, awardedPoints, raceNumber, canGiveExtraPoint), year, raceNumber
        );
    }

    private List<Result> fillResultsByAward(final int year, final List<Integer> awardedPoints, final int raceNumber) {
        final List<Result> resultsByAward = new ArrayList<>();

        if (raceNumber > 0) {
            this.dataHolder.getValue(year).stream()
                .filter(race -> race.getNumber() <= raceNumber)
                .forEach(filteredRace ->
                    resultsByAward.addAll(
                        filteredRace.getResults().stream().filter(result -> result.getNumber() <= awardedPoints.size()).toList())
                );

            return resultsByAward;
        }

        this.dataHolder.getValue(year).forEach(race ->
            resultsByAward.addAll(
                race.getResults().stream().filter(result -> result.getNumber() <= awardedPoints.size()).toList())
        );
        return resultsByAward;
    }

    private Map<String, List<Float>> groupingPilotsByAwardedPoints(final List<Integer> awardedPoints,
                                                                   final List<Result> resultsByAward,
                                                                   final boolean canGiveExtraPoint
    ) {
        return resultsByAward.stream().collect(
            Collectors.groupingBy(Result::getDriverName,
                Collectors.mapping(resultByName -> {
                        if (canGiveExtraPoint && resultByName.isFastest()) {
                            return calculatePointForRanking(awardedPoints.get(resultByName.getNumber() - 1), resultByName.getOdds(), true);
                        }
                        return calculatePointForRanking(awardedPoints.get(resultByName.getNumber() - 1), resultByName.getOdds(), false);
                    },
                    Collectors.toList()
                )
            )
        );

    }

    private Map<String, Float> calculateFinallyRanking(final int year,
                                                       final List<Integer> awardedPoints,
                                                       final int raceNumber,
                                                       final boolean canGiveExtraPoint
    ) {
        final List<Result> resultsByAward = fillResultsByAward(year, awardedPoints, raceNumber);
        final Map<String, List<Float>> groupedPilotsByAwardedPoints =
            groupingPilotsByAwardedPoints(awardedPoints, resultsByAward, canGiveExtraPoint);

        final Map<String, Float> result = new HashMap<>();

        for (Map.Entry<String, List<Float>> stringListEntry : groupedPilotsByAwardedPoints.entrySet()) {
            Float sumOfPoints = stringListEntry.getValue().stream().reduce(0f, Float::sum);
            result.put(stringListEntry.getKey(), sumOfPoints);
        }

        return result.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }


    private float calculatePointForRanking(int awardedPoint, final float odds, final boolean canGiveExtraPoint) {
        if (canGiveExtraPoint) {
            awardedPoint = awardedPoint + 1;
        }
        if (odds == 0.0) {
            return awardedPoint;
        }
        return awardedPoint * odds;
    }

}
