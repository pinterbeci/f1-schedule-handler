package main.java.hu.pinterbeci.f1.schedule.handler.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import main.java.hu.pinterbeci.f1.schedule.handler.data.DataHolder;
import main.java.hu.pinterbeci.f1.schedule.handler.enums.PointingMethod;
import main.java.hu.pinterbeci.f1.schedule.handler.model.Driver;
import main.java.hu.pinterbeci.f1.schedule.handler.model.Race;
import main.java.hu.pinterbeci.f1.schedule.handler.model.Result;
import main.java.hu.pinterbeci.f1.schedule.handler.validator.DataValidator;

public class DataService {

    private final DataHolder dataHolder;

    private final RankingService rankingService;

    private final List<PointingMethod> pointingMethods =
        List.of(PointingMethod.CLASSIC, PointingMethod.MODERN, PointingMethod.NEW, PointingMethod.PRESENT);

    public DataService() {
        this.dataHolder = new DataHolder();
        this.rankingService = new RankingService(this.dataHolder);
    }

    protected Race initNewRaceInstanceFromLine(final String[] raceLine) throws Exception {
        DataValidator.validateRaceLine(raceLine);

        //data init
        final int year = Integer.parseInt(raceLine[1]);
        final String name = raceLine[2];
        final int number = Integer.parseInt(raceLine[3]);
        final float oddsOfTheRace = Float.parseFloat(raceLine[4]);

        final Race race = new Race();
        race.setYear(year);
        race.setName(name);
        race.setNumber(number);
        race.setOdds(oddsOfTheRace);

        return race;
    }

    protected Result initNewResultInstanceFromFile(final String[] resultLine) throws Exception {
        DataValidator.validateResultLine(resultLine);
        //data init
        final int number = Integer.parseInt(resultLine[1]);
        final String name = resultLine[2];
        final String team = resultLine[3];
        final Driver driver = new Driver(name, team);

        final Result result = new Result();
        result.setNumber(number);
        result.setDriverName(driver.getName());
        result.setDriverTeam(driver.getTeam());

        return result;
    }

    protected String initFastestPilotNameFromLine(final String[] fastestLine) throws Exception {
        DataValidator.validateFastestLine(fastestLine);
        return fastestLine[1];
    }

    protected void fillAndSaveRaceData(final String[] finishLineStr, final Race race, final List<Result> raceResults, final String fastestName)
        throws Exception {
        DataValidator.validateRaceData(finishLineStr, race, raceResults, fastestName);
        setFastest(race, raceResults, fastestName);
        setOddsForResults(raceResults, race.getOdds());
        savingRace(race);
    }

    protected void determineRanking(final String[] queryCommandParts, final String[] pointCommandParts) {
        final int yearOfQuery = Optional.of(queryCommandParts[1]).map(Integer::parseInt).orElse(0);

        final String currentPointingMethod = Optional.of(pointCommandParts[1]).orElse("");
        final boolean canGiveExtraPoint = Objects.equals(currentPointingMethod, PointingMethod.PRESENT.name());

        final List<Integer> awardedPoints = pointingMethods.stream()
            .filter(method -> Objects.equals(method.name(), currentPointingMethod))
            .findFirst()
            .map(PointingMethod::getPointsByMethod)
            .orElse(null);

        if (queryCommandParts.length == 3) {
            final int raceNumber = Optional.of(queryCommandParts[2]).map(Integer::parseInt).orElse(0);
            determineRanking(yearOfQuery, awardedPoints, raceNumber, canGiveExtraPoint);
            return;
        }
        determineRanking(yearOfQuery, awardedPoints, canGiveExtraPoint);
    }

    private void determineRanking(final int year, final List<Integer> awardedPoints, final boolean canGiveExtraPoint) {
        this.rankingService.determineRanking(year, awardedPoints, canGiveExtraPoint);
    }

    private void determineRanking(final int year, final List<Integer> awardedPoints, final int raceNumber, final boolean canGiveExtraPoint) {
        this.rankingService.determineRanking(year, awardedPoints, raceNumber, canGiveExtraPoint);
    }

    private void savingRace(final Race race) {
        if (!this.dataHolder.keyAlreadyExists(race.getYear())) {
            this.dataHolder.putData(race.getYear(), List.of(race));
            return;
        }
        final String currentRaceName = race.getName();
        final int currentRaceNumber = race.getYear();

        boolean raceAlreadySaved = this.dataHolder.getValue(race.getYear()).stream().anyMatch(
            savedRace -> Objects.equals(savedRace.getName(), currentRaceName) &&
                Objects.equals(savedRace.getYear(), currentRaceNumber)
        );
        if (raceAlreadySaved) {
            return;
        }
        final List<Race> currentRacesByYear = new ArrayList<>(this.dataHolder.getValue(race.getYear()));
        currentRacesByYear.add(race);
        this.dataHolder.putData(race.getYear(), currentRacesByYear);
    }

    private void setOddsForResults(final List<Result> raceResults, final float odds) {
        raceResults.forEach(result -> result.setOdds(odds));
    }

    private void setFastest(final Race race, final List<Result> raceResults, final String fastestName) {
        raceResults.forEach(
            result -> {
                if (Objects.equals(result.getDriverName(), fastestName)) {
                    result.setFastest(true);
                }
            }
        );
        race.setResults(raceResults);
    }
}
