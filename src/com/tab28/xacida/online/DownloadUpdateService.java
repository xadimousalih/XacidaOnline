package com.tab28.xacida.online;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;

public class DownloadUpdateService extends Service {
	private static final String LOG_TAG = "DownloadUpdateService";

	private static final int DOWNLOAD_TIMEOUT = 20 * 1000;

	// TODO: Test URL
	private String mFileFullPath, downloadUrl;

	private Context mContext;
	private boolean isUpdating = false;
	private DownloadNotificationHandler mNotificationHandler;

	@Override
	public void onCreate() {
		mContext = this;
		mNotificationHandler = new DownloadNotificationHandler(mContext);
	}

	@Override
	public void onDestroy() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public boolean isHaveNewVersion() {
		return false;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Log.d(LOG_TAG, "DownloadUpdateService Started");
		Bundle extra = intent.getExtras();
		if (extra != null) {
			downloadUrl = extra.getString("downloadUrl");
			// downloadUrl =
			// "http://download.quranicaudio.com/quran/alhusaynee_al3azazee_with_children/112.mp3";
		}

		if (!downloadUrl.endsWith("mp3")) {
			Toast.makeText(this, "update_update_url_wrong", Toast.LENGTH_SHORT)
					.show();
		} else {
			if (!isUpdating) {
				DownloadTask task = new DownloadTask();
				if (Build.VERSION.SDK_INT >= 11) {
					task.execute();
				} else {
					task.execute();
				}
			} else {
				Toast.makeText(this, getResources().getString(R.string.udx),
						Toast.LENGTH_SHORT).show();
			}
		}
		return Service.START_NOT_STICKY;
	}

	@SuppressWarnings("deprecation")
	public static long getAvailableInternalMemorySize() {
		File path = Environment.getExternalStorageDirectory();
		Log.d(LOG_TAG, "PATH: " + path);
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	public String getFileName(String url) {
		int taille = url.length();
		int positionDernierSlash = url.lastIndexOf("/");
		String fileName = url.substring(positionDernierSlash + 1, taille);
		return fileName;
	}

	private boolean checkAndUpdatePath() {
		boolean result = false;
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/xacidaonline");
		if (!file.exists()) {
			result = file.mkdirs();
		}
		result = file.canWrite();
		if (result) {

			mFileFullPath = Environment.getExternalStorageDirectory()
					+ "/xacidaonline/" + getFileName(downloadUrl);
		}
		return result;
	}

	private void showToastMsg(String msgResId) {
		Toast.makeText(mContext, msgResId, Toast.LENGTH_SHORT).show();
	}

	class DownloadTask extends AsyncTask<Void, Integer, Integer> {
		@Override
		protected Integer doInBackground(Void... params) {
			Log.d(LOG_TAG, "doInBackground ");

			try {
				downloadApk();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return 1;
		}

		private void downloadApk() throws Exception {
			URL url;
			url = new URL(downloadUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(DOWNLOAD_TIMEOUT);
			int length = conn.getContentLength();
			checkAndUpdatePath();
			File apkFile = new File(mFileFullPath);
			FileOutputStream fos = new FileOutputStream(apkFile);
			InputStream is = conn.getInputStream();
			int count = 0;
			byte buf[] = new byte[1024 * 2];
			int progress = 0;
			do {
				int numread = is.read(buf);
				count += numread;
				progress = (int) (((float) count / length) * 100);
				publishProgress(progress);
				if (numread <= 0) {
					break;
				}
				fos.write(buf, 0, numread);
			} while (true);
		}

		@Override
		protected void onPreExecute() {
			showToastMsg(DownloadUpdateService.this.getResources().getString(R.string.usd));
			mNotificationHandler.sendNotification();
		}

		@Override
		protected void onPostExecute(Integer result) {
			Log.d(LOG_TAG, "onPostExecute: " + result);
			mNotificationHandler.cancelNotification();
			isUpdating = false;
			DownloadUpdateService.this.stopSelf();
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			mNotificationHandler.updateNotification(progress[0]);
		}
	}
}