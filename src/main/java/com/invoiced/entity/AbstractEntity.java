package com.invoiced.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import javax.swing.text.html.parser.Entity;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.invoiced.exception.EntityException;
import com.invoiced.util.ListResponse;
import com.invoiced.util.Util;

public abstract class AbstractEntity<T extends AbstractEntity> {

	private Connection conn;
	protected Class<T> tClass;
	protected String entityName;
	private boolean entityCreated;
	private String listUrl;
	private String parentId;
	private String parentName;

	public AbstractEntity(Connection conn, Class<T> tClass) {
		this.conn = conn;
		this.tClass = tClass;
		this.entityCreated = false;
		this.setEntityName();
	}

	public AbstractEntity(Class<T> tClass) {
		this.tClass = tClass;
		this.entityCreated = false;
		this.setEntityName();
	}

	protected void setConnection(Connection conn) {
		this.conn = conn;
	}

	protected Connection getConnection() {
		return this.conn;
	}

	protected void setClass(Class<T> tClass) {
		this.tClass = tClass;
	}

	protected String getEntityName() {
		return this.entityName;
	}

	protected String getListUrl() {
		if (this.listUrl != null) return this.listUrl;
		else return this.getEntityName();
	}

	protected void setListUrl() {
		if (this.isSubEntity() && this.parentId != null) {
			this.listUrl = this.getParentName() + "/" + this.getParentID() + "/" + this.getEntityName();
		}
	}

