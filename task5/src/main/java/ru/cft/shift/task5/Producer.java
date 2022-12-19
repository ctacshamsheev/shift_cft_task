package ru.cft.shift.task5;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class Producer extends Thread {
    private final int producerID;
    private Storage storage;

    public Product produce() {
        try {
            Thread.sleep(ProductionProperties.getProducerTime());
        } catch (InterruptedException e) {
            log.error("Producer run error: {}", e.getMessage());
        }

        Product product = new Product();
        log.trace("Producer[{}] produce(product[{}])", producerID, product.getCurrentProductID());
        return product;
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            storage.put(produce());
        }
    }
}
