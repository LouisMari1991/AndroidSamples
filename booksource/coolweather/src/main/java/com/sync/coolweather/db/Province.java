package com.sync.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Author：Administrator on 2017/2/16 0016 23:39
 * Contact：289168296@qq.com
 */
public class Province extends DataSupport {
  
  private int id;
  private String provinceName;
  private int provinceCode;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getProvinceName() {
    return provinceName;
  }

  public void setProvinceName(String provinceName) {
    this.provinceName = provinceName;
  }

  public int getProvinceCode() {
    return provinceCode;
  }

  public void setProvinceCode(int provinceCode) {
    this.provinceCode = provinceCode;
  }
}
