package de.elite.games.maplib.data;

public class TestMapFieldData {

    private boolean isMarkedAsPath;

    public void markAsPath(boolean isMarkedAsPath) {
        this.isMarkedAsPath = isMarkedAsPath;
    }

    public boolean isMarkedAsPath() {
        return isMarkedAsPath;
    }
}
