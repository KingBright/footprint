package com.kingbright.footprint.location;

import android.content.Context;

public class FootprintProxyFactory {
	private static FootprintProxyFactory sFactory;

	private Context mContext;

	/**
	 * Use the application context to get the single instance.
	 * 
	 * @param context
	 * @return
	 */
	public synchronized static FootprintProxyFactory getInstance(Context context) {
		if (sFactory == null) {
			sFactory = new FootprintProxyFactory(context);
		}
		return sFactory;
	}

	private FootprintProxyFactory(Context context) {
		mContext = context;
	}

	public FootprintProxy getProxy() {
		return new BaiduFootprintProxy(mContext);
	}

	public void release() {
		sFactory = null;
	}
}
