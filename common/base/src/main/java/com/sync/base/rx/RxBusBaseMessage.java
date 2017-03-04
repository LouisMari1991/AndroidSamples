package com.sync.base.rx;

/**
 * Created by YH on 2017-01-17.
 */

public class RxBusBaseMessage {

  private int code;
  private Object object;

  public RxBusBaseMessage() {
  }

  public RxBusBaseMessage(int code, Object object) {
    this.code = code;
    this.object = object;
  }

  public int getCode() {
    return code;
  }

  public Object getObject() {
    return object;
  }
}
