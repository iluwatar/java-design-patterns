package com.iluwatar.layers;

import java.util.List;

public class CakeInfo {

	public final CakeToppingInfo cakeToppingInfo;
	public final List<CakeLayerInfo> cakeLayerInfos;
	
	public CakeInfo(CakeToppingInfo cakeToppingInfo, List<CakeLayerInfo> cakeLayerInfos) {
		this.cakeToppingInfo = cakeToppingInfo;
		this.cakeLayerInfos = cakeLayerInfos;
	}
}
