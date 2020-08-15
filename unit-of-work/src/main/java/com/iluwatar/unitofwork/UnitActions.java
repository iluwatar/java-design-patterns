package com.iluwatar.unitofwork;

public enum UnitActions {
    INSERT("INSERT"),
    DELETE("DELETE"),
    MODIFY("MODIFY")
    ;

    private final String actionValue;

    UnitActions(String actionValue) {
        this.actionValue = actionValue;
    }

    public String getActionValue() {
        return actionValue;
    }
}
