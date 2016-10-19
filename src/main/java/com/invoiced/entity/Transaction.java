package com.invoiced.entity;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

public class Transaction extends AbstractEntity<Transaction> {

	public Transaction(Connection conn) {
		super(conn, Transaction.class);
	}

	Transaction() {
		super(Transaction.class);
	}

	@JsonIgnore
	protected long getEntityId() {
		return this.id;
	}
	@JsonIgnore
	protected String getEntityName() {
		return "transactions";
	}

	@JsonIgnore
	protected boolean hasCRUD() {
		return true;
	}

	@JsonIgnore
	protected boolean hasList() {
		return true;
	}


	@JsonIgnore
	protected boolean isSubEntity() {
		return false;
	}

	@JsonIgnore
	protected void setParentID(long parentID) {

	}

	@JsonIgnore
	protected long getParentID() {
		return -1;
	}



	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("id")
	public long id;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("customer")
	public long customer;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("invoice")
	public long invoice;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("date")
	public Timestamp date;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("type")
	public String type;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("method")
	public String method;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("status")
	public String status;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("gateway")
	public String gateway;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("gateway_id")
	public String gatewayId;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("payment_source")
	public PaymentSource paymentSource;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("currency")
	public String currency;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("amount")
	public double amount;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("fee")
	public double fee;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("notes")
	public String notes;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("failure_reason")
	public String failureReason;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("parent_transaction")
	public long parentTransaction;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("pdf_url")
	public String pdfUrl;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("created_at")
	public Timestamp createdAt;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("metadata")
	public Object metadata;


	@JsonIgnore
	public Transaction refund(double amount) throws EntityException {

		String url = this.getConnection().baseUrl() + "/" + this.getEntityName() + "/" + String.valueOf(this.getEntityId()) + "/refunds";


		RefundRequest refundRequest = new RefundRequest(amount);

		Transaction v1 = null;

		try {


			String refundToJson = refundRequest.toJsonString();

			String response = this.getConnection().post(url, null, refundToJson);

			v1 = Util.getMapper().readValue(response, Transaction.class);
			v1.setConnection(this.getConnection());
			v1.setClass(Transaction.class);



		} catch (Throwable c) {

			throw new EntityException(c);
		}


		return v1;


	}

	@JsonIgnore
	public Email[] send(EmailRequest emailRequest) throws EntityException {

		String url = this.getConnection().baseUrl() + "/" + this.getEntityName() + "/" + String.valueOf(this.getEntityId()) + "/emails";

		Email[] emails = null;


		try {

			String emailRequestJson = emailRequest.toJsonString();

			String response = this.getConnection().post(url, null, emailRequestJson);

			emails = Util.getMapper().readValue(response, Email[].class);


		} catch (Throwable c) {

			throw new EntityException(c);
		}


		return emails;


	}






}
