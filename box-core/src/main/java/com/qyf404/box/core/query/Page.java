/*
 */
package com.qyf404.box.core.query;

import java.io.Serializable;


/**
 * 对翻页数据的封装
 * @author qyf404@gmail.com
 * @since 2014年12月7日
 */
public class Page implements Serializable {

  private static final long serialVersionUID = 1L;

  public int firstResult;
  public int maxResults;
  
  public Page(int firstResult, int maxResults) {
    this.firstResult = firstResult;
    this.maxResults = maxResults;
  }
}
