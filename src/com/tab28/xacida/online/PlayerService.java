package com.tab28.xacida.online;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerService extends Service implements OnCompletionListener,
		OnClickListener, OnSeekBarChangeListener {

	private WeakReference<ImageButton> btnRepeat, btnShuffle;
	private WeakReference<ImageView> btnPlay, btnForward, btnBackward, btnNext,
			btnPrevious;
	private WeakReference<SeekBar> songProgressBar;
	private WeakReference<TextView> songTitleLabel;
	private WeakReference<TextView> songCurrentDurationLabel;
	private WeakReference<TextView> songTotalDurationLabel;
	public static MediaPlayer mp;
	private Handler progressBarHandler = new Handler();;
	private Utilities utils;
	private int seekForwardTime = 5000; // 5000 milliseconds
	private int seekBackwardTime = 5000; // 5000 milliseconds
	private boolean isShuffle = false;
	private boolean isRepeat = false;
	private ArrayList<HashMap<String, String>> songsListingSD = new ArrayList<HashMap<String, String>>();
	public static int currentSongIndex = -1;

	@Override
	public void onCreate() {
		try {
			mp = new MediaPlayer();
			mp.setOnCompletionListener(this);
			mp.reset();
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC);//
			utils = new Utilities();
			songsListingSD = PlayerDrouss.songsList;
			songCurrentDurationLabel = new WeakReference<TextView>(
					PlayerDrouss.songCurrentDurationLabel);
			super.onCreate();
		} catch (Exception e) {
		}

	}

	// --------------onStartCommand-------------------------------------------------------------------------//
	@SuppressWarnings("deprecation")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		initUI();
		try {
			int songIndex = intent.getIntExtra("songIndex", 0);
			if (songIndex != currentSongIndex) {
				playSong(songIndex);
				initNotification(songIndex);
				currentSongIndex = songIndex;
			} else if (currentSongIndex != -1) {
				String monTitre = songsListingSD.get(currentSongIndex).get(
						"songTitle");
				songTitleLabel.get().setText(monTitre);
				if (mp.isPlaying())
					btnPlay.get().setImageResource(R.drawable.ic_media_pause);
				else
					btnPlay.get().setImageResource(R.drawable.ic_media_play);
			}
			super.onStart(intent, startId);
		} catch (NullPointerException e) {
		} catch (ArrayIndexOutOfBoundsException e) {
		} catch (Exception e) {
		}
		return START_STICKY;
	}

	/**
 * 	
 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Init UI
	 */
	private void initUI() {
		try {
			songTitleLabel = new WeakReference<TextView>(PlayerDrouss.songTitle);
			songCurrentDurationLabel = new WeakReference<TextView>(
					PlayerDrouss.songCurrentDurationLabel);
			songTotalDurationLabel = new WeakReference<TextView>(
					PlayerDrouss.songTotalDurationLabel);

			btnPlay = new WeakReference<ImageView>(PlayerDrouss.btnPlay);
			btnForward = new WeakReference<ImageView>(PlayerDrouss.btnForward);
			btnBackward = new WeakReference<ImageView>(PlayerDrouss.btnBackward);
			btnNext = new WeakReference<ImageView>(PlayerDrouss.btnNext);
			btnPrevious = new WeakReference<ImageView>(PlayerDrouss.btnPrevious);
			btnRepeat = new WeakReference<ImageButton>(PlayerDrouss.btnRepeat);
			btnShuffle = new WeakReference<ImageButton>(PlayerDrouss.btnShuffle);
			btnPlay.get().setOnClickListener(this);
			btnForward.get().setOnClickListener(this);
			btnBackward.get().setOnClickListener(this);
			btnNext.get().setOnClickListener(this);
			btnPrevious.get().setOnClickListener(this);
			btnRepeat.get().setOnClickListener(this);
			btnShuffle.get().setOnClickListener(this);
			songProgressBar = new WeakReference<SeekBar>(
					PlayerDrouss.songProgressBar);
			songProgressBar.get().setOnSeekBarChangeListener(this);
		} catch (NullPointerException e) {
		} catch (Exception e) {
		}
	}

	public void onClick(View v) {
		try {
			switch (v.getId()) {
			case R.id.btn_play_imageview:
				if (currentSongIndex != -1) {
					if (mp.isPlaying()) {
						if (mp != null) {
							mp.pause();
							btnPlay.get().setImageResource(
									R.drawable.ic_media_play);
							Log.d("Player Service", "Pause");
						}
					} else {
						if (mp != null) {
							mp.start();
							btnPlay.get().setImageResource(
									R.drawable.ic_media_pause);
							Log.d("Player Service", "Play");
						}
					}
				}
				break;

			case R.id.btn_next_imageview:
				Log.d("Player Service", "Next");
				if (currentSongIndex < (songsListingSD.size() - 1)) {
					playSong(currentSongIndex + 1);
					currentSongIndex = currentSongIndex + 1;
				} else {
					playSong(0);
					currentSongIndex = 0;
				}
				break;

			case R.id.btn_forward_imageview:

				int currentPosition = mp.getCurrentPosition();
				if (currentPosition + seekForwardTime <= mp.getDuration()) {
					mp.seekTo(currentPosition + seekForwardTime);
				} else {
					// forward to end position
					mp.seekTo(mp.getDuration());
				}
				break;

			case R.id.btn_backward_imagview:
				// get current song position
				int currentPosition2 = mp.getCurrentPosition();
				// check if seekBackward time is greater than 0 sec
				if (currentPosition2 - seekBackwardTime >= 0) {
					// forward song
					mp.seekTo(currentPosition2 - seekBackwardTime);
				} else {
					// backward to starting position
					mp.seekTo(0);
				}
				break;

			case R.id.btn_previous_imageview:

				if (currentSongIndex > 0) {
					playSong(currentSongIndex - 1);
					currentSongIndex = currentSongIndex - 1;
				} else {
					// play last song
					playSong(songsListingSD.size() - 1);
					currentSongIndex = songsListingSD.size() - 1;
				}
				break;

			case R.id.btnRepeat:

				if (isRepeat) {
					isRepeat = false;
					Toast.makeText(getApplicationContext(), "Repeat is OFF",
							Toast.LENGTH_SHORT).show();
					btnRepeat.get().setImageResource(R.drawable.btn_repeat);
				} else {
					// make repeat to true
					isRepeat = true;
					Toast.makeText(getApplicationContext(), "Repeat is ON",
							Toast.LENGTH_SHORT).show();
					// make shuffle to false
					isShuffle = false;
					btnRepeat.get().setImageResource(
							R.drawable.btn_repeat_focused);
					btnShuffle.get().setImageResource(R.drawable.btn_shuffle);
				}
				break;

			case R.id.btnShuffle:

				if (isShuffle) {
					isShuffle = false;
					Toast.makeText(getApplicationContext(), "Shuffle is OFF",
							Toast.LENGTH_SHORT).show();
					btnShuffle.get().setImageResource(R.drawable.btn_shuffle);
				} else {
					// make repeat to true
					isShuffle = true;
					Toast.makeText(getApplicationContext(), "Shuffle is ON",
							Toast.LENGTH_SHORT).show();
					// make shuffle to false
					isRepeat = false;
					btnShuffle.get().setImageResource(
							R.drawable.btn_shuffle_focused);
					btnRepeat.get().setImageResource(R.drawable.btn_repeat);
				}
				break;
			}
		} catch (NullPointerException e) {
		} catch (Exception e) {
		}
	}

	// -------------------------------------------------------------//

	/**
	 * @author www.9android.net
	 * @param songIndex
	 *            : index of song
	 */
	public void playSong(int songIndex) {
		// Play song
		String songTitle = null;
		try {
			mp.reset();
			songTitle = songsListingSD.get(songIndex).get("songTitle");

			if (!songTitle.equals("Fatiha.mp3"))
				mp.setDataSource(songsListingSD.get(songIndex).get("songPath"));
			else {
				mp.setDataSource(
						getApplicationContext(),
						Uri.parse("android.resource://com.tab28.xacida.online/raw/"
								+ R.raw.fatiha));
			}
			mp.prepare();
			mp.start();
			// Displaying Song title
			// songTitleLabel.get().setText(songTitle);
			// Changing Button Image to pause image
			btnPlay.get().setImageResource(R.drawable.ic_media_pause);
			// set Progress bar values
			songProgressBar.get().setProgress(0);
			songProgressBar.get().setMax(100);
			// Updating progress bar
			updateProgressBar();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ----------------onSeekBar Change Listener------------------------------//
	/**
	 * Update timer on seekbar
	 * */
	public void updateProgressBar() {
		progressBarHandler.postDelayed(mUpdateTimeTask, 100);
	}

	/**
	 * Background Runnable thread
	 * */
	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			try {

				long totalDuration = 0;
				try {
					totalDuration = mp.getDuration();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (NullPointerException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				long currentDuration = 0;
				try {
					currentDuration = mp.getCurrentPosition();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
				// Displaying Total Duration time
				songTotalDurationLabel.get().setText(
						"" + utils.milliSecondsToTimer(totalDuration));
				// Displaying time completed playing
				songCurrentDurationLabel.get().setText(
						"" + utils.milliSecondsToTimer(currentDuration));

				// Updating progress bar
				int progress = (int) (utils.getProgressPercentage(
						currentDuration, totalDuration));
				// Log.d("Progress", ""+progress);
				songProgressBar.get().setProgress(progress);
				// Running this thread after 100 milliseconds
				progressBarHandler.postDelayed(this, 100);
				// Log.d("AndroidBuildingMusicPlayerActivity","Runable  progressbar");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};

	// ----------------on Seekbar Change
	// Listener---------------------------------------//
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromTouch) {
	}

	/**
	 * When user starts moving the progress handler
	 * */
	public void onStartTrackingTouch(SeekBar seekBar) {
		// remove message Handler from updating progress bar
		progressBarHandler.removeCallbacks(mUpdateTimeTask);
	}

	/**
	 * When user stops moving the progress hanlder
	 * */
	public void onStopTrackingTouch(SeekBar seekBar) {
		progressBarHandler.removeCallbacks(mUpdateTimeTask);
		int totalDuration = mp.getDuration();
		int currentPosition = utils.progressToTimer(seekBar.getProgress(),
				totalDuration);

		// forward or backward to certain seconds
		mp.seekTo(currentPosition);

		// update timer progress again
		updateProgressBar();
	}

	/**
	 * On Song Playing completed if repeat is ON play same song again if shuffle
	 * is ON play random song
	 * */
	public void onCompletion(MediaPlayer arg0) {
		try {

			// check for repeat is ON or OFF
			if (isRepeat) {
				// repeat is on play same song again
				playSong(currentSongIndex);
			} else if (isShuffle) {
				// shuffle is on - play a random song
				Random rand = new Random();
				currentSongIndex = rand
						.nextInt((songsListingSD.size() - 1) - 0 + 1) + 0;
				playSong(currentSongIndex);
			} else {
				// no repeat or shuffle ON - play next song
				if (currentSongIndex < (songsListingSD.size() - 1)) {
					playSong(currentSongIndex + 1);
					currentSongIndex = currentSongIndex + 1;
				} else {
					// play first song
					playSong(0);
					currentSongIndex = 0;
				}
			}
		} catch (Exception e) {
		}

	}

	// ---------------------------------------------------------//
	@Override
	public void onDestroy() {
		try {
			super.onDestroy();
			currentSongIndex = -1;
			// Remove progress bar update Hanlder callBacks
			progressBarHandler.removeCallbacks(mUpdateTimeTask);
			Log.d("Player Service", "Player Service Stopped");
			if (mp != null) {
				if (mp.isPlaying()) {
					mp.stop();
				}
				mp.release();
			}
		} catch (Exception e) {
		}

	}

	// --------------------Push Notification
	// Set up the notification ID
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;

	// Create Notification
	@SuppressWarnings("deprecation")
	private void initNotification(int songIndex) {
		try {

			String ns = Context.NOTIFICATION_SERVICE;
			mNotificationManager = (NotificationManager) getSystemService(ns);
			int icon = R.drawable.notification;
			CharSequence songName = songsListingSD.get(songIndex).get(
					"songTitle");
			CharSequence songNameOK = songName.toString().substring(0,
					(songName.toString().length() - 4));
			long when = System.currentTimeMillis();
			Notification notification = new Notification(icon, songNameOK, when);
			notification.flags = Notification.FLAG_ONGOING_EVENT;
			Context context = getApplicationContext();

			Intent notificationIntent = new Intent(this, PlayerDrouss.class);
			PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
					notificationIntent, 0);
			notification.setLatestEventInfo(context, songName, null,
					contentIntent);
			mNotificationManager.notify(NOTIFICATION_ID, notification);
		} catch (Exception e) {
		}

	}

}
