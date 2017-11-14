package com.pillar.billing;

import com.pillar.customer.Customer;
import com.pillar.customer.CustomerRepository;
import com.pillar.email.Emailer;
import com.pillar.invoice.Invoice;
import com.pillar.invoice.InvoiceGenerator;
import com.pillar.mail.Mailer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EverythingBillerTest {

    @InjectMocks
    private Biller biller;

    @Mock
    private CustomerRepository cutomerRepository;

    @Mock
    private InvoiceGenerator invoiceGenerator;

    @Mock
    private Emailer emailer;

    @Mock
    private Mailer mailer;

    @Mock
    private Customer firstCustomer;

    @Mock
    private Customer secondCustomer;

    @Mock
    private Invoice firstInvoice;

    @Mock
    private Invoice secondInvoice;

    @Before
    public void setUp(){
        List<Customer> customersWithOutstandingBalances = Arrays.asList(firstCustomer, secondCustomer);
        when(cutomerRepository.findWithOutstandingBalance()).thenReturn(customersWithOutstandingBalances);

        when(invoiceGenerator.createFor(firstCustomer)).thenReturn(firstInvoice);
        when(invoiceGenerator.createFor(secondCustomer)).thenReturn(secondInvoice);
    }

    @Test
    public void sendEmailInvoiceToCustomersThatHaveAnOustandingBalance(){
        when(firstCustomer.shouldEmail()).thenReturn(true);
        when(secondCustomer.shouldEmail()).thenReturn(true);

        biller.sendInvoicesToCustomersWithOutstandingBalances();

        verify(emailer).send(firstCustomer, firstInvoice);
        verify(emailer).send(secondCustomer, secondInvoice);

        when(firstCustomer.shouldEmail()).thenReturn(false);
        when(secondCustomer.shouldEmail()).thenReturn(false);

        biller.sendInvoicesToCustomersWithOutstandingBalances();

        verify(mailer).mail(firstCustomer, firstInvoice);
        verify(mailer).mail(secondCustomer, secondInvoice);

        when(firstCustomer.shouldEmail()).thenReturn(true);
        when(secondCustomer.shouldEmail()).thenReturn(false);

        biller.sendInvoicesToCustomersWithOutstandingBalances();

        verify(emailer, atLeastOnce()).send(firstCustomer, firstInvoice);
        verify(mailer, atLeastOnce()).mail(secondCustomer, secondInvoice);

    }
}
