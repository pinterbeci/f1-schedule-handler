package main.java.hu.pinterbeci.f1.schedule.handler.validator;

import java.util.List;
import java.util.Objects;

import main.java.hu.pinterbeci.f1.schedule.handler.errors.F1ScheduleException;
import main.java.hu.pinterbeci.f1.schedule.handler.model.Race;
import main.java.hu.pinterbeci.f1.schedule.handler.model.Result;

public class DataValidator {

    public static void validateRaceLine(final String[] raceLine) throws Exception {
        if (raceLine.length != 5) {
            throw new F1ScheduleException("'RACE' line length validation",
                "'RACE' soran nem megfelelo a parancs parametereinek szama! (5 parameter szukseges)");
        }
    }

    public static void validateResultLine(final String[] resultLine) throws Exception {
        if (resultLine.length != 4) {
            throw new F1ScheduleException("'RESULT' line length validation",
                "'RESULT' soran nem megfelelo a parancs parametereinek szama! (4 parameter szukseges)");
        }
    }

    public static void validateFastestLine(final String[] fastestLine) throws Exception {
        if (fastestLine.length != 3) {
            throw new F1ScheduleException("'FASTEST' line length validation",
                "'FASTEST' soran nem megfelelo a parancs parametereinek szama! (3 parameter szukseges)");
        }
    }

    public static void validateRaceData(final String[] finishLineStr, final Race race, final List<Result> raceResults, final String fastestName)
        throws Exception {

        if (finishLineStr.length != 1) {
            throw new F1ScheduleException("Read 'finish' line length validation", "A 'FINISH' 'null' erteku!");
        }
        if (Objects.isNull(race)) {
            throw new F1ScheduleException("'RACE' line null check is true", "A 'RACE' 'null' erteku!");
        }

        if (Objects.isNull(raceResults)) {
            throw new F1ScheduleException("'RESULT' line null check is true", "A 'RESULT' 'null' erteku!");
        }

        if (Objects.isNull(fastestName)) {
            throw new F1ScheduleException("'FASTEST' line null check is true", "A 'FASTEST' 'null' erteku!");
        }

        if (raceResults.size() < 10) {
            throw new F1ScheduleException("'RESULT' line size check", "Nem elengendo 'RESULT' kerult megadasra! (Legalabb 10 kell szerepeljen)");
        }
    }

}
