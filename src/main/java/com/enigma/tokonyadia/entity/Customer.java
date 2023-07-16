package com.enigma.tokonyadia.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_customer")
public class Customer {

    @Id
    @GenericGenerator(strategy = "uuid2", name = "system-uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(targetEntity = Address.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "mobile_phone", nullable = false, unique = true)
    private String mobilePhone;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

}
