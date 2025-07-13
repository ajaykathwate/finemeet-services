package com.finemeet.userservice.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID addressId;

    private String street;

    private String city;

    private String state;

    private String postcode;

    private String country;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Address address = (Address) o;
        return new EqualsBuilder()
                .append(addressId, address.addressId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(addressId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + addressId +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postcode='" + postcode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

