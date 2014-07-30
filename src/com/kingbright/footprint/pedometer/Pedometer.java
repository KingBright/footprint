package com.kingbright.footprint.pedometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

public class Pedometer implements StepListener {
	public static final String TAG = "Pedometer";

	private long mSteps = 0L;

	private OnPedometerUpdateListener mOnPedometerUpdateListener;

	private SensorManager mSensorManager;

	private StepDetector mStepDetector;

	public Pedometer(Context context) {
		mSensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		mStepDetector = new StepDetector();
		mStepDetector.addStepListener(this);
	}

	public void start() {
		Log.i(TAG, "Start step detector");
		Sensor sensor = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(mStepDetector, sensor,
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	public void stop() {
		Log.i(TAG, "Stop step detector");
		mSensorManager.unregisterListener(mStepDetector);
	}

	public void setListener(OnPedometerUpdateListener listener) {
		mOnPedometerUpdateListener = listener;
	}

	@Override
	public void onStep() {
		mSteps++;
		if (mOnPedometerUpdateListener != null) {
			mOnPedometerUpdateListener.onUpdate(mSteps);
		}
	}

	public interface OnPedometerUpdateListener {
		public void onUpdate(long mSteps);
	}
}
