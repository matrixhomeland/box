
package com.qyf404.box.core.cmd;


public abstract class QueryCommand<T> extends AbstractCommand<T>{

  private static final long serialVersionUID = 1L;
  
  protected int firstResult;
  protected int maxResults;

  public QueryCommand(int firstResult, int maxResults) {
    this.firstResult = firstResult;
    this.maxResults = maxResults;
  }
}
