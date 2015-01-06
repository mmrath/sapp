package com.mmrath.sapp;


public enum Severity {

    INFO(0), WARN(1), ERROR(2), FETAL(3);
    int level;

    private Severity(int sevLevel) {
        level = sevLevel;
    }

    public int level() {
        return level;
    }
}
