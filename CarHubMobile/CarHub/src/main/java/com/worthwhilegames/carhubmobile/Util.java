package com.worthwhilegames.carhubmobile;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author jamiekujawa
 */
public class Util {

	/**
	 * A boolean to represent if the current build is a debug build
	 */
	public static final boolean isDebugBuild = true;

	/**
	 * Utility method for getting the SharedPreferences instance for the app
	 * 
	 * @param ctx
	 * @return
	 */
	public static SharedPreferences getSharedPrefs(Context ctx) {
		return ctx.getSharedPreferences("Preferences", 0);
	}
}