package org.pensatocode.loadtest.repository;

import org.pensatocode.loadtest.domain.EventConfig;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventConfigRepository extends PagingAndSortingRepository<EventConfig, Long> {

    @Query(value = "SELECT * FROM event_config ORDER BY id LIMIT 1 OFFSET ?1", nativeQuery = true)
    List<EventConfig> findByOffset(Integer offset);

    @Modifying
    @Query(value = "DELETE FROM EventConfig e WHERE e.id >= :id")
    Integer deleteEventConfigs(@Param("id") Long id);
}