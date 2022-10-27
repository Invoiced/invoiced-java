package com.invoiced;

import com.invoiced.entity.*;
import com.invoiced.exception.EntityException;

abstract class help {
    public static void main(String[] args) throws EntityException {
        Connection conn = new Connection("{API_KEY}", false);

        EntityList<Subscription> subscriptions = conn.newSubscription().listAll();
        EntityList<Customer> customers = conn.newCustomer().listAll();
        EntityList<Item> items = conn.newItem().listAll();
        EntityList<CreditNote> cns = conn.newCreditNote().listAll();

        System.out.println(subscriptions);
        System.out.println(customers);
        System.out.println(items);
        System.out.println(cns);
    }
}