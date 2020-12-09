package com.invoiced.entity;

import com.invoiced.exception.*;
import com.invoiced.util.ListResponse;
import com.invoiced.util.Util;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.options.Options;

import java.util.HashMap;

public final class Connection {

  private static final String Accept = "application/json";
  private static final String baseEndPointProduction = "https://api.invoiced.com";
  private static final String baseEndPointSandbox = "https://api.sandbox.invoiced.com";
  private final String apiKey;
  private final String baseUrl;
  private boolean autoRefresh = true;

  public Connection(String apiKey, boolean sandbox) {
    this.apiKey = apiKey;

    if (sandbox) {
      baseUrl = baseEndPointSandbox;
    } else {
      baseUrl = baseEndPointProduction;
    }
  }

  public Connection(String apiKey, String baseUrl) {
    this.apiKey = apiKey;
    this.baseUrl = baseUrl;
  }

  public static void closeAll() {
    try {
      Unirest.shutdown();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected String post(String url, HashMap<String, Object> queryParms, String jsonBody)
      throws InvoicedException {

    String responseString;
    int responseCode;

    if (this.autoRefresh) {
      this.refreshUnirestConnection();
    }

    url = this.baseUrl() + url;

    try {
      HttpResponse<String> response =
          Unirest.post(url)
              .basicAuth(this.apiKey, "")
              .header("accept", Connection.Accept)
              .header("Content-Type", "application/json")
              .queryString(queryParms)
              .body(jsonBody)
              .asString();
      responseString = response.getBody().toString();
      responseCode = response.getStatus();

    } catch (Throwable c) {
      throw new ConnException(c);
    }

    if (responseCode >= 400) {
      throw this.handleApiError(responseCode, responseString);
    }

    return responseString;
  }

  protected String patch(String url, String jsonBody) throws InvoicedException {

    String responseString;
    int responseCode;

    if (this.autoRefresh) {
      this.refreshUnirestConnection();
    }

    url = this.baseUrl() + url;

    try {
      HttpResponse<String> response =
          Unirest.patch(url)
              .basicAuth(this.apiKey, "")
              .header("accept", Connection.Accept)
              .header("Content-Type", "application/json")
              .body(jsonBody)
              .asString();
      responseString = response.getBody().toString();
      responseCode = response.getStatus();

    } catch (Throwable c) {
      throw new ConnException(c);
    }

    if (responseCode >= 400) {
      throw this.handleApiError(responseCode, responseString);
    }

    return responseString;
  }

  protected String get(String url, HashMap<String, Object> queryParms) throws InvoicedException {

    String responseString;
    int responseCode;

    if (this.autoRefresh) {
      this.refreshUnirestConnection();
    }

    url = this.baseUrl() + url;

    try {
      HttpResponse<String> response =
          Unirest.get(url)
              .basicAuth(this.apiKey, "")
              .header("accept", Connection.Accept)
              .header("Content-Type", "application/json")
              .queryString(queryParms)
              .asString();

      responseString = response.getBody().toString();
      responseCode = response.getStatus();

    } catch (Throwable c) {
      throw new ConnException(c);
    }

    if (responseCode >= 400) {
      throw this.handleApiError(responseCode, responseString);
    }

    return responseString;
  }

  protected ListResponse getList(String url, HashMap<String, Object> queryParms)
      throws InvoicedException {

    String responseString;
    int responseCode;

    if (this.autoRefresh) {
      this.refreshUnirestConnection();
    }

    url = this.baseUrl() + url;

    ListResponse apiResult;

    try {
      HttpResponse<String> response =
          Unirest.get(url)
              .basicAuth(this.apiKey, "")
              .header("accept", Connection.Accept)
              .header("Content-Type", "application/json")
              .queryString(queryParms)
              .asString();

      responseString = response.getBody().toString();
      responseCode = response.getStatus();

      String linkString = (response.getHeaders().get("Link")).get(0);

      int totalCount = Integer.parseInt((response.getHeaders().get("X-Total-Count")).get(0));

      HashMap<String, String> links = Util.parseLinks(linkString);

      apiResult = new ListResponse(responseString, links, totalCount);

    } catch (Throwable c) {
      throw new ConnException(c);
    }

    if (responseCode >= 400) {
      throw this.handleApiError(responseCode, responseString);
    }

    return apiResult;
  }

  protected void delete(String url) throws InvoicedException {

    int responseCode;
    String responseString = "";

    if (this.autoRefresh) {
      this.refreshUnirestConnection();
    }

    url = this.baseUrl() + url;

    try {
      HttpResponse<String> response =
          Unirest.delete(url)
              .basicAuth(this.apiKey, "")
              .header("accept", Connection.Accept)
              .header("Content-Type", "application/json")
              .asString();

      responseCode = response.getStatus();

      if (response.getBody() != null) {
        responseString = response.getBody().toString();
      }

    } catch (Throwable c) {
      throw new ConnException(c);
    }

    if (responseCode >= 400) {
      throw this.handleApiError(responseCode, responseString);
    }
  }

  // not all delete requests return 204 so alternate delete is needed for these
  protected String deleteWithResponse(String url) throws InvoicedException {

    String responseString;
    int responseCode;

    if (this.autoRefresh) {
      this.refreshUnirestConnection();
    }

    url = this.baseUrl() + url;

    try {
      HttpResponse<String> response =
          Unirest.delete(url)
              .basicAuth(this.apiKey, "")
              .header("accept", Connection.Accept)
              .header("Content-Type", "application/json")
              .asString();

      responseString = response.getBody().toString();
      responseCode = response.getStatus();

    } catch (Throwable c) {
      throw new ConnException(c);
    }

    if (responseCode >= 400) {
      throw this.handleApiError(responseCode, responseString);
    }

    return responseString;
  }

  private void refreshUnirestConnection() {
    Connection.closeAll();
    Options.refresh();
  }

  public void autoRefreshOff() {
    this.autoRefresh = false;
  }

  public final Charge newCharge() {
    return new Charge(this);
  }

  public final Coupon newCoupon() {
    return new Coupon(this);
  }

  public final CreditBalanceAdjustment newCreditBalanceAdjustment() {
    return new CreditBalanceAdjustment(this);
  }

  public final CreditNote newCreditNote() {
    return new CreditNote(this);
  }

  public final Customer newCustomer() {
    return new Customer(this);
  }

  public final Estimate newEstimate() {
    return new Estimate(this);
  }

  public final Event newEvent() {
    return new Event(this);
  }

  public final File newFile() {
    return new File(this);
  }

  public final Invoice newInvoice() {
    return new Invoice(this);
  }

  public final Item newItem() {
    return new Item(this);
  }

  public final Note newNote() {
    return new Note(this);
  }

  public final Payment newPayment() {
    return new Payment(this);
  }

  public final Plan newPlan() {
    return new Plan(this);
  }

  public final Refund newRefund() {
    return new Refund(this);
  }

  public final Subscription newSubscription() {
    return new Subscription(this);
  }

  public final Task newTask() {
    return new Task(this);
  }

  public final TaxRate newTaxRate() {
    return new TaxRate(this);
  }

  protected final String baseUrl() {
    return this.baseUrl;
  }

  protected final InvoicedException handleApiError(int responseCode, String responseBody) {
    if (responseCode == 401) {
      return new AuthException(responseBody);
    } else if (responseCode == 400) {
      return new InvalidRequestException(responseBody);
    } else if (responseCode == 429) {
      return new RateLimitException(responseBody);
    }

    return new ApiException(responseBody);
  }
}
