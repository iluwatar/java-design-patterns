package com.callusage.domain;

public class UsageDetail {

	public UsageDetail() { }

	private String userId;

	private long duration;

	private long data;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getData() {
		return data;
	}

	public void setData(long data) {
		this.data = data;
	}

}
