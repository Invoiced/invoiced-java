invoiced-java
========

This repository contains the Java client library for the [Invoiced](https://invoiced.com) API.

You can find detailed API documentation along with java code snippets [here](https://invoiced.com/docs/api/?java#).

[![Build Status](https://travis-ci.org/Invoiced/invoiced-java.svg?branch=master)](https://travis-ci.org/Invoiced/invoiced-java)
[![Coverage Status](https://coveralls.io/repos/github/Invoiced/invoiced-java/badge.svg?branch=master)](https://coveralls.io/github/Invoiced/invoiced-java?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.invoiced/invoiced-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.invoiced/invoiced-java)

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
PaymentItem[] appliedTo = new PaymentItem[1];
appliedTo[0] = new PaymentItem();
appliedTo[0].type = "invoice";
appliedTo[0].invoice = invoice.id;
appliedTo[0].amount = invoice.balance;
payment.appliedTo = appliedTo;
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