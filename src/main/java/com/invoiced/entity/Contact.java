package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFilter("customFilter")
public final class Contact extends AbstractEntity<Contact> {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("id")
  public Long id;

  @JsonProperty(value = "object", access = JsonProperty.Access.WRITE_ONLY)
  public String object;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("name")
  public String name;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("email")
  public String email;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("primary")
  public Boolean primary;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("address1")
  public String address1;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("address2")
  public String address2;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("city")
  public String city;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("state")
  public String state;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("postal_code")
  public String postalCode;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("country")
  public String country;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
  public Long createdAt;

  @JsonProperty(value = "updated_at", access = JsonProperty.Access.WRITE_ONLY)
  public Long updatedAt;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("title")
  public String title;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("phone")
  public String phone;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("sms_enabled")
  public Boolean smsEnabled;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("department")
  public String department;

  Contact(Connection conn) {
    super(conn, Contact.class);
    this.entityName = "/contacts";
  }

  Contact() {
    super(Contact.class);
    this.entityName = "/contacts";
  }

  @Override
  @JsonIgnore
  protected String getEntityId() {
    return String.valueOf(this.id);
  }

  @Override
  @JsonIgnore
  protected String[] getCreateExclusions() {
    return new String[] {"id", "created_at"};
  }

  @Override
  @JsonIgnore
  protected String[] getSaveExclusions() {
    return new String[] {"id", "created_at"};
  }
}
