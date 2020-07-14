package org.pensatocode.loadtest.handler;

import lombok.extern.slf4j.Slf4j;
import org.pensatocode.loadtest.domain.EventConfig;
import org.pensatocode.loadtest.domain.Product;
import org.pensatocode.loadtest.repository.ProductRepository;
import org.pensatocode.loadtest.util.ProductUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Slf4j
public abstract class AbstractEventHandler implements EventHandler {

    protected ProductRepository productRepository;

    protected AbstractEventHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    protected Short doSomeWork(EventConfig eventConfig, short loopCounter) {
        short count = 0;
        Product product = ProductUtil.createRandomProduct();
        Pageable pageable = PageRequest.of(0, 20, Sort.unsorted());
        List<Product> products  = productRepository.findByDescriptionLike(product.getSearchPattern(), pageable);
        if (products == null || products.isEmpty()) {
            Long totalProducts = productRepository.count();
            long offset = totalProducts / eventConfig.getConfigPageReads();
            int size = eventConfig.getConfigPageSize();
            int page = (int) (offset / size);
            pageable = PageRequest.of(page, size, Sort.unsorted());
            Page<Product> pagedProducts = productRepository.findAll(pageable);
            if (pagedProducts.isEmpty()) {
                log.warn(String.format("Products list is empty with searchQuery '%s' and page %s",
                        product.getSearchPattern(), pageable.toString()));
            }
        }
        if (loopCounter%eventConfig.getConfigLoopSkips() == 0) {
            productRepository.save(product);
            count++;
        }
        return count;
    }
}
