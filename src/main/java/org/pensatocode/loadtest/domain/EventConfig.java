package org.pensatocode.loadtest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventConfig {
    @Id
    @SequenceGenerator(name = "eventConfigSequence", sequenceName = "event_config_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "eventConfigSequence")
    Long id;
    String strategy;
    String experimentName;
    Short configMaxEvents;
    Integer configMaxTime;
    Short configPageReads;
    Short configPageSize;
    Short configLoopSkips;
    Short executedEvents;
    Long executedTime;
}
