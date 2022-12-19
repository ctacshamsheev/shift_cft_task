package ru.cft.shift.task5;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class Consumer extends Thread {

    private final int consumerID;
    private final Storage storage;

    public void consume() {
        try {
            Product product = storage.pop();
            Thread.sleep(ProductionProperties.getConsumerTime());
            log.trace("Consumer[{}] consume(product[{}])", consumerID, product.getCurrentProductID());
        } catch (InterruptedException e) {
            log.error("Producer run error: {}", e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            consume();
        }
    }
}
