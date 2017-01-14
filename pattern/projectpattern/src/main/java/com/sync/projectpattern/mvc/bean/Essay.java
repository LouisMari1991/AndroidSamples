package com.sync.projectpattern.mvc.bean;

/**
 * Author：Administrator on 2017/1/14 0014 11:56
 * Contact：289168296@qq.com
 */
public class Essay {
  private String title;
  private String url;
  private String page;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  @Override public String toString() {
    return "Essay{" +
        "title='" + title + '\'' +
        ", url='" + url + '\'' +
        ", page='" + page + '\'' +
        '}';
  }
}
