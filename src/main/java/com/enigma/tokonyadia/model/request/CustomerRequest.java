package com.enigma.tokonyadia.model.request;

import com.enigma.tokonyadia.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CustomerRequest {
    private String id;
    private String name;
    private Address address;
    private String mobilePhone;
    private String email;
}
