package com.example.bikash.SpringTest.Repos;

import com.example.bikash.SpringTest.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository  extends JpaRepository<Address,Long> {
}
