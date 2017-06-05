package com.invoiced.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.invoiced.exception.EntityException;
import com.invoiced.util.ListResponse;
import com.invoiced.util.Util;

public abstract class AbstractEntity<T extends AbstractEntity> {

	private Connection conn;
	private Class<T> tClass;
	private boolean entityCreated;

	public AbstractEntity(Connection conn, Class<T> tClass) {
		this.conn = conn;
		this.tClass = tClass;
		this.entityCreated = false;
	}

	public AbstractEntity(Class<T> tClass) {
		this.tClass = tClass;
		this.entityCreated = false;
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

		String url = conn.baseUrl() + "/" + this.getEntityName();
		T v1 = null;

		try {
			String jsonRequest = this.toJsonString();
			String response = conn.post(url, null, jsonRequest);

			v1 = Util.getMapper().readValue(response, tClass);
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
			// s =
			// Util.getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(s);

		} catch (Throwable c) {
			throw new EntityException(c);
		}

		return s;
	}

	public void save() throws EntityException {

		if (!this.hasCRUD()) {
			return;
		}

		String url = conn.baseUrl() + "/" + this.getEntityName() + "/" + String.valueOf(this.getEntityId());

		T v1 = null;

		try {
			String jsonRequest = this.toJsonString();
			String response = conn.patch(url, jsonRequest);

			v1 = Util.getMapper().readValue(response, tClass);

			// v1.setConnection(this.conn);
			// v1.setClass(this.tClass);

			setFields(v1, this);

		} catch (Throwable c) {
			throw new EntityException(c);
		}

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

		if (!this.hasCRUD()) {
			return null;
		}

		String url = conn.baseUrl() + "/" + this.getEntityName() + "/" + String.valueOf(id);

		T v1 = null;

		try {

			String response = conn.get(url, queryParms);

			v1 = Util.getMapper().readValue(response, tClass);
			v1.setConnection(this.conn);
			v1.setClass(this.tClass);

			if (this.isSubEntity()) {
				v1.setParentID(this.getParentID());
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

		String url = conn.baseUrl() + "/" + this.getEntityName() + "/" + String.valueOf(this.getEntityId());

		try {

			conn.delete(url);

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

		if (!hasList()) {
			return null;
		}

		String url = conn.baseUrl() + "/" + this.getEntityName();

		if (nextURL != null && nextURL.length() > 0) {
			url = nextURL;
		}

		EntityList<T> entities = null;

		try {

			ListResponse response = conn.getList(url, queryParms);

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

		if (!hasList()) {
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

	abstract long getEntityId();

	abstract String getEntityName();

	abstract boolean hasCRUD();

	abstract boolean hasList();

	abstract boolean isSubEntity();

	abstract void setParentID(long parentID);

	abstract long getParentID();

}