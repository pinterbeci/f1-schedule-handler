package main.java.hu.pinterbeci.f1.schedule.handler.enums;

import java.util.List;

public enum PointingMethod {
    CLASSIC(List.of(10, 6, 4, 3, 2, 1)),
    MODERN(List.of(10, 8, 6, 5, 4, 3, 2, 1)),
    NEW(List.of(25, 18, 15, 12, 10, 8, 6, 4, 2, 1)),
    PRESENT(List.of(25, 18, 15, 12, 10, 8, 6, 4, 2, 1));

    private final List<Integer> pointsByMethod;

    PointingMethod(final List<Integer> pointsByMethod) {
        this.pointsByMethod = pointsByMethod;
    }

    public List<Integer> getPointsByMethod() {
        return pointsByMethod;
    }
}
