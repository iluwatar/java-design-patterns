package com.iluwatar.remote.facade.DTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * {@link com.iluwatar.remote.facade.DTOs.Customerdto } is a data transfer object.
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
