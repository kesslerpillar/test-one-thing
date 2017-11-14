package com.pillar.mail;

import com.pillar.invoice.Invoice;

public interface Mailer {

    void mail(Mailable invoicable, Invoice invoice);
}
