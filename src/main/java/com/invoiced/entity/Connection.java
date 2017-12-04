package com.invoiced.entity;

import java.util.HashMap;

import com.invoiced.exception.ApiException;
import com.invoiced.exception.AuthException;
import com.invoiced.exception.ConnException;
import com.invoiced.exception.InvalidRequestException;
import com.invoiced.exception.InvoicedException;
import com.invoiced.exception.RateLimitException;
import com.invoiced.util.ListResponse;
import com.invoiced.util.Util;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.options.Options;

public class Connection {

	private final String apiKey;
	private final static String Accept = "application/json";
	private boolean sandBox;
	private final static String baseEndPointProduction = "https://api.invoiced.com";
	private final static String baseEndPointSandbox = "https://api.sandbox.invoiced.com";
	private final static String baseEndPointLocal = "http://localhost:8080";

	private boolean testMode;
	private boolean autoRefresh;

	protected String post(String url, HashMap<String, Object> queryParms, String jsonBody) throws InvoicedException {

		String responseString = "";
		int responseCode = -1;

		if (this.autoRefresh) {
			this.refreshUnirestConnection();
		}

		try {
			HttpResponse<String> response = Unirest.post(url).basicAuth(this.apiKey, "")
					.header("accept", Connection.Accept).header("Content-Type", "application/json")
					.queryString(queryParms).body(jsonBody).asString();
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

	public static void closeAll() {
		try {
			Unirest.shutdown();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String patch(String url, String jsonBody) throws InvoicedException {

		String responseString = "";
		int responseCode = -1;

		if (this.autoRefresh) {
			this.refreshUnirestConnection();
		}

		try {
			HttpResponse<String> response = Unirest.patch(url).basicAuth(this.apiKey, "")
					.header("accept", Connection.Accept).header("Content-Type", "application/json").body(jsonBody)
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

		String responseString = "";
		int responseCode = -1;

		if (this.autoRefresh) {
			this.refreshUnirestConnection();
		}

		try {
			HttpResponse<String> response = Unirest.get(url).basicAuth(this.apiKey, "")
					.header("accept", Connection.Accept).header("Content-Type", "application/json")
					.queryString(queryParms).asString();

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

	protected ListResponse getList(String url, HashMap<String, Object> queryParms) throws InvoicedException {

		String responseString = "";
		int responseCode = -1;

		if (this.autoRefresh) {
			this.refreshUnirestConnection();
		}

		ListResponse apiResult = null;

		try {
			HttpResponse<String> response = Unirest.get(url).basicAuth(this.apiKey, "")
					.header("accept", Connection.Accept).header("Content-Type", "application/json")
					.queryString(queryParms).asString();

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

		int responseCode = -1;
		String responseString = "";

		if (this.autoRefresh) {
			this.refreshUnirestConnection();
		}

		try {
			HttpResponse<String> response = Unirest.delete(url).basicAuth(this.apiKey, "")
					.header("accept", Connection.Accept).header("Content-Type", "application/json").asString();

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

	private void refreshUnirestConnection() {
		Connection.closeAll();
		Options.refresh();
	}

	public Connection(String apiKey, boolean sandBox) {

		this.apiKey = apiKey;
		this.sandBox = sandBox;
		this.testMode = false;
		this.autoRefresh = true;
	}

	public void autoRefreshOff() {
		this.autoRefresh = false;
	}

	public final Invoice newInvoice() {
		return new Invoice(this);
	}

	public final Customer newCustomer() {
		return new Customer(this);
	}

	public final Transaction newTransaction() {
		return new Transaction(this);
	}

	public final Subscription newSubscription() {
		return new Subscription(this);
	}

	public final Event newEvent() {
		return new Event(this);
	}

	public final File newFile() {
		return new File(this);
	}

	protected final void testModeOn() {
		this.testMode = true;
	}

	protected final String baseUrl() {

		if (this.testMode == true) {
			return baseEndPointLocal;
		}

		if (this.sandBox == true) {

			return baseEndPointSandbox;

		}

		return baseEndPointProduction;
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
