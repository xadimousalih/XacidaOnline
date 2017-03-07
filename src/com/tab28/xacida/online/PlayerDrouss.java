package com.tab28.xacida.online;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerDrouss extends Activity implements OnClickListener,
		OnItemClickListener, AnimationListener {

	public static ImageView btnPlay, btnForward, btnBackward, btnNext,
			btnPrevious, listSongBtn, downloadfileBtn, lecteurBtn, backBtn,
			karaBtn;
	public static ImageButton btnShuffle, btnRepeat;
	public static SeekBar songProgressBar;
	private static final int RESULT_SETTINGS = 1;
	public static ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	private ListAdapter adapter;
	private ListView listSongLv;
	private LinearLayout playerScreen, listSongScreen;
	public static TextView songTitle, songCurrentDurationLabel,
			songTotalDurationLabel;
	public Intent playerService, downloadService;
	String myURL = null;
	private WebView webview;
	private TextView textView;
	private Button btnChange;
	String url = null;
	String xacidaname = null;
	String urlAudio = null;

	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.player_drouss);
		if (isTablet(this)) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			myURL = extras.getString("url");
			xacidaname = extras.getString("XacidaName");
			urlAudio = extras.getString("UrlAudio");
		}
		initViews();

		webview = (WebView) findViewById(R.id.webview);
		webview.setWebViewClient(new Callback());
		webview.loadUrl(myURL);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setPluginState(PluginState.ON);
		textView = (TextView) findViewById(R.id.textView);
		btnChange = (Button) findViewById(R.id.btnChange);
		btnChange.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (isOnline()) {

					if (webview.getVisibility() == View.VISIBLE) {
						webview.setVisibility(View.GONE);
						textView.setVisibility(View.VISIBLE);
						textView.setSelected(true);
						karaBtn.setVisibility(View.VISIBLE);
						btnChange.setText(PlayerDrouss.this.getResources()
								.getString(R.string.openxacida));

					} else {
						webview.setVisibility(View.VISIBLE);
						textView.setVisibility(View.GONE);
						karaBtn.setVisibility(View.GONE);
						btnChange.setText(PlayerDrouss.this.getResources()
								.getString(R.string.closexacida));
					}
				} else {
					Toast.makeText(
							PlayerDrouss.this,
							PlayerDrouss.this.getString(R.string.no_connection),
							Toast.LENGTH_LONG).show();
				}
			}
		});
		getSongsList();
		listSongLv = (ListView) findViewById(R.id.listsong_listview);
		playerService = new Intent(this, PlayerService.class);
		playerService.putExtra("songIndex", PlayerService.currentSongIndex);
		startService(playerService);

	}

	private ListAdapter getSongsList() {
		ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();

		SongsProvider plm = new SongsProvider();
		try {
			songsList = plm.getPlayList();
			for (int i = 0; i < songsList.size(); i++) {
				HashMap<String, String> song = songsList.get(i);
				songsListData.add(song);
			}
			adapter = new SimpleAdapter(this, songsListData,
					R.layout.listsong_item, new String[] { "songTitle" },
					new int[] { R.id.songTitle });
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return adapter;
	}

	@Override
	protected void onDestroy() {
		try {
			super.onDestroy();
			if (!PlayerService.mp.isPlaying()) {
				stopService(playerService);
				cancelNotification();
			}
		} catch (NullPointerException e) {
		} catch (IllegalStateException e) {
		} catch (Exception e) {

		}

	}

	public void cancelNotification() {
		try {
			String notificationServiceStr = Context.NOTIFICATION_SERVICE;
			NotificationManager mNotificationManager = (NotificationManager) getSystemService(notificationServiceStr);
			mNotificationManager.cancel(PlayerService.NOTIFICATION_ID);
		} catch (Exception e) {
		}
	}

	public boolean isOnline() {
		try {
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnected()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * Initiaze Views
	 */
	private void initViews() {
		try {

			playerScreen = (LinearLayout) findViewById(R.id.playerScreen);
			listSongScreen = (LinearLayout) findViewById(R.id.list_song_layout);
			listSongScreen.setVisibility(View.INVISIBLE);
			btnPlay = (ImageView) findViewById(R.id.btn_play_imageview);
			btnForward = (ImageView) findViewById(R.id.btn_forward_imageview);
			btnBackward = (ImageView) findViewById(R.id.btn_backward_imagview);
			btnNext = (ImageView) findViewById(R.id.btn_next_imageview);
			btnPrevious = (ImageView) findViewById(R.id.btn_previous_imageview);
			listSongBtn = (ImageView) findViewById(R.id.listsong_btn);
			downloadfileBtn = (ImageView) findViewById(R.id.downloadsong_btn);
			btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
			btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
			songProgressBar = (SeekBar) findViewById(R.id.song_playing_progressbar);
			songTitle = (TextView) findViewById(R.id.song_title_txt);
			songTitle.setText(xacidaname);
			songCurrentDurationLabel = (TextView) findViewById(R.id.current_time_txt);
			songTotalDurationLabel = (TextView) findViewById(R.id.total_time_txt);
			backBtn = (ImageView) findViewById(R.id.back_btn);
			karaBtn = (ImageView) findViewById(R.id.kara);
			btnPlay.setOnClickListener(this);
			btnForward.setOnClickListener(this);
			btnBackward.setOnClickListener(this);
			btnNext.setOnClickListener(this);
			btnPrevious.setOnClickListener(this);
			btnShuffle.setOnClickListener(this);
			btnRepeat.setOnClickListener(this);
			listSongBtn.setOnClickListener(this);
			if (urlAudio == null || urlAudio.equals("XND")) {
				downloadfileBtn.setVisibility(View.GONE);
			} else {
				downloadfileBtn.setVisibility(View.VISIBLE);

				downloadfileBtn.setOnClickListener(this);

			}
			backBtn.setOnClickListener(this);
		} catch (Exception e) {
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			openOptionsDialog();
			return true;
		case R.id.app_exit:
			exitOptionsDialog();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void exitOptionsDialog() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.str_exit)
				.setMessage(R.string.app_exit_message)
				.setNegativeButton(R.string.str_no,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
							}
						})
				.setPositiveButton(R.string.str_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
								exitPlayer();
							}
						}).show();
	}

	private void openOptionsDialog() {
		AboutDialog about = new AboutDialog(this);
		about.setTitle(Html.fromHtml(this.getString(R.string.app_about)));
		about.show();
	}

	private void exitPlayer() {
		try {
			if (PlayerService.mp.isPlaying())
				PlayerService.mp.stop();
			finish();
		} catch (Exception e) {
		}
	}

	/**
	 * View OnclickListener Implement
	 * 
	 * @param v
	 */
	public void onClick(View v) {
		try {
			switch (v.getId()) {
			case R.id.listsong_btn:
				adapter = getSongsList();
				listSongLv.setAdapter(adapter);
				listSongLv.setOnItemClickListener(this);
				showListSongScreen();

				break;
			case R.id.downloadsong_btn:
				if (isOnline()) {
					if (urlAudio != null && !urlAudio.equals("XND")) {
						downloadService = new Intent(this,
								DownloadUpdateService.class);
						downloadService.putExtra("downloadUrl", urlAudio);
						startService(downloadService);
					}
				} else {
					Toast.makeText(
							PlayerDrouss.this,
							PlayerDrouss.this.getString(R.string.no_connection),
							Toast.LENGTH_LONG).show();
				}

				break;
			case R.id.back_btn:
				showListSongScreen();
				break;
			}
		} catch (Exception e) {
		}

	}

	/**
	 * onItemClick Listener Implement.
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		try {
			switch (parent.getId()) {
			case R.id.listsong_listview:
				playerService = new Intent(this, PlayerService.class);
				playerService.putExtra("songIndex", position);
				startService(playerService);
				showListSongScreen();
				break;
			}
		} catch (Exception e) {
		}

	}

	/**
	 * Show Listsong Screen with Sliding Animation
	 */
	private boolean isVisible = false;

	private void showListSongScreen() {
		Animation anim;
		if (isVisible == false) {
			listSongScreen.setVisibility(View.VISIBLE);
			anim = AnimationUtils.loadAnimation(this, R.anim.push_down_in);
			isVisible = true;
		} else {
			anim = AnimationUtils.loadAnimation(this, R.anim.push_down_out);
			isVisible = false;
			playerScreen.setVisibility(View.VISIBLE);

		}
		anim.setAnimationListener(this);
		listSongScreen.startAnimation(anim);

	}

	public void onAnimationStart(Animation animation) {

	}

	public void onAnimationEnd(Animation animation) {
		if (isVisible == true)
			playerScreen.setVisibility(View.INVISIBLE);
		else
			listSongScreen.setVisibility(View.INVISIBLE);
	}

	public void onAnimationRepeat(Animation animation) {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SETTINGS:
			break;
		}
	}

	private class Callback extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return (false);
		}
	}

}
