package com.callusage.domain;

public class MessageHeader {

    private String dataLocation;
    private String dataFileName;
    private String operataionName;
    
    public String getDataLocation() {
        return dataLocation;
    }

    public void setDataLocation(String dataLocation) {
        this.dataLocation = dataLocation;
    }

    public String getDataFileName() {
		return dataFileName;
	}

	public void setDataFileName(String dataFileName) {
		this.dataFileName = dataFileName;
	}
	
    public String getOperataionName() {
        return operataionName;
    }

    public void setOperataionName(String operataionName) {
        this.operataionName = operataionName;
    }

	@Override
	public String toString() {
		return "MessageHeader [dataLocation=" + dataLocation + ", dataFileName=" + dataFileName + ", operataionName="
				+ operataionName + "]";
	}

    
}
