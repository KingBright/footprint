package com.kingbright.footprint.pedometer;

import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

public class Pedometer implements StepListener {
	public static final String TAG = "Pedometer";

	private AtomicInteger mSteps = new AtomicInteger(0);

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

	public void reset() {
		mSteps.set(0);
	}

	public int getCurrentSteps() {
		return mSteps.get();
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
		int currentSteps = mSteps.addAndGet(1);
		if (mOnPedometerUpdateListener != null) {
			mOnPedometerUpdateListener.onUpdate(currentSteps);
		}
	}

	public interface OnPedometerUpdateListener {
		public void onUpdate(int steps);
	}
}
