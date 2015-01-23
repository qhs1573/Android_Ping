package com.ygc.ping.activity;


import android.util.Log;

/**日志管理
 * [统一管理日志，包括各种级别的日志]
 */
public class PrintLog {
	private static final String TAG = "Command";
	//打印日志的开关
	private static boolean LOG_VERBOSE = true;
	private static boolean LOG_DEBUG = true;
	private static boolean LOG_INFO = true;
	private static boolean LOG_WARN = true;
	private static boolean LOG_ERROR = true;
	
    /**
     * 打印verbose级别的日志
     * 
     * @param tag 模块打印标记
     * @param msg 日志内容
     */
	public static void v(String tag, String msg) {
		if (LOG_VERBOSE) {
			Log.v(TAG, "[" + tag + "]" + msg);
		}
	}

    /**
     * 打印verbose级别的日志
     * 
     * @param tag 模块打印标记
     * @param msg 日志内容
     * @param tr 打印异常到日志
     */
	public static void v(String tag, String msg, Throwable tr) {
		if (LOG_VERBOSE) {
			Log.v(TAG, "[" + tag + "] " + msg, tr);
		}
	}
	
    /**
     * 打印debug级别的日志
     * 
     * @param tag 模块打印标记
     * @param msg 日志内容
     */
	public static void d(String tag, String msg) {
		if (LOG_DEBUG) {
			Log.d(TAG, "[" + tag + "]" + msg);
		}
	}

    /**
     * 打印debug级别的日志
     * 
     * @param tag 模块打印标记
     * @param msg 日志内容
     * @param tr 打印异常到日志
     */
	public static void d(String tag, String msg, Throwable tr) {
		if (LOG_DEBUG) {
			Log.d(TAG, "[" + tag + "]" + msg, tr);
		}
	}
    

    /**
     * 打印info级别的日志
     * 
     * @param tag 模块打印标记
     * @param msg 日志内容
     */
	public static void i(String tag, String msg) {
		if (LOG_INFO) {
			Log.i(TAG, "[" + tag + "]" + msg);
		}
	}

    /**
     * 打印info级别的日志
     * 
     * @param tag 模块打印标记
     * @param msg 日志内容
     * @param tr 打印异常到日志
     */
	public static void i(String tag, String msg, Throwable tr) {
		if (LOG_INFO) {
			Log.i(TAG, "[" + tag + "]" + msg, tr);
		}
	}

    /**
     * 打印warning级别的日志
     * 
     * @param tag 模块打印标记
     * @param msg 日志内容
     */
	public static void w(String tag, String msg) {
		if (LOG_WARN) {
			Log.w(TAG, "[" + tag + "]" + msg);
		}
	}

    /**
     * 打印warning级别的日志
     * 
     * @param tag 模块打印标记
     * @param msg 日志内容
     * @param tr 打印异常到日志
     */
	public static void w(String tag, String msg, Throwable tr) {
		if (LOG_WARN) {
			Log.w(TAG, "[" + tag + "]" + msg, tr);
		}
	}

    /**
     * 打印error级别的日志
     * 
     * @param tag 模块打印标记
     * @param msg 日志内容
     */
	public static void e(String tag, String msg) {
		if (LOG_ERROR) {
			Log.e(TAG, "[" + tag + "]" + msg);
		}
	}

    /**
     * 打印error级别的日志
     * 
     * @param tag 模块打印标记
     * @param msg 日志内容
     * @param tr 打印异常到日志
     */
	public static void e(String tag, String msg, Throwable tr) {
		if (LOG_ERROR) {
			Log.e(TAG, "[" + tag + "]" + msg, tr);
		}
	}

}