package de.dhbw.todoapp.model;

public enum TodoStatus {
    NOT_STARTED("Nicht gestartet"),
    IN_PROGRESS("In Bearbeitung"),
    ON_HOLD("Pausiert"),
    DONE("Erledigt");

    private final String displayName;

    TodoStatus(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
