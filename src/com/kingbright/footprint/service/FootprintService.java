package com.kingbright.footprint.service;

import com.kingbright.footprint.constants.Constants;
import com.kingbright.footprint.location.FootprintCallback;
import com.kingbright.footprint.location.FootprintProxy;
import com.kingbright.footprint.location.FootprintProxyFactory;
import com.kingbright.footprint.model.Footprint;
import com.kingbright.footprint.pedometer.Pedometer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

/**
 * A background service helps to collect location info.
 * 
 * @author KingBright
 * 
 */
public class FootprintService extends Service {
	public static final String TAG = "FootprintService";

	public static final String ACTION_GET_LOCATION = Constants.PREFIX_PACKAGE_NAME
			+ "ACTION_GET_LOCATION";
	public static final String ACTION_START_TRACK = Constants.PREFIX_PACKAGE_NAME
			+ "ACTION_START_TRACK";
	public static final String ACTION_STOP_TRACK = Constants.PREFIX_PACKAGE_NAME
			+ "ACTION_STOP_TRACK";

	private static final int LOCATION_REQUEST_CODE = 11;
	private static final long LOCATION_REQEUST_INTERVAL = 15 * 1000L;

	private FootprintProxy mFootprintProxy;
	private FootprintCallback mFootprintCallback = new FootprintCallback() {

		@Override
		public void onGetFootprint(Footprint footprint) {
			Log.i("TAG", "footprint : " + footprint.toString());
		}
	};

	private AlarmManager mAlarmManager;
	private Pedometer mPedometer;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mFootprintProxy = FootprintProxyFactory.getInstance(
				getApplicationContext()).getProxy();
		mFootprintProxy.registerCallback(mFootprintCallback);

		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		mPedometer = new Pedometer(getApplicationContext());
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent == null) {
			return super.onStartCommand(intent, flags, startId);
		}
		String action = intent.getAction();
		if (!TextUtils.isEmpty(action)) {
			if (action.equals(ACTION_GET_LOCATION)) {
				getLocation();
			} else if (action.equals(ACTION_START_TRACK)) {
				registerRepeatLocationRequest(LOCATION_REQEUST_INTERVAL);
				mPedometer.start();
			} else if (action.equals(ACTION_STOP_TRACK)) {
				Log.i(TAG, "cancel repeat task");
				mAlarmManager.cancel(getLocationRequestOpertaion());
				Log.i(TAG, "stop self");
				stopSelf();
				mPedometer.stop();
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private void registerRepeatLocationRequest(long interval) {
		Log.i(TAG, "register for repeat task");
		mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				SystemClock.elapsedRealtime(), interval,
				getLocationRequestOpertaion());
	}

	private PendingIntent getLocationRequestOpertaion() {
		return PendingIntent.getService(getApplicationContext(),
				LOCATION_REQUEST_CODE, new Intent(ACTION_GET_LOCATION),
				PendingIntent.FLAG_UPDATE_CURRENT);
	}

	private void getLocation() {
		Log.i(TAG, "request for footprint");
		mFootprintProxy.requestFootprint();
	}

	private void getLocationName() {

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		FootprintProxyFactory.getInstance(getApplicationContext()).release();
		mFootprintProxy.release();
	}

}
