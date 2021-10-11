package com.iluwatar.domain;

public class MessageReference {
   
  private String dataLocation;
  private String dataFileName;
  
  public MessageReference() {
  }

  public MessageReference(String dataLocation, String dataFileName) {
        this.dataLocation = dataLocation;
        this.dataFileName = dataFileName;
  }

  public String getDataLocation() {
    return dataLocation;
  }
  public void setDataLocation(String dataLocation) {
    this.dataLocation = dataLocation;
  }
  public String getDataFileName() {
    return dataFileName;
  }
  public void setDataFileName(String dataFileName) {
    this.dataFileName = dataFileName;
  }
}
