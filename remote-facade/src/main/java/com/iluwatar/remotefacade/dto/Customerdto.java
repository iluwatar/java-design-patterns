package com.iluwatar.remotefacade.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * {@link Customerdto } is a data transfer object.
 *
 * <p>Dto will not have any business logic in it.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class Customerdto {
  public  String name;
  public  String phone;
  public  String address;

}
