/*
 */
package com.qyf404.box.core.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qyf404.box.core.BoxException;
import com.qyf404.box.core.cmd.Command;
import com.qyf404.box.core.env.Environment;
import com.qyf404.box.core.env.EnvironmentImpl;
import com.qyf404.box.core.session.QuerySession;
import com.qyf404.box.core.svc.CommandService;

/**
 * 查询对象的一个抽象类。
 * @author qyf404@gmail.com
 * @since 2014年12月7日
 */
public abstract class AbstractQuery implements Command<Object> {

  private static final long serialVersionUID = 1L;

  protected transient CommandService commandService;
  protected String orderByClause;
  protected Page page;
  protected boolean isWhereAdded;
  protected boolean count;
  protected boolean uniqueResult;

  protected Map<String, QueryOperator> parameterOperators = new HashMap<String, QueryOperator>();

  /* reuse by copy and paste:
   * (return type can't be changed)
  public ConcreteQuery page(int firstResult, int maxResults) {
    this.page = new Page(firstResult, maxResults);
    return this;
  }
  */

  public List<?> untypedList() {
    if (commandService != null) {
      return (List<?>) commandService.execute(this);
    }
    QuerySession querySession = EnvironmentImpl.getFromCurrent(QuerySession.class);
    try {
      return (List<?>) execute(querySession);
    } catch(Exception ex) {
      throw new BoxException(ex);
    }
  }

  protected Object untypedUniqueResult() {
    uniqueResult = true;

    if (commandService != null) {
      return commandService.execute(this);
    }
    QuerySession querySession = EnvironmentImpl.getFromCurrent(QuerySession.class);
    try {
      return execute(querySession);
    } catch(Exception ex) {
      throw new BoxException(ex);
    }
  }

  public Object execute(Environment env) throws Exception {
    QuerySession querySession = env.get(QuerySession.class);
    try {
      return execute(querySession);
    } finally {
      resetQuery(); // reset the query member fields so the query can be reused.
    }
  }

  /**
   * Returns the count of the query.
   * Query types that want to use this count method should
   *   - add the operation signature to their interface
   *   - use the 'count' variable in their hql() method.
   */
  public long count() {
    count = true;

    // Page and count are mutually exclusive because paging is applied after query is formed
    if (page != null) {
      throw new BoxException("page(firstResult, maxResult) and count() cannot be used together");
    }

    return (Long) untypedUniqueResult();
  }

  /**
   * Resets the query so it can be reused after an invocation.
   */
  protected void resetQuery() {
    isWhereAdded = false;
    count = false;
    uniqueResult = false;
  }

  protected void appendWhereClause(String whereClause, StringBuilder hql) {
    if (isWhereAdded) {
      hql.append("  and ");
    }
    else {
      isWhereAdded = true;
      hql.append("where ");
    }
    hql.append(whereClause);
  }

  protected void appendOrderByClause(StringBuilder hql) {
    if (orderByClause != null) {
      hql.append("order by ");
      hql.append(orderByClause);
    }
  }

  protected void addOrderByClause(String clause) {
    if (orderByClause == null) {
      orderByClause = clause;
    }
    else {
      orderByClause += ", " + clause;
    }
  }

  protected QueryOperator getOperator(String param) {
    if (parameterOperators.containsKey(param)) {
      return parameterOperators.get(param);
    }
    return QueryOperator.EQUALS;
  }

  protected void appendWhereClauseWithOperator(String where, String namedParam, StringBuilder hql) {
    QueryOperator operator = getOperator(namedParam);
    switch (operator) {
    case IN:
    case NOT_IN:
      appendWhereClause(where + " " + operator + " (:" + namedParam + ") ", hql);
      break;
    default:
      appendWhereClause(where + " " + operator + " :" + namedParam + " ", hql);
    }
  }

  public void setCommandService(CommandService commandService) {
    this.commandService = commandService;
  }

  protected QueryOperator findOperator(String name) {
    QueryOperator operator = parameterOperators.get(name);
    if (operator == null) {
      return QueryOperator.EQUALS;
    } else {
      return operator;
    }
  }

  protected abstract Object execute(QuerySession querySession) throws Exception;
}
