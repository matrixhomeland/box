/*
 */
package com.qyf404.box.core.query;


/**
 * query condition.
 *
 */
public class QueryCondition {
    private String name;

    private Object value;

    private QueryOperator operator;

    public QueryCondition(String name, Object value, QueryOperator operator) {
        this.name = name;
        this.value = value;
        this.operator = operator;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public QueryOperator getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return "QueryCondition:{name:" + name + ",value:" + value + ",operator:" + operator + "}";
    }
}
