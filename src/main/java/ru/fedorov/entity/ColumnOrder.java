package ru.fedorov.entity;

public enum ColumnOrder {
    First(0),
    Second(1),
    Third(2);

    private int value;

    public int getValue() {
        return value;
    }

    ColumnOrder(int value) {
        this.value = value;
    }
}
