package ru.cft.shift.task5;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class Storage {
    private volatile int count;
    private final ArrayList<Product> arrayList;

    public Storage() {
        count = ProductionProperties.getStorageSize();
        arrayList = new ArrayList<>(count);
    }

    public synchronized void put(Product product) {
        while (count <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.error("Storage put error: {}", e.getMessage());
            }
        }

        synchronized (arrayList) {
            --count;
            arrayList.add(product);
            log.trace("Storage put product[{}], in storage ({}) products", product.getCurrentProductID(), arrayList.size());
        }
        notifyAll();
    }

    public synchronized Product pop() {
        while (count >= ProductionProperties.getStorageSize()) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.error("Storage pop error: {}", e.getMessage());
            }
        }
        synchronized (arrayList) {
            ++count;
            Product product = arrayList.remove(0);
            log.trace("Storage pop product[{}], in storage ({}) products", product.getCurrentProductID(), arrayList.size());
            notifyAll();
            return product;
        }
    }
}
