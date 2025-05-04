package com.iluwatar.daofactory;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * A customer generic POJO that represents the data that can be stored in any supported data source.
 * This class is designed t work with various ID types (e.g., Long, String, or ObjectId) through
 * generic, making it adaptable to different persistence system.
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
