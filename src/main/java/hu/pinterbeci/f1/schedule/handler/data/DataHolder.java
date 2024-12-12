package main.java.hu.pinterbeci.f1.schedule.handler.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.hu.pinterbeci.f1.schedule.handler.model.Race;

public class DataHolder {

    private final Map<Integer, List<Race>> data;

    public DataHolder() {
        this.data = new HashMap<>();
    }

    public void putData(final Integer key, final List<Race> value) {
        this.data.put(key, value);
    }

    public List<Race> getValue(final Integer key) {
        return this.data.get(key);
    }

    public boolean keyAlreadyExists(final Integer key) {
        return this.data.containsKey(key);
    }

    public Map<Integer, List<Race>> getData() {
        return data;
    }
}
