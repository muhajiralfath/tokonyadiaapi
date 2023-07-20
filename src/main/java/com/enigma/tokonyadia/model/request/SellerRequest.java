package com.enigma.tokonyadia.model.request;

import com.enigma.tokonyadia.entity.UserCredential;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SellerRequest {
    private String username;
    private String email;
    private String storeName;
    private String mobilePhone;
}
