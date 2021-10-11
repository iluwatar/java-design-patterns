package com.iluwatar.domain;

import java.util.List;

public class MessageBody<T> {

  private List<T> data;
  
  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }
  
}
