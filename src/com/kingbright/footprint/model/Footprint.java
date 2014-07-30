package com.kingbright.footprint.model;

import com.google.gson.Gson;
import com.kingbright.footprint.location.FootprintType;

/**
 * Info of your step. One step, one footprint.
 * 
 * @author KingBright
 * 
 */
public class Footprint {
	public double lat;
	public double lon;
	public double alt;
	public String addr;
	public String time;
	public FootprintType type;

	/**
	 * JSON format.
	 */
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
