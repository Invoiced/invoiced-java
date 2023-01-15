
invoiced-java
========

This repository contains the Java client library for the [Invoiced](https://invoiced.com) API.

You can find detailed API documentation along with java code snippets [here](https://invoiced.com/docs/api/?java#).

[![CI](https://github.com/Invoiced/invoiced-java/actions/workflows/ci.yml/badge.svg)](https://github.com/Invoiced/invoiced-java/actions/workflows/ci.yml)
[![Coverage Status](https://coveralls.io/repos/github/Invoiced/invoiced-java/badge.svg?branch=master)](https://coveralls.io/github/Invoiced/invoiced-java?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.invoiced/invoiced/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.invoiced/invoiced)

## Installing

You can build the jar file by doing

```
gradle jar
```

or build the complete all in one jar file by doing

```
gradle fatJar
```

## Requirements

- Gradle 5.6.4
- Java 8+

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
Payment payment = invoiced.newPayment();
payment.amount = invoice.balance;
payment.method = "check";
paymentItem = new PaymentItem();
paymentItem.type = "invoice";
paymentItem.invoice = invoice.id;
paymentItem.amount = invoice.balance;
payment.appliedTo = PaymentItem{paymentItem};
payment.create();
```

If you want to use the sandbox API instead then you must set the second argument on the client to `true` like this:

```java
import com.invoiced.entity.Connection;

Connection invoiced = new Connection("{YOUR_API_KEY}",true);
```

## Developing


The test suite can be ran with `gradle test`

## Deployment

Here is a useful [guide](http://www.albertgao.xyz/2018/01/18/how-to-publish-artifact-to-maven-central-via-gradle/) for deploying to Maven.