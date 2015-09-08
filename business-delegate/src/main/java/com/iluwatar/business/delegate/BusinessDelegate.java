package com.iluwatar.business.delegate;

/**
 * 
 * BusinessDelegate separates the presentation and business tiers
 *
 */
public class BusinessDelegate {
	
	private BusinessLookup lookupService = new BusinessLookup();
	private BusinessService businessService;
	private ServiceType serviceType;

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public void doTask() {
		businessService = lookupService.getBusinessService(serviceType);
		businessService.doProcessing();
	}
}
