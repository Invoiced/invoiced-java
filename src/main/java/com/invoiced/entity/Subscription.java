package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;

public class Subscription extends AbstractEntity<Subscription> {

	public Subscription(Connection conn) {
		super(conn, Subscription.class);

	}

	Subscription() {
		super(Subscription.class);

	}

	@Override
	@JsonIgnore
	protected long getEntityId() {
		return this.id;
	}

	@Override
	@JsonIgnore
	protected String getEntityName() {
		return "subscriptions";
	}

	@Override
	@JsonIgnore
	protected boolean hasCRUD() {
		return true;
	}

	@Override
	@JsonIgnore
	protected boolean hasList() {
		return true;
	}

	@Override
	@JsonIgnore
	protected boolean isSubEntity() {
		return false;
	}

	@Override
	@JsonIgnore
	protected void setParentID(long parentID) {

	}

	@Override
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

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("plan")
	public String plan;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("start_date")
	public long startDate;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("bill_in")
	public String billIn;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("quantity")
	public int quantity;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("cycles")
	public int cycles;

	@JsonProperty(value = "period_start", access = JsonProperty.Access.WRITE_ONLY)
	public long periodStart;

	@JsonProperty(value = "period_end", access = JsonProperty.Access.WRITE_ONLY)
	public long periodEnd;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("snap_to_nth_day")
	public long snapToNthDay;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("cancel_at_period_end")
	public Boolean cancelAtPeriodEnd;

	@JsonProperty(value = "canceled_at", access = JsonProperty.Access.WRITE_ONLY)
	public long canceledAt;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("prorate")
	public Boolean prorate;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("proration_date")
	public long prorationDate;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("paused")
	public Boolean paused;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("contract_period_start")
	public long contractPeriodStart;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("contract_period_end")
	public long contractPeriodEnd;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("contract_renewal_cycles")
	public long contractRenewalCycles;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("contract_renewal_mode")
	public String contractRenewalMode;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("start_date")
	public long startDate;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("status")
	public String status;

	@JsonProperty(value = "recurring_total", access = JsonProperty.Access.WRITE_ONLY)
	public long recurringTotal;
	
	@JsonProperty(value = "mrr", access = JsonProperty.Access.WRITE_ONLY)
	public long mrr;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("addons")
	public SubscriptionAddon[] addons;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("discounts")
	public String[] discounts;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("taxes")
	public String[] taxes;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("pending_line_items")
	public String[] pendingLineItems;

	@JsonProperty(value = "url", access = JsonProperty.Access.WRITE_ONLY)
	public String url;

	@JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
	public long createdAt;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("metadata")
	public Object metadata;

	public void cancel() throws EntityException {

		try {

			this.delete();

		} catch (Throwable c) {

			throw new EntityException(c);
		}

	}

	public SubscriptionPreview preview() throws EntityException {

		String url = this.getConnection().baseUrl() + "/" + this.getEntityName() + "/preview";

		SubscriptionPreview preview = null;

		try {

			String previewJson = this.toJsonString();

			String response = this.getConnection().post(url, null, previewJson);

			preview = Util.getMapper().readValue(response, SubscriptionPreview.class);
			preview.setConnection(this.getConnection());
			preview.setClass(SubscriptionPreview.class);

		} catch (Throwable c) {

			throw new EntityException(c);
		}

		return preview;
	}

}
