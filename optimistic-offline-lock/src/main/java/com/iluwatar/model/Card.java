package com.iluwatar.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bank card entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {

  /**
   * Primary key.
   */
  private long id;

  /**
   * Foreign key points to card's owner.
   */
  private long personId;

  /**
   * Sum of money.
   */
  private float sum;

  /**
   * Current version of object.
   */
  private int version;
}
