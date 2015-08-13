package com.iluwatar.layers;

import java.util.List;
import java.util.Optional;

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
	
	@Override
	public String toString() {
		return String.format("CakeInfo id=%d topping=%s layers=%s", id.get(), cakeToppingInfo, cakeLayerInfos);
	}
}
