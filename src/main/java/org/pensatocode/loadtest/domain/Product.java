package org.pensatocode.loadtest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @SequenceGenerator(name = "productSequence", sequenceName = "product_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "productSequence")
    private Long id;
    private String description;
    private String searchPattern;
    private Double price;
    private Integer categoryRank;
    private Long categoryId;
    private Long companyId;
    private Boolean original;
}
