package com.iluwatar.domain;

public class MessageHeader {
  
  private String id;
  private String subject;
  private String topic;
  private String eventType;
  private String eventTime;
  private Object data;
  private String dataVersion;

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getSubject() {
    return subject;
  }
  public void setSubject(String subject) {
    this.subject = subject;
  }
  public String getTopic() {
    return topic;
  }
  public void setTopic(String topic) {
    this.topic = topic;
  }
  public String getEventType() {
    return eventType;
  }
  public void setEventType(String eventType) {
    this.eventType = eventType;
  }
  public String getEventTime() {
    return eventTime;
  }
  public void setEventTime(String eventTime) {
    this.eventTime = eventTime;
  }
  public Object getData() {
    return data;
  }
  public void setData(Object data) {
    this.data = data;
  } 
  public String getDataVersion() {
    return dataVersion;
  }
  public void setDataVersion(String dataVersion) {
    this.dataVersion = dataVersion;
  }
}