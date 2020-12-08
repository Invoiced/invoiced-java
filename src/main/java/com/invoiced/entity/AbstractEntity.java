package com.invoiced.entity;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.invoiced.exception.EntityException;
import com.invoiced.util.ListResponse;
import com.invoiced.util.Util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public abstract class AbstractEntity<T extends AbstractEntity> {

  protected Class<T> tClass;
  protected String entityName;
  private Connection conn;
  private boolean entityCreated;
  private String endpointBase = "";

  public AbstractEntity(Connection conn, Class<T> tClass) {
    this.conn = conn;
    this.tClass = tClass;
    this.entityCreated = false;
  }

  public AbstractEntity(Class<T> tClass) {
    this.tClass = tClass;
    this.entityCreated = false;
  }

  protected static void setFields(Object from, Object to) throws EntityException {
    Field[] fields = from.getClass().getFields();

    for (Field field : fields) {
      try {
        Field fieldFrom = from.getClass().getField(field.getName());

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
        to.getClass().getField(field.getName()).set(to, value);

      } catch (Throwable c) {
        throw new EntityException(c);
      }
    }
  }

  protected String getEndpoint(boolean includeId) {
    String url = this.getEndpointBase() + this.entityName;

    if (this.getEntityId() != null && includeId) {
      url += "/" + this.getEntityId();
    }

    return url;
  }

  protected Connection getConnection() {
    return this.conn;
  }

  protected void setConnection(Connection conn) {
    this.conn = conn;
  }

  protected void setClass(Class<T> tClass) {
    this.tClass = tClass;
  }

  protected String getEndpointBase() {
    return this.endpointBase;
  }

  protected void setEndpointBase(String base) {
    this.endpointBase = base;
  }

  public void create() throws EntityException {

    if (this.entityCreated) {
      throw new EntityException(new Throwable("Object has already been created."));
    }

    if (!this.hasCRUD()) {
      throw new EntityException(new Throwable("Create operation not available on object."));
    }

    String url = this.getEndpoint(false);

    try {
      String jsonRequest = this.toJsonString("create");
      String response = this.conn.post(url, null, jsonRequest);

      T object = Util.getMapper().readValue(response, this.tClass);
      object.setConnection(this.conn);
      object.setClass(this.tClass);

      setFields(object, this);
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
    try {
      return Util.getMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(this);
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }

  private String toJsonString(String operation) throws EntityException {

    String[] exclusions = null;

    try {

      if (operation == "create") exclusions = this.getCreateExclusions();
      else if (operation == "update") exclusions = this.getSaveExclusions();

      return Util.getFilteredMapper(exclusions)
              .writeValueAsString(this);

    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }

  public void save() throws EntityException {
    if (!this.hasCRUD()) {
      throw new EntityException(new Throwable("Save operation not available on object."));
    }

    String url = this.getEndpoint(true);

    try {
      String jsonRequest = this.toJsonString("update");
      String response = this.conn.patch(url, jsonRequest);

      T object = Util.getMapper().readValue(response, this.tClass);
      setFields(object, this);
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }

  public T retrieve() throws EntityException {
    return this.retrieve("", null);
  }

  public T retrieve(long id) throws EntityException {
    return this.retrieve(String.valueOf(id), null);
  }

  public T retrieve(String id) throws EntityException {
    return this.retrieve(id, null);
  }

  public T retrieve(long id, HashMap<String, Object> queryParams) throws EntityException {
    return this.retrieve(String.valueOf(id), queryParams);
  }

  public T retrieve(String id, HashMap<String, Object> queryParams) throws EntityException {
    String url = this.getEndpoint(false);
    if (id.length() > 0) url += "/" + id;

    try {
      String response = this.conn.get(url, queryParams);

      T object = Util.getMapper().readValue(response, this.tClass);
      object.setConnection(this.conn);
      object.setClass(this.tClass);
      object.setEndpointBase(this.endpointBase);

      return object;
    } catch (Throwable c) {

      throw new EntityException(c);
    }
  }

  public void delete() throws EntityException {

    if (!this.hasCRUD()) {
      throw new EntityException(new Throwable("Delete operation not available on object."));
    }

    String url = this.getEndpoint(true);

    try {

      this.conn.delete(url);

    } catch (Throwable c) {

      throw new EntityException(c);
    }
  }

  public void delete(boolean includeId) throws EntityException {

    if (!this.hasCRUD()) {
      throw new EntityException(new Throwable("Delete operation not available on object."));
    }

    String url = this.getEndpoint(includeId);

    try {

      this.conn.delete(url);

    } catch (Throwable c) {

      throw new EntityException(c);
    }
  }

  public void deleteWithResponse() throws EntityException {
    if (!this.hasCRUD()) {
      throw new EntityException(new Throwable("Delete operation not available on object."));
    }

    String url = this.getEndpoint(true);

    try {
      String response = this.conn.deleteWithResponse(url);

      T object = Util.getMapper().readValue(response, this.tClass);
      object.setConnection(this.conn);
      object.setClass(this.tClass);
      object.setEndpointBase(this.endpointBase);

      setFields(object, this);
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

  public EntityList<T> list(String nextURL, HashMap<String, Object> queryParams)
      throws EntityException {

    if (!this.hasList()) {
      throw new EntityException(new Throwable("List operation not available on object."));
    }

    String url = this.getEndpoint(false);

    if (nextURL != null && nextURL.length() > 0) {
      url = nextURL;
    }

    EntityList<T> entities = null;

    try {

      ListResponse response = this.conn.getList(url, queryParams);

      JavaType collectionType =
          Util.getMapper().getTypeFactory().constructCollectionType(EntityList.class, this.tClass);

      entities = Util.getMapper().readValue(response.getResult(), collectionType);

      entities.setLinkURLs(response.getLinks());
      entities.setTotalCount(response.getTotalCount());

      for (T entity : entities) {

        entity.setConnection(this.conn);
        entity.setClass(this.tClass);
      }

    } catch (Throwable c) {
      throw new EntityException(c);
    }

    return entities;
  }

  public EntityList<T> listAll(HashMap<String, Object> queryParams) throws EntityException {
    EntityList<T> entities = null;
    EntityList<T> tmp = null;

    if (!this.hasList()) {
      throw new EntityException(new Throwable("List operation not available on object."));
    }

    String url = null;

    try {

      do {

        tmp = this.list(url, queryParams);
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

  protected boolean hasCRUD() {
    return true;
  }

  protected boolean hasList() {
    return true;
  }

  abstract String getEntityId();

  abstract String[] getCreateExclusions();

  abstract String[] getSaveExclusions();
}
