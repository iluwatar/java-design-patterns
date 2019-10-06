package com.iluwatar.pipeline;

public class App {
  public static void main(String[] args) {
    Pipeline<String, char[]> filters = new Pipeline<>(new RemoveAlphabetsHandler())
        .addHandler(new RemoveDigitsHandler())
        .addHandler(new ConvertToCharArrayHandler());
  }
}
