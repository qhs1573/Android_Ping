package com.ygc.ping.activity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class CommandService extends Service {
	private static final String TAG = CommandService.class.getSimpleName();
	private boolean isStop=false;
	
	@Override
	public void onCreate() {
		super.onCreate();
		PrintLog.d(TAG, "onCreate--->>");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		PrintLog.d(TAG, "onStartCommand--->>");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		PrintLog.d(TAG, "onBind--->>");
		return iPing;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		PrintLog.d(TAG, "onUnbind--->>");
		isStop=false;
//		exit();
		return super.onUnbind(intent);
	}
	
	public void exit(){
		ActivityManager manager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);     
		manager.killBackgroundProcesses(getPackageName());
		System.exit(0);
	}

	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
		PrintLog.d(TAG, "onRebind--->>");
	}

	private IPing.Stub iPing = new IPing.Stub() {

		@Override
		public void startPing(final String command,final PingCallBack pingCallBack) throws RemoteException {
			try {
				PrintLog.d(TAG, "---->>startPing");
				 exec(command, pingCallBack);
			} catch (Exception e) {
				e.printStackTrace();
				isStop=false;
				PrintLog.e(TAG, "--->>"+e.getMessage());
			}
		}

		@Override
		public void stopPing() throws RemoteException {
			isStop=false;
		}
	};
	

	private void exec(String command, PingCallBack pingCallBack)throws Exception {
		Runtime runtime = Runtime.getRuntime();
		Process proc = runtime.exec(command);
		InputStreamReader inputstreamreader = new InputStreamReader(proc.getInputStream());
		BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
		InputStreamReader errstreamreader = new InputStreamReader(proc.getErrorStream());
		BufferedReader errorreader = new BufferedReader(errstreamreader);
		try {
			{
				isStop = true;
				if (pingCallBack != null) {
					pingCallBack.pingStart();
				}
				String line;
				while (isStop && ((line = bufferedreader.readLine()) != null || (line = errorreader.readLine()) != null)) {
					if (line == null) {
						break;
					} else {
						PrintLog.e(TAG, "---Running...");
						if (pingCallBack != null) {
							pingCallBack.ping(line);
						}
					}
				}
			}
		} catch (Exception e) {
			PrintLog.d(TAG, "ping exception::" + e);
			e.printStackTrace();
			throw e;
		} finally {
			isStop = false;
			proc.destroy();
			PrintLog.e(TAG, "---Stop...");
			if (errorreader != null)
				errorreader.close();
			if (errstreamreader != null)
				errstreamreader.close();
			if (bufferedreader != null)
				bufferedreader.close();
			if (inputstreamreader != null)
				inputstreamreader.close();
			if (pingCallBack != null) {
				pingCallBack.pingStop();
			}
			 exit();
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		isStop=false;
		PrintLog.d(TAG, "onDestroy--->>");
	}
}
