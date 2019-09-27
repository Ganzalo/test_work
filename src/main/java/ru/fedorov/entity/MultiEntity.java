package ru.fedorov.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class MultiEntity {

    private List<Entity> entities = new ArrayList<>();

    public MultiEntity(String... strings) {
        for (ColumnOrder columnOrder : ColumnOrder.values()) {
            entities.add(new Entity(columnOrder, strings[columnOrder.getValue()]));
        }
    }

    public Entity getEntity(ColumnOrder columnOrder) {
        return entities.get(columnOrder.getValue());
    }

    public List<Entity> getLegitEntities() {
        return entities.stream().filter(Entity::isLegit).collect(Collectors.toList());
    }

    public boolean isLegit() {
        for (Entity entity : entities) {
            if(entity.isLegit()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiEntity that = (MultiEntity) o;
        return entities.equals(that.entities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entities);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(";");
        for (Entity entity : entities)
            joiner.add(entity.toString());
        return joiner.toString();
    }
}
