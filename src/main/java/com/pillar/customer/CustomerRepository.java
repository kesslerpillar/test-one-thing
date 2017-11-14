package com.pillar.customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findWithOutstandingBalance();
}
