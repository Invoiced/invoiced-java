package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;

@JsonFilter("customFilter")
public final class PaymentPlan extends AbstractEntity<PaymentPlan> {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("id")
  public Long id;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("object")
  public String object;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("status")
  public String status;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("installments")
  public PaymentPlanInstallment[] installments;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("approval")
  public Object approval;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
  public long createdAt;

  PaymentPlan(Connection conn) {
    super(conn, PaymentPlan.class);
    this.entityName = "/payment_plan";
  }

  PaymentPlan() {
    super(PaymentPlan.class);
    this.entityName = "/payment_plan";
  }

  @Override
  @JsonIgnore
  protected String getEntityId() {
    return String.valueOf(this.id);
  }

  @Override
  @JsonIgnore
  protected boolean hasList() {
    return false;
  }

  @Override
  @JsonIgnore
  protected String[] getCreateExclusions() {
    return new String[] {"id", "object", "status", "approval", "created_at"};
  }

  @Override
  @JsonIgnore
  protected String[] getSaveExclusions() {
    return new String[] {};
  }

  @Override
  public void delete() throws EntityException {
    super.delete(false);
  }

  public void cancel() throws EntityException {
    try {
      this.delete();
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }
}
