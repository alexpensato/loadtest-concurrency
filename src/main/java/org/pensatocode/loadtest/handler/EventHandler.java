package org.pensatocode.loadtest.handler;

import org.pensatocode.loadtest.domain.EventConfig;

public interface EventHandler {
    Short processEvents(EventConfig eventConfig);
}
