package com.iluwatar.context.object;

import lombok.Getter;

@Getter
public class LayerC {

    public ServiceContext context;

    public LayerC(LayerB layerB) {
        this.context = layerB.getContext();
    }

    public void addSearchInfo(String searchService) {
        context.setSearchService(searchService);
    }
}
