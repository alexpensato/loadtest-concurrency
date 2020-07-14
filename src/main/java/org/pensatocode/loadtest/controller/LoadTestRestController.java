package org.pensatocode.loadtest.controller;

import org.pensatocode.loadtest.handler.EventHandler;
import org.pensatocode.loadtest.model.Strategy;
import org.pensatocode.loadtest.service.LoadTestService;
import org.pensatocode.loadtest.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loadtest")
public class LoadTestRestController {

    private final StrategyService strategyService;
    private final LoadTestService loadTestService;

    public LoadTestRestController(@Autowired StrategyService strategyService,
                                  @Autowired LoadTestService loadTestService) {
        this.strategyService = strategyService;
        this.loadTestService = loadTestService;
    }

    @GetMapping("/data/generate")
    public String generate() {
        return loadTestService.generateTestData();
    }

    @GetMapping("/data/reset")
    public String reset() {
        return loadTestService.resetTestData(500000, 200000);
    }

    @GetMapping("/start/{strategyName}/{experimentName}")
    public String loadTest(@PathVariable("strategyName") String strategyName,
                           @PathVariable("experimentName") String experimentName) {
        Strategy strategy = strategyService.update(strategyName);
        EventHandler eventHandler = strategyService.createEventHandlerByStrategy(strategy);
        return loadTestService.startLoadTest(strategy, eventHandler, experimentName);
    }

}
