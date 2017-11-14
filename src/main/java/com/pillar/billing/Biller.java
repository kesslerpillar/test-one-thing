package com.pillar.billing;

import com.pillar.customer.CustomerRepository;
import com.pillar.customer.Customer;
import com.pillar.email.Emailer;
import com.pillar.invoice.Invoice;
import com.pillar.invoice.InvoiceGenerator;
import com.pillar.mail.Mailer;

import java.util.List;

public class Biller {

    private CustomerRepository customerRepository;
    private InvoiceGenerator invoiceGenerator;
    private Emailer emailer;
    private Mailer mailer;

    public Biller(CustomerRepository customerRepository, InvoiceGenerator invoiceGenerator, Emailer emailer, Mailer mailer){
        this.customerRepository = customerRepository;
        this.invoiceGenerator = invoiceGenerator;
        this.emailer = emailer;
        this.mailer = mailer;
    }

    public void sendInvoicesToCustomersWithOutstandingBalances() {
        List<Customer> customersWithOutstandingBalances = customerRepository.findWithOutstandingBalance();
        for(Customer customer : customersWithOutstandingBalances){
            Invoice invoice = invoiceGenerator.createFor(customer);
            if(customer.shouldEmail()) {
                emailer.send(customer, invoice);
            }else{
                mailer.mail(customer, invoice);
            }
        }
    }
}
