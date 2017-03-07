package com.tab28.xacida.online;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Environment;
import android.util.Log;

public class SongsProvider {
	// "/mnt/extsd/Music"
	static File root = Environment.getExternalStorageDirectory();
	static String mypath = root.getAbsolutePath() + "/xacidaonline/";
	private static final String path = new String(mypath);

	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

	// Constructor
	public SongsProvider() {

	}

	/**
	 * Function to read all mp3 files from sdcard and store the details in
	 * ArrayList
	 * */
	public ArrayList<HashMap<String, String>> getPlayList() {
		File musicFolder = null;
		HashMap<String, String> song = null;

		song = new HashMap<String, String>();
		song.put("songTitle",
				"Fatiha.mp3");
		song.put("songPath", "fatiha.mp3");
		// Adding each song to SongList
		songsList.add(song);
		if (existFile(path)) {
			musicFolder = new File(path);

			if (musicFolder.listFiles(new FileExtensionFilter()).length > 0) {
				for (File file : musicFolder
						.listFiles(new FileExtensionFilter())) {
					song = new HashMap<String, String>();
					String chemin = file.getName().substring(0,
							(file.getName().length() - 4));
					song.put(
							"songTitle",
							chemin);
					song.put("songPath", file.getPath());
					// Adding each song to SongList
					songsList.add(song);
				}
			}
		}
		// return songs list array
		return songsList;
	}

	/**
	 * The class is used to filter files which are having .mp3 extension
	 * */
	class FileExtensionFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return (name.endsWith(".mp3") || name.endsWith(".MP3"));
		}
	}

	public boolean existFile(String folderURL) {
		boolean isExist = false;
		File dir = null;
		try {
			dir = new File(folderURL);
			if (dir.exists())
				isExist = true;
			else {
				if (isExternalStorageAvailableAndWriteable())
					dir.mkdir();
				else {
					dir = new File(Environment.getDataDirectory(), "xacida");
					dir.mkdir();
				}
			}
		} catch (Exception e) {
			Log.d("existFile", e.getMessage());
		}
		return isExist;
	}

	// Storage states
	private boolean externalStorageAvailable, externalStorageWriteable;

	/**
	 * Checks the external storage's state and saves it in member attributes.
	 */
	private void checkStorage() {
		// Get the external storage's state
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			// Storage is available and writeable
			externalStorageAvailable = externalStorageWriteable = true;
		} else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
			// Storage is only readable
			externalStorageAvailable = true;
			externalStorageWriteable = false;
		} else {
			// Storage is neither readable nor writeable
			externalStorageAvailable = externalStorageWriteable = false;
		}
	}


	/**
	 * Checks the state of the external storage.
	 * 
	 * @return True if the external storage is available and writeable, false
	 *         otherwise.
	 */
	public boolean isExternalStorageAvailableAndWriteable() {
		checkStorage();
		if (!externalStorageAvailable) {
			return false;
		} else if (!externalStorageWriteable) {
			return false;
		} else {
			return true;
		}
	}

}
