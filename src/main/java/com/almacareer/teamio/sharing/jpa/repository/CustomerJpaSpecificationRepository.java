package com.almacareer.teamio.sharing.jpa.repository;

import com.almacareer.teamio.sharing.jpa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository for customers, extended with JPA specification executor.
 */
@Repository
public interface CustomerJpaSpecificationRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

}