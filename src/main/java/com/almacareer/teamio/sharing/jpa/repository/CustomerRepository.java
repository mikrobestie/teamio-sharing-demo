package com.almacareer.teamio.sharing.jpa.repository;

import com.almacareer.teamio.sharing.jpa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = """
            select *
            from customer
            offset :offset limit :limit
            """, nativeQuery = true)
    List<Customer> findAll(int offset, int limit);

}