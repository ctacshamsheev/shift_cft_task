package ru.cft.shift.task5;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Product {
    private static volatile int productID = 0;
    private static final Object syncMonitor = new Object();
    @Getter
    private final int currentProductID;

    public Product() {
        synchronized (syncMonitor) {
            currentProductID = ++productID;
            // log.trace("new product [{}]", currentProductID);
        }
    }
}
