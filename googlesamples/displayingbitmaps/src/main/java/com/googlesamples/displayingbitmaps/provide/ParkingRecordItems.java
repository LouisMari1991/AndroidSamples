package com.googlesamples.displayingbitmaps.provide;

/**
 * <pre>
 * desc:TO-DO
 * author:TanXueMei
 * time:2017/10/30
 * version:1.0
 * </pre>
 */

public class ParkingRecordItems {
  /**
   * CardSnr : 苏EG08P9
   * CardType : 1
   * StartTime : 2017-10-28 13:38:49
   * EndTime : 2017-10-28 13:55:11
   * giving : 10.0
   * CredenceSnr : 1002_6574d13c6f822259_1509169129596
   * BackByte : 101035
   * Money : 10.00
   * memo :
   * Mode : 1
   * RealMoney : 10.0
   * ReturnMoney : 0.0
   * UserID : s0001
   * userMoney : 0
   */

  private String CardSnr;
  private String CardType;
  private String StartTime;
  private String EndTime;
  private double giving;
  private String CredenceSnr;
  private String BackByte;
  private String Money;
  private String memo;
  private int    Mode;
  private double RealMoney;
  private double ReturnMoney;
  private String UserID;
  private int    userMoney;

  public String getCardSnr() {
    return CardSnr;
  }

  public void setCardSnr(String CardSnr) {
    this.CardSnr = CardSnr;
  }

  public String getCardType() {
    return CardType;
  }

  public void setCardType(String CardType) {
    this.CardType = CardType;
  }

  public String getStartTime() {
    return StartTime;
  }

  public void setStartTime(String StartTime) {
    this.StartTime = StartTime;
  }

  public String getEndTime() {
    return EndTime;
  }

  public void setEndTime(String EndTime) {
    this.EndTime = EndTime;
  }

  public double getGiving() {
    return giving;
  }

  public void setGiving(double giving) {
    this.giving = giving;
  }

  public String getCredenceSnr() {
    return CredenceSnr;
  }

  public void setCredenceSnr(String CredenceSnr) {
    this.CredenceSnr = CredenceSnr;
  }

  public String getBackByte() {
    return BackByte;
  }

  public void setBackByte(String BackByte) {
    this.BackByte = BackByte;
  }

  public String getMoney() {
    return Money;
  }

  public void setMoney(String Money) {
    this.Money = Money;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public int getMode() {
    return Mode;
  }

  public void setMode(int Mode) {
    this.Mode = Mode;
  }

  public double getRealMoney() {
    return RealMoney;
  }

  public void setRealMoney(double RealMoney) {
    this.RealMoney = RealMoney;
  }

  public double getReturnMoney() {
    return ReturnMoney;
  }

  public void setReturnMoney(double ReturnMoney) {
    this.ReturnMoney = ReturnMoney;
  }

  public String getUserID() {
    return UserID;
  }

  public void setUserID(String UserID) {
    this.UserID = UserID;
  }

  public int getUserMoney() {
    return userMoney;
  }

  public void setUserMoney(int userMoney) {
    this.userMoney = userMoney;
  }

  @Override public String toString() {
    return "ParkingRecordItems{"
        + "CardSnr='"
        + CardSnr
        + '\''
        + ", CardType='"
        + CardType
        + '\''
        + ", StartTime='"
        + StartTime
        + '\''
        + ", EndTime='"
        + EndTime
        + '\''
        + ", giving="
        + giving
        + ", CredenceSnr='"
        + CredenceSnr
        + '\''
        + ", BackByte='"
        + BackByte
        + '\''
        + ", Money='"
        + Money
        + '\''
        + ", memo='"
        + memo
        + '\''
        + ", Mode="
        + Mode
        + ", RealMoney="
        + RealMoney
        + ", ReturnMoney="
        + ReturnMoney
        + ", UserID='"
        + UserID
        + '\''
        + ", userMoney="
        + userMoney
        + '}';
  }
}