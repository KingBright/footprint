package com.kingbright.footprint.location;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.kingbright.footprint.model.Footprint;

public class BaiduLocationUtil {
	public static final String TAG = "BaiduLocationUtil";
	public static final int GPS_RESULT = 61;
	public static final int CACHED_RESULT = 65;
	public static final int NETWORK_RESULT = 161;
	public static final int OFFLINE_RESULT = 66;
	public static final int NETWORK_OFFLINE_RESULT = 68;

	public static final int INVALID_ERROR = 62;
	public static final int NETWORK_ERROR = 63;
	public static final int OFFLINE_ERROR_USE_LAST = 67;
	public static final int SERVER_ERROR_START = 162;
	public static final int SERVER_ERROR_END = 167;
	public static final int KEY_ERROR_START = 501;
	public static final int KEY_ERROR_END = 700;

	/**
	 * Return null if error occurs.
	 * 
	 * @param location
	 * @return
	 */
	public static Footprint convertToFootprint(BDLocation location) {
		if (location == null) {
			return null;
		}

		Footprint footprint = new Footprint();

		int type = location.getLocType();
		Log.i(TAG, "type is " + type);
		if (type == INVALID_ERROR || type == NETWORK_ERROR
				|| type == OFFLINE_ERROR_USE_LAST
				|| (type >= SERVER_ERROR_START && type <= SERVER_ERROR_END)
				|| (type >= KEY_ERROR_START && type <= KEY_ERROR_END)) {
			return null;
		} else {
			if (type == GPS_RESULT) {
				footprint.type = FootprintType.GPS;
			} else if (type == NETWORK_RESULT) {
				footprint.type = FootprintType.NETWORK;
			} else if (type == NETWORK_OFFLINE_RESULT
					|| type == OFFLINE_ERROR_USE_LAST) {
				footprint.type = FootprintType.OFFLINE;
			} else if (type == CACHED_RESULT) {
				footprint.type = FootprintType.CACHE;
			}

			footprint.lat = location.getLatitude();
			footprint.lon = location.getLongitude();
			footprint.alt = location.getAltitude();
			footprint.addr = location.getAddrStr();
			footprint.time = location.getTime();
		}
		return footprint;
	}
}
