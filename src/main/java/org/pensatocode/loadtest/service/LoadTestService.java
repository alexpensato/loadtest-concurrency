package org.pensatocode.loadtest.service;

import lombok.extern.slf4j.Slf4j;
import org.pensatocode.loadtest.domain.EventConfig;
import org.pensatocode.loadtest.domain.Product;
import org.pensatocode.loadtest.handler.EventHandler;
import org.pensatocode.loadtest.model.Strategy;
import org.pensatocode.loadtest.repository.EventConfigRepository;
import org.pensatocode.loadtest.repository.ProductRepository;
import org.pensatocode.loadtest.util.EventConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Slf4j
@Service
public class LoadTestService {

    private final EventConfigRepository eventConfigRepository;
    private final ProductRepository productRepository;

    public LoadTestService(@Autowired EventConfigRepository eventConfigRepository,
                           @Autowired ProductRepository productRepository) {
        this.eventConfigRepository = eventConfigRepository;
        this.productRepository = productRepository;
    }

    public String generateTestData(Integer productOffset, Integer eventConfigOffset) {
        // TODO: generate minimum to achieve both offsets
        return "Method not implemented";
    }

    @Transactional
    public String resetTestData(Integer productOffset, Integer eventConfigOffset) {
        log.info(String.format("Product offset = %d, Event offset = %d", productOffset, eventConfigOffset));
        StringBuilder sb = new StringBuilder();
        List<Product> products = productRepository.findByOffset(productOffset);
        if (products == null || products.isEmpty()) {
            sb.append("Product table is below offset");
        } else {
            Integer productCount = productRepository.deleteProducts(products.get(0).getId());
            sb.append("Deleted products = ").append(productCount);
        }
        sb.append(", ");
        List<EventConfig> events = eventConfigRepository.findByOffset(eventConfigOffset);
        if (events == null || events.isEmpty()) {
            sb.append("EventConfig table is below offset");
        } else {
            Integer eventCount = eventConfigRepository.deleteEventConfigs(events.get(0).getId());
            sb.append("Deleted events = ").append(eventCount);
        }
        return sb.toString();
    }

    public String executeSingleStep(Strategy strategy, EventHandler eventHandler, String experimentName) {
        EventConfig eventConfig = EventConfigUtil.createRandomEventConfig(experimentName, strategy.getKey());
        long startTime = System.nanoTime();
        Short executedEvents = eventHandler.processEvents(eventConfig);
        long duration = System.nanoTime() - startTime;
        eventConfig.setExecutedTime(Duration.ofNanos(duration).toMillis());
        eventConfig.setExecutedEvents(executedEvents);
        eventConfigRepository.save(eventConfig);
        return "OK";
    }
}
