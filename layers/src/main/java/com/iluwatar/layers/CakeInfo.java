package com.iluwatar.layers;

import java.util.List;
import java.util.Optional;

/**
 * 
 * DTO for cakes
 *
 */
public class CakeInfo {

  public final Optional<Long> id;
  public final CakeToppingInfo cakeToppingInfo;
  public final List<CakeLayerInfo> cakeLayerInfos;

  public CakeInfo(Long id, CakeToppingInfo cakeToppingInfo, List<CakeLayerInfo> cakeLayerInfos) {
    this.id = Optional.of(id);
    this.cakeToppingInfo = cakeToppingInfo;
    this.cakeLayerInfos = cakeLayerInfos;
  }

  public CakeInfo(CakeToppingInfo cakeToppingInfo, List<CakeLayerInfo> cakeLayerInfos) {
    this.id = Optional.empty();
    this.cakeToppingInfo = cakeToppingInfo;
    this.cakeLayerInfos = cakeLayerInfos;
  }

  public int calculateTotalCalories() {
    int total = cakeToppingInfo != null ? cakeToppingInfo.calories : 0;
    total += cakeLayerInfos.stream().mapToInt(c -> c.calories).sum();
    return total;
  }

  @Override
  public String toString() {
    return String.format("CakeInfo id=%d topping=%s layers=%s totalCalories=%d", id.orElse(-1L),
        cakeToppingInfo, cakeLayerInfos, calculateTotalCalories());
  }
}
