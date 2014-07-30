package com.kingbright.footprint.location;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.google.gson.Gson;
import com.kingbright.footprint.model.Footprint;

public class BaiduFootprintProxy extends FootprintProxy {
	public static final String TAG = "BaiduFootprintProxy";
	public LocationClient mLocationClient = null;
	public BDLocationListener mListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			Gson gson = new Gson();
			Log.i(TAG, "onReceiveLocation : " + gson.toJson(location));
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
		LocationClientOption options = new LocationClientOption();
		options.setIsNeedAddress(true);
		options.setNeedDeviceDirect(true);
		options.setLocationMode(LocationMode.Hight_Accuracy);
		mLocationClient.setLocOption(options);
		mLocationClient.start();
	}

	@Override
	public void requestFootprint() {
		Log.i(TAG, "requestLocation");
		mLocationClient.requestLocation();
	}

	@Override
	public void release() {
		registerCallback(null);
		mLocationClient.stop();
		mLocationClient = null;
	}

}
