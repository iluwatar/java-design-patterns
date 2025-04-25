package com.iluwatar.daofactory;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by: IntelliJ IDEA
 * User      : dthanh
 * Date      : 16/04/2025
 * Time      : 23:22
 * Filename  : Customer
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer<T> implements Serializable {
  private T id;
  private String name;
}
