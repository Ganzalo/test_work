package ru.fedorov.entity;

import java.util.Objects;

public class Entity {

    private ColumnOrder columnOrder;
    private String number;
    private boolean legit;

    public Entity(ColumnOrder columnOrder, String number) {
        this.columnOrder = columnOrder;
        this.number = number;
        if (number.isEmpty())
            legit = false;
        else
            legit = true;
    }

    public boolean isLegit() {
        return legit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return columnOrder == entity.columnOrder &&
                number.equals(entity.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnOrder, number);
    }

    @Override
    public String toString() {
        return "\"" + number + "\"";
    }
}
