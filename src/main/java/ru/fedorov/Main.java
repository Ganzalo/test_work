package ru.fedorov;

import ru.fedorov.entity.Entity;
import ru.fedorov.entity.MultiEntity;
import ru.fedorov.group.Group;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    private final static String SEP = File.separator;
    private final static String FILEPATH = System.getProperty("user.dir") + SEP + "src" +
            SEP + "main" + SEP + "resources" + SEP + "lng.csv";

    private static Set<MultiEntity> uniqueStrings = new HashSet<>();
    private static Map<Entity, ArrayList<MultiEntity>> containers = new HashMap<>();
    private static Map<Integer, ArrayList<Group>> orderedGroup = new TreeMap<>(Collections.reverseOrder());

    private static int countGroup = 0;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                filterData(line.replace("\"", "").split(";", -1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        clearGroupWithoutPair();
        finalGrouping();
        print();

        System.out.println(((System.currentTimeMillis() - start) / 1000f) + " seconds");
    }

    private static void filterData(String[] values) {
        if(values.length == 3) {
            MultiEntity multiEntity = new MultiEntity(values);
            if(!uniqueStrings.contains(multiEntity) && multiEntity.isLegit()) {
                uniqueStrings.add(multiEntity);
                primaryGrouping(multiEntity);
            }
        }
    }

    private static void primaryGrouping(MultiEntity multiEntity) {
        for (Entity entity : multiEntity.getLegitEntities()) {
            ArrayList<MultiEntity> v = containers.getOrDefault(entity, new ArrayList<>());
            v.add(multiEntity);
            containers.put(entity, v);
        }
    }

    private static void clearGroupWithoutPair() {
        Map<Entity, ArrayList<MultiEntity>> newContains = new HashMap<>();
        for (Map.Entry<Entity, ArrayList<MultiEntity>> entry : containers.entrySet()) {
            if(entry.getValue().size() > 1) {
                newContains.put(entry.getKey(), entry.getValue());
            }
        }
        containers = newContains;
    }

    private static void finalGrouping() {
        additionalGrouping();

        for (Map.Entry<Entity, ArrayList<MultiEntity>> entry : containers.entrySet()) {
            ArrayList<Group> v = orderedGroup.getOrDefault(entry.getValue().size(), new ArrayList<>());
            Group group = new Group(entry.getValue());
            v.add(group);
            orderedGroup.put(group.size(), v);
            countGroup++;
        }
    }

    private static void additionalGrouping() {
        Map<MultiEntity, Integer> multiEntityCount = new HashMap<>();

        for (Map.Entry<Entity,  ArrayList<MultiEntity>> entry : containers.entrySet()) {
            ArrayList<MultiEntity> entities = entry.getValue();
            for(MultiEntity multiEntity : entities) {
                int value = multiEntityCount.getOrDefault(multiEntity, 0);
                multiEntityCount.put(multiEntity, value + 1);
            }
        }

        List<Set<MultiEntity>> multiEntitySubGroup = new ArrayList<>();
        for (Map.Entry<MultiEntity, Integer> entry : multiEntityCount.entrySet()) {
            if(entry.getValue() > 1) {
                Set<MultiEntity> set = new HashSet<>();
                for (Entity entity : entry.getKey().getLegitEntities()) {
                    if(containers.containsKey(entity)) {
                        set.addAll(containers.remove(entity));
                    }
                }
                if(set.size() > 1)
                    multiEntitySubGroup.add(set);
            }
        }

        for (Set<MultiEntity> subGroup : multiEntitySubGroup) {
            ArrayList<Group> v = orderedGroup.getOrDefault(subGroup.size(), new ArrayList<>());
            Group group = new Group(new ArrayList<>(subGroup));
            v.add(group);
            orderedGroup.put(group.size(), v);
            countGroup++;
        }
    }

    public static void print() {
        System.out.println("Кол-во групп " + countGroup);
        int count = 1;
        for (Map.Entry<Integer, ArrayList<Group>> entry : orderedGroup.entrySet()) {
            for(Group group : entry.getValue()) {
                System.out.println("Группа " + count + "\n");
                System.out.println(group);
                count++;
            }
        }
    }
}
