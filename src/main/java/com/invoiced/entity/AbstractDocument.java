package com.invoiced.entity;

import com.fasterxml.jackson.annotation.*;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

@JsonFilter("customFilter")
public abstract class AbstractDocument<T extends AbstractDocument<T>> extends AbstractEntity<T> {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("id")
    public Long id;

    @JsonProperty(value = "object", access = JsonProperty.Access.WRITE_ONLY)
    public String object;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("customer")
    public Long customer;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("name")
    public String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("number")
    public String number;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("currency")
    public String currency;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("draft")
    public Boolean draft;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("closed")
    public Boolean closed;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("voided")
    public Boolean voided;

    @JsonProperty(value = "status", access = JsonProperty.Access.WRITE_ONLY)
    public String status;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("date")
    public Long date;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("items")
    public LineItem[] items;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("notes")
    public String notes;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("subtotal")
    public Double subtotal;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("discounts")
    public Discount[] discounts;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("taxes")
    public Tax[] taxes;

    @JsonProperty(value = "total", access = JsonProperty.Access.WRITE_ONLY)
    public Double total;

    @JsonProperty(value = "url", access = JsonProperty.Access.WRITE_ONLY)
    public String url;

    @JsonProperty(value = "pdf_url", access = JsonProperty.Access.WRITE_ONLY)
    public String pdfUrl;

    @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
    public Long createdAt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("metadata")
    public Object metadata;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("attachments")
    public Long[] attachments;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("calculate_taxes")
    public Boolean calculateTaxes;

    public AbstractDocument(Connection conn, Class aClass) {
        super(conn, aClass);
    }

    public AbstractDocument(Class aClass) {
        super(aClass);
    }

    @Override
    @JsonIgnore
    protected String getEntityId() {
        return String.valueOf(this.id);
    }

    @JsonIgnore
    public Email[] send(EmailRequest emailRequest) throws EntityException {
        String url = this.getEndpoint(true) + "/emails";

        try {
            String emailRequestJson = emailRequest.toJsonString();

            String response = this.getConnection().post(url, null, emailRequestJson);

            return Util.getMapper().readValue(response, Email[].class);
        } catch (Throwable c) {
            throw new EntityException(c);
        }
    }

    @JsonIgnore
    public Attachment[] listAttachments() throws EntityException {
        String url = this.getEndpoint(true) + "/attachments";

        try {
            String response = this.getConnection().post(url, null, "");

            return Util.getMapper().readValue(response, Attachment[].class);
        } catch (Throwable c) {
            throw new EntityException(c);
        }
    }

    @JsonIgnore
    public void voidDocument() throws EntityException {
        String url = this.getEndpoint(true) + "/void";

        try {
            String response = this.getConnection().post(url, null, "{}");

            Object document = Util.getMapper().readValue(response, tClass);
            setFields(document, this);
        } catch (Throwable c) {
            throw new EntityException(c);
        }
    }
}
