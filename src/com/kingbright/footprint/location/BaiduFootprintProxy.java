package com.kingbright.footprint.location;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.kingbright.footprint.model.Footprint;

public class BaiduFootprintProxy extends FootprintProxy {
	public LocationClient mLocationClient = null;
	public BDLocationListener mListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			Footprint footprint = BaiduLocationUtil
					.convertToFootprint(location);
			FootprintCallback callback = getCallback();
			if (callback != null) {
				callback.onGetFootprint(footprint);
			}
		}
	};

	public BaiduFootprintProxy(Context context) {
		mLocationClient = new LocationClient(context);
		mLocationClient.registerLocationListener(mListener);
	}

	@Override
	public void requestFootprintOnce() {
		mLocationClient.requestLocation();
	}

}
