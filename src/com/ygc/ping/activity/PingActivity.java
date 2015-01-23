package com.ygc.ping.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class PingActivity extends Activity {
	private TextView pingResult;
	private String ip;
	private ScrollView scrollView;
	private ProgressBar progressBar;
	private TextView baseTitle;
	private IPing mService;
	private boolean isConnect = false;
	private static final String TAG = PingActivity.class.getSimpleName();
	private StringBuffer sBuffer = new StringBuffer();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ping_activity);
		initView();
		Intent intent = new Intent("com.ygc.ping.activity.ACTION");
		Bundle args = new Bundle();
		intent.putExtras(args);
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
		
		ip = "127.0.0.1";
		if (TextUtils.isEmpty(ip)) {
			Log.d(TAG, "ip==null");
			Toast.makeText(this, R.string.string_ip_empty, Toast.LENGTH_LONG).show();
			return;
		} else {
			new Thread() {
				@Override
				public void run() {
					boolean isStop = true;
					sBuffer.delete(0, sBuffer.length());
					try {
						while (isStop) {
							if (mService != null) {
								isStop = false;
								mService.startPing("ping " + ip, forActivity);
							}
						}
					} catch (RemoteException e) {
						e.printStackTrace();
						try {
							mService.stopPing();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}
				}
			}.start();
		}
	}

	private void initView() {
		pingResult = (TextView) findViewById(R.id.tv_ping_result);
		scrollView = (ScrollView) findViewById(R.id.sl_view);
		progressBar = (ProgressBar) findViewById(R.id.pb_progress_bar);
		baseTitle = (TextView) findViewById(R.id.tv_base_title);
	}
	
	private final PingCallBack.Stub forActivity = new PingCallBack.Stub() {

		@Override
		public void ping(String str) throws RemoteException {
			sBuffer.append("\n");
			sBuffer.append(str);
			int len = sBuffer.length();
			if(len>5000){
				sBuffer.delete(0, 2000);
			}
			runOnUiThread(new Runnable() {
				public void run() {
					scrollView.fullScroll(ScrollView.FOCUS_DOWN);
					pingResult.setText(sBuffer.toString());
				}
			});
			PrintLog.d(TAG, "---------->>ping" + str);
		}

		@Override
		public void pingStart() throws RemoteException {
			runOnUiThread(new Runnable() {
				public void run() {
					baseTitle.setText("Loading...");
					progressBar.setVisibility(View.VISIBLE);
				}
			});
			PrintLog.d(TAG, "---------->>pingStart");
		}

		@Override
		public void pingStop() throws RemoteException {
			runOnUiThread(new Runnable() {
				public void run() {
					progressBar.setVisibility(View.GONE);
					baseTitle.setText(getString(R.string.string_ping_str));
					finish();
				}
			});
			PrintLog.d(TAG, "---------->>pingStop");
		}
	};

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService = null;
			setConnect(false);
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = IPing.Stub.asInterface(service);
			setConnect(true);
		}
	};

	public void setConnect(boolean isConnect) {
		this.isConnect = isConnect;
	}
	
	@Override
	public void onBackPressed() {
		try {
			if(mService!=null){
				mService.stopPing();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (isConnect) {
			unbindService(serviceConnection);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
