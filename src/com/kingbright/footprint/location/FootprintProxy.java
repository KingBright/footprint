package com.kingbright.footprint.location;

public abstract class FootprintProxy implements FootprintFetcher {
	private FootprintCallback mFootprintCallback;

	public FootprintCallback getCallback() {
		return mFootprintCallback;
	}

	public void registerCallback(FootprintCallback callback) {
		mFootprintCallback = callback;
	}
}
