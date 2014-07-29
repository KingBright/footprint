package com.kingbright.footprint.location;

public interface FootprintFetcher {
	public void requestFootprintOnce();

	public void requestFootprintRepeatly(long interval);

	public void cancelRepeatTask();
}
