package main.java.hu.pinterbeci.f1.schedule.handler.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;

import main.java.hu.pinterbeci.f1.schedule.handler.service.DataMappingService;

public class DataReader {

    private static final String FILE_PATH = "src/main/resources/input-hf.txt";

    public void readAndSaveData() {
        try (final BufferedReader bufferedReader = new BufferedReader(
            new FileReader(FILE_PATH))
        ) {
            final DataMappingService dataMappingService = new DataMappingService();
            String line;
            while ((line = bufferedReader.readLine()) != null || (Objects.equals("EXIT", line = bufferedReader.readLine()))) {
                dataMappingService.mappingData(line);
            }
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }
}
