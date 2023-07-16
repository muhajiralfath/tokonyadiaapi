package com.enigma.tokonyadia.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String productId;
    private String productName;
    private String description;
    private Long price;
    private Integer stock;
    private String storeId;

}
