package org.pensatocode.loadtest.service;

import org.pensatocode.loadtest.handler.AbstractEventHandler;
import org.pensatocode.loadtest.handler.impl.BasicEventHandler;
import org.pensatocode.loadtest.handler.impl.DedicatedClockEventHandler;
import org.pensatocode.loadtest.handler.impl.ForkJoinEventHandler;
import org.pensatocode.loadtest.model.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.MissingResourceException;

@Service
public class StrategyService {

    private final BasicEventHandler basicEventHandler;
    private final ForkJoinEventHandler forkJoinEventHandler;
    private final DedicatedClockEventHandler dedicatedClockEventHandler;

    private Strategy currentStrategy = Strategy.BASIC;

    public StrategyService(@Autowired BasicEventHandler basicEventHandler,
                           @Autowired ForkJoinEventHandler forkJoinEventHandler,
                           @Autowired DedicatedClockEventHandler dedicatedClockEventHandler) {
        this.basicEventHandler = basicEventHandler;
        this.forkJoinEventHandler = forkJoinEventHandler;
        this.dedicatedClockEventHandler = dedicatedClockEventHandler;
    }

    public Strategy getCurrent() {
        return currentStrategy;
    }

    public Strategy update(String value) {
        Strategy result = Strategy.find(value);
        if (result == null) {
            throw new IllegalArgumentException("Strategy not found!");
        }
        this.currentStrategy = result;
        return this.currentStrategy;
    }

    public AbstractEventHandler createEventHandlerByStrategy(Strategy strategy) {
        switch(strategy) {
            case BASIC:
                return basicEventHandler;
            case FORK_JOIN:
                return forkJoinEventHandler;
            case DEDICATED_CLOCK:
                return dedicatedClockEventHandler;
        }
        throw new MissingResourceException(
                "Current Strategy not found",
                this.getClass().getName(),
                AbstractEventHandler.class.getName());
    }
}
