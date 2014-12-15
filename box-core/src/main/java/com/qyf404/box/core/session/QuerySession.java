/*
 */
package com.qyf404.box.core.session;

import java.util.Map;

import com.qyf404.box.core.query.QueryProperty;

/**
 * All of query class could use this interface to fetch data from database.
 *
 */
public interface QuerySession {
  Object query(QueryProperty queryProperty);

  Object execute(String queryString, String queryType, Map<String, Object> params, boolean isUpdate, boolean isUnique);
}
