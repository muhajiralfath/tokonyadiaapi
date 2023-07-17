package com.enigma.tokonyadia.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductRequest {

    private String productId;
    private String productName;
    private String description;
    private Long price;
    private Integer stock;
    private String storeId;

}
