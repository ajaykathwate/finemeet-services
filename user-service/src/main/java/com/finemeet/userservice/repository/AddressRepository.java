package com.finemeet.userservice.repository;

import com.finemeet.userservice.entity.Address;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository  extends JpaRepository<Address, UUID> {
}
