/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.qyf404.box.core.query;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * query property.
 *
 * @author Huisheng Xu
 */
public class QueryProperty {

    public static final int RESULT_TYPE_LIST = 0;

    public static final int RESULT_TYPE_COUNT = 1;

    public static final int RESULT_TYPE_UNIQUE = 2;

    public static final int RESULT_TYPE_PAGE = 3;

    public static final int RESULT_TYPE_PAGE_COUNT = 4;

    public static final int RESULT_TYPE_PAGE_UNIQUE = 5;

    private int resultType = RESULT_TYPE_LIST;

    private int firstResult;

    private int maxResults;

    private Map<String, QueryCondition> conditions = new LinkedHashMap<String, QueryCondition>();

    private Map<String, String> orders = new LinkedHashMap<String, String>();

    private String key;

    public int getResultType() {
        return resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }

    // ~ ======================================================================
    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    // ~ ======================================================================
    public Map<String, QueryCondition> getConditions() {
        return conditions;
    }

    public void addCondition(String name, Object value, QueryOperator operator) {
        conditions.put(name, new QueryCondition(name, value, operator));
    }

    public boolean hasCondition(String name) {
        return conditions.containsKey(name);
    }

    public QueryCondition getCondition(String name) {
        return conditions.get(name);
    }

    public void removeCondition(String name) {
        conditions.remove(name);
    }

    // ~ ======================================================================
    public Map<String, String> getOrders() {
        return orders;
    }

    public void addOrder(String order, String dir) {
        orders.put(order, dir);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
