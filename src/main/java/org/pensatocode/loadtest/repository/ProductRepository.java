package org.pensatocode.loadtest.repository;

import org.pensatocode.loadtest.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    List<Product> findByDescriptionLike(String description, Pageable pageable);

    @Query(value = "SELECT * FROM product ORDER BY id LIMIT 1 OFFSET ?1", nativeQuery = true)
    List<Product> findByOffset(Integer offset);

    @Query(value = "DELETE FROM product WHERE id >= ?1", nativeQuery = true)
    Integer deleteGreaterThan(Long producId);
}