	protected static void setFields(Object from, Object to) throws EntityException {
		Field[] fields = from.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				Field fieldFrom = from.getClass().getDeclaredField(field.getName());

				if (Modifier.isPrivate(field.getModifiers())) {
					continue;
				}

				if (Modifier.isPrivate(fieldFrom.getModifiers())) {
					continue;
				}

				if (Modifier.isFinal(field.getModifiers())) {
					continue;
				}

				if (Modifier.isFinal(fieldFrom.getModifiers())) {
					continue;
				}

				Object value = fieldFrom.get(from);
				to.getClass().getDeclaredField(field.getName()).set(to, value);

			} catch (Throwable c) {
				throw new EntityException(c);
			}
		}
	}

	public void create() throws EntityException {

		if (this.entityCreated) {
			return;
		}

		if (!this.hasCRUD()) {
			return;
		}

		String url = this.conn.baseUrl() + "/" + this.getEntityName();
		T v1 = null;

		try {
			String jsonRequest = this.toJsonString();
			String response = this.conn.post(url, null, jsonRequest);

			v1 = Util.getMapper().readValue(response, this.tClass);
			v1.setConnection(this.conn);
			v1.setClass(this.tClass);

			setFields(v1, this);

		} catch (Throwable c) {
			throw new EntityException(c);
		}

		this.entityCreated = true;
	}

	@Override
	public String toString() {

		String s1 = super.toString();

		try {

			String jsonString = this.toJsonString();
			s1 = s1 + " JSON: " + jsonString;

		} catch (Throwable c) {
			throw new RuntimeException(c);
		}

		return s1;
	}

	public String toJsonString() throws EntityException {

		String s = "Entity";

		try {

			s = Util.getMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(this);

		} catch (Throwable c) {
			throw new EntityException(c);
		}

		return s;
	}

	public void save() throws EntityException {

		if (!this.hasCRUD()) {
			return;
		}

		String url = null;

		url = this.conn.baseUrl() + "/" + this.getEntityName() + "/" + this.getEntityIdString();

		T v1 = null;

		try {
			String jsonRequest = this.toJsonString();
			String response = this.conn.patch(url, jsonRequest);

			v1 = Util.getMapper().readValue(response, this.tClass);

			setFields(v1, this);

		} catch (Throwable c) {
			throw new EntityException(c);
		}
	}

	public T retrieve() throws EntityException {

		String url = this.conn.baseUrl() + "/" + this.getEntityName();

		T v1 = null;

		try {

			String response = this.conn.get(url, null);

			v1 = Util.getMapper().readValue(response, this.tClass);
			v1.setConnection(this.conn);
			v1.setClass(this.tClass);

			if (this.isSubEntity()) {
				v1.setParentID(this.getParentID());
				v1.setParentName(this.getParentName());
				v1.setEntityName();
			}

		} catch (Throwable c) {

			throw new EntityException(c);
		}

		return v1;
	}

	public T retrieve(long id) throws EntityException {

		T v1 = null;

		try {

			v1 = this.retrieve(id, null);

		} catch (EntityException e) {

			throw e;
		}

		return v1;
	}

	public T retrieve(long id, HashMap<String, Object> queryParms) throws EntityException {

		String url = this.conn.baseUrl() + "/" + this.getEntityName() + "/" + String.valueOf(id);

		T v1 = null;

		try {

			String response = this.conn.get(url, queryParms);

			v1 = Util.getMapper().readValue(response, this.tClass);
			v1.setConnection(this.conn);
			v1.setClass(this.tClass);

			if (this.isSubEntity()) {
				v1.setParentID(this.getParentID());
				v1.setParentName(this.getParentName());
				v1.setEntityName();
			}

		} catch (Throwable c) {

			throw new EntityException(c);
		}

		return v1;
	}

	public T retrieve(String id) throws EntityException {

		T v1 = null;

		try {

			v1 = this.retrieve(id, null);

		} catch (EntityException e) {

			throw e;
		}

		return v1;
	}

	public T retrieve(String id, HashMap<String, Object> queryParms) throws EntityException {

		String url = this.conn.baseUrl() + "/" + this.getEntityName() + "/" + id;

		T v1 = null;

		try {

			String response = this.conn.get(url, queryParms);

			v1 = Util.getMapper().readValue(response, this.tClass);
			v1.setConnection(this.conn);
			v1.setClass(this.tClass);

			if (this.isSubEntity()) {
				v1.setParentID(this.getParentID());
				v1.setParentName(this.getParentName());
				v1.setEntityName();
			}

		} catch (Throwable c) {

			throw new EntityException(c);
		}

		return v1;
	}

	public void delete() throws EntityException {

		if (!this.hasCRUD()) {
			return;
		}

		String url = null;

		url = this.conn.baseUrl() + "/" + this.getEntityName() + "/" + this.getEntityIdString();


		try {

			this.conn.delete(url);

		} catch (Throwable c) {

			throw new EntityException(c);
		}
	}

	public EntityList<T> list(String nextURL) throws EntityException {

		EntityList<T> entities = null;

		try {

			entities = this.list(nextURL, null);

		} catch (EntityException e) {
			throw e;
		}

		return entities;
	}

	public EntityList<T> listAll() throws EntityException {
		EntityList<T> entities = null;

		try {

			entities = this.listAll(null);

		} catch (EntityException e) {
			throw e;
		}

		return entities;
	}

	public EntityList<T> list(String nextURL, HashMap<String, Object> queryParms) throws EntityException {

		if (!this.hasList()) {
			return null;
		}

		String url = this.conn.baseUrl() + "/" + this.getListUrl();

		if (nextURL != null && nextURL.length() > 0) {
			url = nextURL;
		}

		EntityList<T> entities = null;

		try {

			ListResponse response = this.conn.getList(url, queryParms);

			JavaType collectionType = Util.getMapper().getTypeFactory().constructCollectionType(EntityList.class,
			                          this.tClass);

			entities = Util.getMapper().readValue(response.getResult(), collectionType);

			entities.setLinkURLs(response.getLinks());
			entities.setTotalCount(response.getTotalCount());

			for (T entity : entities) {

				entity.setConnection(this.conn);
				entity.setClass(this.tClass);
				if (this.isSubEntity()) {
					entity.setParentID(this.getParentID());
					entity.setParentName(this.getParentName());
					entity.setEntityName();
				}

			}

		} catch (Throwable c) {
			throw new EntityException(c);
		}

		return entities;
	}

	public EntityList<T> listAll(HashMap<String, Object> queryParms) throws EntityException {
		EntityList<T> entities = null;
		EntityList<T> tmp = null;

		if (!this.hasList()) {
			return null;
		}

		String url = null;

		try {

			do {

				tmp = this.list(url, queryParms);
				if (entities == null) {
					entities = tmp;
				} else {
					entities.addAll(tmp);
				}

				url = tmp.getLinkURLs().get("next");

			} while (tmp.getLinkURLs().get("next") != null
			         && !tmp.getLinkURLs().get("self").equals(tmp.getLinkURLs().get("last")));

			entities.setLinkURLs(tmp.getLinkURLs());

		} catch (EntityException e) {
			throw e;
		} catch (Throwable c) {
			throw new EntityException(c);
		}

		return entities;
	}

	abstract long getEntityId() throws EntityException;

	abstract String getEntityIdString() throws EntityException;

	abstract void setEntityName();

	abstract boolean hasCRUD();

	abstract boolean hasList();

	abstract boolean isSubEntity();

	abstract boolean idIsString();

	// on most objects, parent entities do not exist
	protected void setParentID(String parentID) {
		if (this.isSubEntity()) {
			this.parentId = parentID;
		}
	}
	
	protected void setParentName(String parentName) {
		if (this.isSubEntity()) {
			this.parentName = parentName;
		}
	}
	
	protected String getParentID() {
		if (this.isSubEntity()) return this.parentId;
		else return null;
	}

	protected String getParentName() {
		if (this.isSubEntity()) return this.parentName;
		else return null;
	}

}