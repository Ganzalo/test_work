package ru.fedorov.group;

import ru.fedorov.entity.MultiEntity;

import java.util.List;
import java.util.Objects;

public class Group {

   private List<MultiEntity> group;

    public Group(List<MultiEntity> group) {
        this.group = group;
    }

    public List<MultiEntity> getGroup() {
        return group;
    }

    public int size() {
        return group.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group1 = (Group) o;
        return group.equals(group1.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (MultiEntity multiEntity : group) {
            str.append(multiEntity + "\n");
        }
        return str.toString();
    }
}
