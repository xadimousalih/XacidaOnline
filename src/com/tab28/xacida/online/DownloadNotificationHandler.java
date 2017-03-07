package com.tab28.xacida.online;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class DownloadNotificationHandler {

    private static final String DOWNLOAD_NOTIFICATION_TAG = "com.tab28.xacida.online";
    private static final int DOWNLOAD_NOTIFICATION_ID = 1;
    private static final int UPDATE_INTERVAL = 3000;
    private Context mContext;
    private Notification mDownNotification;
    private RemoteViews mContentView;
    private NotificationManager mNm;

    private long mLastUpdateTime = -1;

    DownloadNotificationHandler(Context context) {
        mContext = context;
        mNm = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void sendNotification() {
        initNotification();
        mNm.notify(DOWNLOAD_NOTIFICATION_TAG, DOWNLOAD_NOTIFICATION_ID,
                mDownNotification);
        mLastUpdateTime = System.currentTimeMillis();
    }

    @SuppressWarnings("deprecation")
	private void initNotification() {
        String contextText = null;
        mDownNotification = new Notification(
                android.R.drawable.stat_sys_download, contextText,
                System.currentTimeMillis());
        mDownNotification.flags = Notification.FLAG_ONGOING_EVENT;
        initContentView();
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
                new Intent(mContext, PlayerDrouss.class), 0);

        mDownNotification.contentIntent = contentIntent;
    }

    private void initContentView() {
        mContentView = new RemoteViews(mContext.getPackageName(),
                R.layout.download_notification_view);
        mContentView.setProgressBar(R.id.download_progressbar, 100, 0, false);
        mContentView.setImageViewResource(R.id.app_icon,
                android.R.drawable.stat_sys_download);
        mContentView.setTextViewText(R.id.download_information,
                mContext.getString(R.string.udx));
        mContentView.setTextViewText(R.id.download_progress_text, "0%");
        mDownNotification.contentView = mContentView;
    }

    public void updateNotification(int progress) {
        if (checkUpdateInterval()) {
            mContentView.setProgressBar(R.id.download_progressbar, 100,
                    progress, false);
            mContentView.setTextViewText(R.id.download_progress_text, progress
                    + "%");
            mDownNotification.contentView = mContentView;
            mNm.notify(DOWNLOAD_NOTIFICATION_TAG, DOWNLOAD_NOTIFICATION_ID,
                    mDownNotification);
        }
    }

    private boolean checkUpdateInterval() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastUpdateTime > UPDATE_INTERVAL) {
            mLastUpdateTime = currentTime;
            return true;
        }
        return false;
    }

    public void cancelNotification() {
        if (mDownNotification != null) {
            mNm.cancel(DOWNLOAD_NOTIFICATION_TAG, DOWNLOAD_NOTIFICATION_ID);
        }
    }
}
