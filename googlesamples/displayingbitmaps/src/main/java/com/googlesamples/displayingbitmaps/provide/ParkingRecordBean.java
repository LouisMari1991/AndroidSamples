package com.googlesamples.displayingbitmaps.provide;

import java.util.List;

/**
 * <pre>
 * desc:TO-DO 今日记录实体类
 * author:TanXueMei
 * time:2017/10/29
 * version:1.0
 * </pre>
 */

public class ParkingRecordBean {

  /**
   * ParkingRecordItems : [{"CardSnr":"苏EG08P9","CardType":"1","StartTime":"2017-10-28 13:38:49","EndTime":"2017-10-28 13:55:11","giving":10,"CredenceSnr":"1002_6574d13c6f822259_1509169129596","BackByte":"101035","Money":"10.00","memo":"","Mode":1,"RealMoney":10,"UserID":"s0001","carpicture":"","userMoney":0}]
   * cardlist : null
   * count : 29
   * FLAG : 0
   * MESSAGE :
   * token : null
   */

  private Object cardlist;
  private String                       count;
  private String                       FLAG;
  private String                       MESSAGE;
  private Object                       token;
  private List<ParkingRecordItems> ParkingRecordItems;

  public Object getCardlist() {
    return cardlist;
  }

  public void setCardlist(Object cardlist) {
    this.cardlist = cardlist;
  }

  public String getCount() {
    return count;
  }

  public void setCount(String count) {
    this.count = count;
  }

  public String getFLAG() {
    return FLAG;
  }

  public void setFLAG(String FLAG) {
    this.FLAG = FLAG;
  }

  public String getMESSAGE() {
    return MESSAGE;
  }

  public void setMESSAGE(String MESSAGE) {
    this.MESSAGE = MESSAGE;
  }

  public Object getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public List<ParkingRecordItems> getParkingRecordItems() {
    return ParkingRecordItems;
  }

  public void setParkingRecordItems(List<ParkingRecordItems> ParkingRecordItems) {
    this.ParkingRecordItems = ParkingRecordItems;
  }

  @Override public String toString() {
    return "ParkingRecordBean{"
        + "cardlist="
        + cardlist
        + ", count='"
        + count
        + '\''
        + ", FLAG='"
        + FLAG
        + '\''
        + ", MESSAGE='"
        + MESSAGE
        + '\''
        + ", token='"
        + token
        + '\''
        + ", ParkingRecordItems="
        + ParkingRecordItems
        + '}';
  }
}
