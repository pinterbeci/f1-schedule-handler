package main.java.hu.pinterbeci.f1.schedule.handler.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import main.java.hu.pinterbeci.f1.schedule.handler.model.Race;
import main.java.hu.pinterbeci.f1.schedule.handler.model.Result;

public class DataMappingService {

    private final DataService dataService;

    private Race race = null;

    private List<Result> raceResults = null;

    private String fastestName = null;

    private boolean expectingPoint = false;

    private String queryCommand = null;

    public DataMappingService() {
        this.dataService = new DataService();
    }

    public void mappingData(final String line) throws Exception {
        if (line.startsWith("RACE")) {
            final String[] raceLine = line.split(";");
            race = this.dataService.initNewRaceInstanceFromLine(raceLine);
        }
        if (line.startsWith("RESULT")) {
            final String[] resultLine = line.split(";");
            final Result result = this.dataService.initNewResultInstanceFromFile(resultLine);
            if (Objects.isNull(raceResults)) {
                raceResults = new ArrayList<>();
            }
            raceResults.add(result);
        }
        if (line.startsWith("FASTEST")) {
            final String[] fastestLine = line.split(";");
            fastestName = this.dataService.initFastestPilotNameFromLine(fastestLine);
        }
        if (line.startsWith("FINISH")) {
            final String[] finishLineStr = line.split(";");
            this.dataService.fillAndSaveRaceData(finishLineStr, race, raceResults, fastestName);
            raceResults = new ArrayList<>();
            fastestName = null;
        }

        if (line.startsWith("QUERY")) {
            expectingPoint = true;
            queryCommand = line;
        }

        if (line.startsWith("POINT")) {
            if (expectingPoint) {
                final String[] queryCommandParts = queryCommand.split(";");
                final String[] pointCommandParts = line.split(";");
                this.dataService.determineRanking(queryCommandParts, pointCommandParts);
            }
        }
    }
}
