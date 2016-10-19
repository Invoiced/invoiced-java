invoiced-java
========

This repository contains the Java client library for the [Invoiced](https://invoiced.com) API.

[![Build Status](https://travis-ci.org/Invoiced/invoiced-ruby.svg?branch=master)](https://travis-ci.org/Invoiced/invoiced-ruby)
[![Coverage Status](https://coveralls.io/repos/Invoiced/invoiced-ruby/badge.svg?branch=master&service=github)](https://coveralls.io/github/Invoiced/invoiced-ruby?branch=master)

## Installing

You can build the jar file by doing

```
gradle jar
```

## Requirements

- Gradle 3.0
- Java 7 or Java 8

## Usage

First, you must instantiate a new client

```java
import com.invoiced.entity.Connection;

Connection invoiced = new Connection("{YOUR_API_KEY}",false);
```

Then, API calls can be made like this:
```java
# retrieve invoice
Invoice invoice = invoiced.newInvoice().retrieve({INVOICE_ID});

# mark as paid
Transaction transaction = invoiced.newTransaction();
transaction.invoice = invoice.id;
transaction.amount = invoice.balance;
transaction.method = "check";
transaction.create();
```

If you want to use the sandbox API instead then you must set the second argument on the client to `true` like this:

```java
import com.invoiced.entity.Connection;

Connection invoiced = new Connection("{YOUR_API_KEY}",true);
```

## Developing


The test suite can be ran with `gradle test`