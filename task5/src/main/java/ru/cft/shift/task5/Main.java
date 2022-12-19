package ru.cft.shift.task5;


import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;

@Slf4j
public class Main {
    public static void main(String[] args) {
        if (new File(ProductionProperties.file).exists()) {
            ProductionProperties.setPropertiesFromFile();
        } else {
            ProductionProperties.setProperties(10, 10, 1000, 10000, 5);
        }

        Storage storage = new Storage();

        ArrayList<Producer> producers = new ArrayList<>();
        for (int i = 1; i <= ProductionProperties.getProducerCount(); i++) {
            producers.add(new Producer(i, storage));
        }

        ArrayList<Consumer> consumers = new ArrayList<>();
        for (int i = 1; i <= ProductionProperties.getConsumerCount(); i++) {
            consumers.add(new Consumer(i, storage));
        }

        producers.forEach(Thread::start);
        consumers.forEach(Thread::start);

    }
}
