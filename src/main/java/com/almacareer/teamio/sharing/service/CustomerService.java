package com.almacareer.teamio.sharing.service;

import com.almacareer.teamio.sharing.jpa.entity.Customer;
import com.almacareer.teamio.sharing.jpa.repository.CustomerJpaSpecificationRepository;
import com.almacareer.teamio.sharing.jpa.repository.CustomerRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerJpaSpecificationRepository customerJpaSpecificationRepository;

    /**
     * Find list of customers with specified page and size.
     *
     * @param page Page
     * @param size Page size
     * @return Page of customers
     */
    public List<Customer> getList(int page, int size) {
        int offset = page * size;
        return customerRepository.findAll(offset, size);
    }

    /**
     * Find page of customers.
     *
     * @param page Page
     * @param size Page size
     * @return Page of customers
     */
    public Page<Customer> findAllCustomers(int page, int size) {
        return customerRepository.findAll(PageRequest.of(page, size));
    }

    /**
     * Find all customers, paged.
     *
     * @param page Page
     * @param size Page size
     * @return Page of customers
     */
    public Page<Customer> findCustomers(CustomerFilter filter, int page, int size) {
        return customerJpaSpecificationRepository.findAll(where(spec(filter)), PageRequest.of(page, size));
    }

    static Specification<Customer> spec(CustomerFilter filter) {
        return (mq, query, builder) -> {
            if (filter == null) {
                return null;
            }

            List<Predicate> predicates = new ArrayList<>();
            if (filter.firstName() != null) {
                predicates.add(builder.equal(mq.get("firstName"), filter.firstName()));
            }
            if (filter.lastName() != null) {
                predicates.add(builder.equal(mq.get("lastName"), filter.lastName()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
