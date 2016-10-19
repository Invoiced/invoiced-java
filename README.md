invoiced-java
========

This repository contains the Java client library for the [Invoiced](https://invoiced.com) API.

[![Build Status](https://travis-ci.org/Invoiced/invoiced-java.svg?branch=master)](https://travis-ci.org/Invoiced/invoiced-java)
[![Coverage Status](https://coveralls.io/repos/github/Invoiced/invoiced-java/badge.svg?branch=master)](https://coveralls.io/github/Invoiced/invoiced-java?branch=master)

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