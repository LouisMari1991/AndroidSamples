package com.sync.projectpattern.mvc.model;

/**
 * Author：Administrator on 2017/1/14 0014 12:04
 * Contact：289168296@qq.com
 */
public class MainModel {

  private MainImpl mMain;

  public interface MainImpl {
    void success(String text);
  }

  public void load(MainImpl main) {
    this.mMain = main;
    String text = "MVC模式在Android中的应用，获取到的Model中处理的数据";
    main.success(text);
  }
}
