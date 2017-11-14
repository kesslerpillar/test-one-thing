package com.pillar.invoice;

import com.pillar.customer.Customer;

public interface InvoiceGenerator {
    Invoice createFor(Customer customer);
}
