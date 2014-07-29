package com.kingbright.footprint.location;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.kingbright.footprint.model.Footprint;

public class BaiduFootprintProxy extends FootprintProxy {
	public static final String TAG = "BaiduFootprintProxy";
	public LocationClient mLocationClient = null;
	public BDLocationListener mListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			Log.i(TAG, "onReceiveLocation");
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
	public void requestFootprint() {
		mLocationClient.requestLocation();
	}

	@Override
	public void release() {
		registerCallback(null);
		mLocationClient.stop();
		mLocationClient = null;
	}

}
