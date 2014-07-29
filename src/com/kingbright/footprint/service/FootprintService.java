package com.kingbright.footprint.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * A background service helps to collect location info.
 * 
 * @author KingBright
 * 
 */
public class FootprintService extends Service {
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	private void getLocation() {
	}

	private void getLocationName() {

	}

}
