package com.almacareer.teamio.sharing.rest;

import com.almacareer.teamio.sharing.jpa.entity.Customer;
import com.almacareer.teamio.sharing.service.CustomerFilter;
import com.almacareer.teamio.sharing.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class RestCustomer {

    private final CustomerService customerService;

    /**
     * Get page of customers.
     *
     * @param page Page number
     * @param size Page size
     * @return Page of customers
     */
    @GetMapping("/list")
    public List<Customer> getList(@RequestParam int page, @RequestParam int size) {
        return customerService.getList(page, size);
    }

    /**
     * Get page of customers without filtering.
     *
     * @param page Page number
     * @param size Page size
     * @return Page of customers
     */
    @GetMapping("/all")
    public SimplePage<Customer> getCustomers(@RequestParam int page, @RequestParam int size) {
        return simplePage(customerService.findAllCustomers(page, size));
    }

    /**
     * Get page of customers with filtering.
     *
     * @param filter Customer filter
     * @param page   Page number
     * @param size   Page size
     * @return Page of customers
     */
    @GetMapping
    public SimplePage<Customer> findCustomers(CustomerFilter filter, @RequestParam int page, @RequestParam int size) {
        return simplePage(customerService.findCustomers(filter, page, size));
    }

    private <T> SimplePage<T> simplePage(Page<T> page) {
        return new SimplePage<>(page.getContent(), page.getNumber(), page.getSize(), page.getTotalPages(),
                (int) page.getTotalElements());
    }

    public record SimplePage<T>(List<T> items, int page, int size, int totalPages, int totalItems) {
    }
}
