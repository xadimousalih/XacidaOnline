package com.tab28.xacida.online;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class XacidaOnlineActivity extends Activity {
	ListView xacidaList;
	XacidaCustomAdapter xacidaAdapter;
	ArrayList<Xacida> xacidaArray = new ArrayList<Xacida>();
	// Search EditText
	ArrayList<Xacida> sortedXacida = new ArrayList<Xacida>();
	int textlength = 0;
	EditText inputSearch;
	// Spinner langue;
	// String malangue;
	public static final String MIME_TYPE_PDF = "application/pdf";

	/**
	 * Check if the supplied context can render PDF files via some installed
	 * application that reacts to a intent with the pdf mime type and viewing
	 * action.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean canDisplayPdf(Context context) {
		PackageManager packageManager = context.getPackageManager();
		Intent testIntent = new Intent(Intent.ACTION_VIEW);
		testIntent.setType(MIME_TYPE_PDF);
		if (packageManager.queryIntentActivities(testIntent,
				PackageManager.MATCH_DEFAULT_ONLY).size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	Integer[] imageId = { R.drawable.ahmadou };

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xacidaonline);
		AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
		// Setting Dialog Title
		alertDialog1.setTitle(this.getString(R.string.bienvenu));
		// Setting Dialog Message
		alertDialog1.setMessage(Html.fromHtml("<center>"
				+ this.getString(R.string.obj1) + "<br/>"
				+ this.getString(R.string.obj2) + "</center>"));
		// Setting Icon to Dialog
		alertDialog1.setIcon(R.drawable.serignsaliou);
		// Setting OK Button
		alertDialog1.setButton(this.getString(R.string.str_yes),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to execute after dialog closed
						if (dialog != null)
							dialog.dismiss();
						Toast.makeText(
								getApplicationContext(),
								XacidaOnlineActivity.this
										.getString(R.string.dieredieuf),
								Toast.LENGTH_SHORT).show();
					}
				});

		// Showing Alert Message
		alertDialog1.show();
		/**
		 * add item in arraylist
		 */
		xacidaArray
				.add(new Xacida(
						"Achiinou Mouhdiamatane",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Achiinou%20mouhdiamatane.pdf",
						"http://www.tab28.com/media/xacidas/Achinou.mp3"));

		xacidaArray.add(new Xacida("Adiaabanii Rabbou Sama",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Adiaabanii-ar.pdf",
				"http://www.tab28.com/media/xacidas/AjabaniRabbouSama.mp3"));

		xacidaArray
				.add(new Xacida(
						"Adjabani Khayrou Baaqin",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Adjabani%20khayrouba.pdf",
						"http://www.tab28.com/media/xacidas/Adjabani_Khayrou_Baaqin.mp3"));

		xacidaArray
				.add(new Xacida(
						"Advice to the murids From Sheikh Ibrahima Fall",
						"Cheikh Ibrahima Fall Baaboul Mouridina",
						"http://khassidaenpdf.free.fr/khassida_pdf/Advice-to-the-murids-from-Sheikh-Ibrahima-Fall.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Ahbabtou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Ahbabtou.pdf",
				"http://www.tab28.com/media/xacidas/Mafatihoul_Jinane.mp3"));

		xacidaArray
				.add(new Xacida(
						"Ahounzou Bilahi min Maily",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Ahonzoubilahi%20min%20maily.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Alaa Innani Ousni",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Alaa_inani_ousni.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Alal Mountaqa et Yakitaabal Kariimi et Koun Kaatimane",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Alal_montakha-yakitaabal_kariimi-kounakaatimane.pdf",
						"http://www.tab28.com/media/xacidas/Alal_Mountaqa.mp3"));

		xacidaArray.add(new Xacida("Alamane",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Alamane-ar.pdf",
				"http://www.tab28.com/media/xacidas/Alamane.mp3"));

		xacidaArray
				.add(new Xacida(
						"Alayka Ya Moukhtaarou",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Alayka%20yaa%20moukhtarou.pdf",
						"http://www.tab28.com/media/xacidas/Alayka_Ya_Moukhtaarou.mp3"));


		xacidaArray
				.add(new Xacida(
						"Alhamdoulilahi Wahdahou",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Alhamdoulilahiwahdahou.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Anta Rabbi (Français)",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Anta-Rabi-fr.pdf",
				"XND"));

		xacidaArray
				.add(new Xacida(
						"Anta Rabbi",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Antarabi.pdf",
						"XND"));


		xacidaArray
				.add(new Xacida(
						"Ashaaboul Djannati",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",

						"http://khassidaenpdf.free.fr/khassida_pdf/Ashaboul_jannati.pdf",
						"XND"));


		xacidaArray
				.add(new Xacida(
						"Asmaoul Husna",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Asma-ul-Husna-ar.pdf",
						"http://www.tab28.com/media/xacidas/Asmaoul_Husna.mp3"));


		xacidaArray.add(new Xacida("Assiirou (Français)",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Assirou-fr.pdf",
				"http://www.tab28.com/media/xacidas/Assirou.mp3"));

		xacidaArray.add(new Xacida("Assiirou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Assirou.pdf",
				"http://www.tab28.com/media/xacidas/Assirou.mp3"));

		xacidaArray
				.add(new Xacida(
						"Astaghfiroulaaha Bihi",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Astahfiroulaha%20bihi.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Aaxirou Zamaan (Français)",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Axiru_zaman.pdf",
				"XND"));

		xacidaArray.add(new Xacida("Ayassa min Allahou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Ayassamine.pdf",
				"http://www.tab28.com/media/xacidas/Ayassa_Min_Allahou.mp3"));

		xacidaArray.add(new Xacida("Bakh Bakhaa",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Bakh%20bakha.pdf",
				"XND"));

		xacidaArray
				.add(new Xacida(
						"Barakatoul Moukhtaarou",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Barakatou.pdf",
						"http://www.tab28.com/media/xacidas/Barakatoul_Moukhtaarou.mp3"));

		xacidaArray.add(new Xacida("Mafaatihoul Bichri",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Bichri.pdf",
				"http://www.tab28.com/media/xacidas/Bichri.mp3"));

		xacidaArray
				.add(new Xacida(
						"Bihaqi",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Bihakhi.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Bihaqi",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Bihaqi.pdf", "XND"));
		xacidaArray
				.add(new Xacida(
						"Bismillahi Lezi",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Bismillahi%20lezi.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Bouchraa Lanaa",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Bouchera-lana-ar.pdf",
						"http://www.tab28.com/media/xacidas/Bouchraa_Lanaa.mp3"));
		xacidaArray
				.add(new Xacida(
						"Bouchraa Lanaa",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Boucheraa_lanaa.pdf",
						"http://www.tab28.com/media/xacidas/Bouchraa_Lanaa.mp3"));

		xacidaArray.add(new Xacida("Bouchraa Lanaa",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Bouchra.pdf",
				"http://www.tab28.com/media/xacidas/Bouchraa_Lanaa.mp3"));

		xacidaArray.add(new Xacida("Bouchraa Lanaa",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Buchra.pdf",
				"http://www.tab28.com/media/xacidas/Bouchraa_Lanaa.mp3"));

		xacidaArray
				.add(new Xacida(
						"Calendrier Khassida",
						"Cheikh Abdoul Khadre Mbacké",
						"http://khassidaenpdf.free.fr/khassida_pdf/Calendrier%20khassida.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Calligraphie de Cheikh Abdoul Ahad",
						"Cheikh Abdoul Ahad Mbacké",
						"http://khassidaenpdf.free.fr/khassida_pdf/Calligraphie%20de%20Ch.%20Abdoul%20Ahad.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Chayaatinal Ounaas",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Chayaatinal-Ounaas.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Dialibatoul Maraakhib",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Dialibatou%20maraakhib.pdf",
						"XND"));


		xacidaArray.add(new Xacida("Diawartou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Diawartou.pdf",
				"http://www.tab28.com/media/xacidas/Jaawartou.mp3"));

		xacidaArray.add(new Xacida("Djawartou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Djawartou.pdf",
				"http://www.tab28.com/media/xacidas/Jaawartou.mp3"));

		xacidaArray.add(new Xacida("Djazbou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Djazbou.pdf",
				"http://www.tab28.com/media/xacidas/Dieuzbou.mp3"));

		xacidaArray.add(new Xacida("Djeuzbou (Français))",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Djeuzbou-fr.pdf",
				"http://www.tab28.com/media/xacidas/Dieuzbou.mp3"));

		xacidaArray
				.add(new Xacida(
						"Dol0 (Table des matières)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Dol-TABLE%20DES%20MATIERES.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Dol1",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Dol1.pdf", "XND"));

		xacidaArray.add(new Xacida("Dol2",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Dol2.pdf", "XND"));

		xacidaArray.add(new Xacida("Dol3",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Dol3.pdf", "XND"));

		xacidaArray.add(new Xacida("Dol4",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Dol4.pdf", "XND"));

		xacidaArray.add(new Xacida("Dol5",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Dol5.pdf", "XND"));

		xacidaArray.add(new Xacida("Dol6",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Dol6.pdf", "XND"));

		xacidaArray.add(new Xacida("Faridj Bidjahil Moustapha",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/FARIDJ.pdf", "XND"));
		xacidaArray.add(new Xacida("Faazal Laziina",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Faazal-Laziina.pdf",
				"XND"));
		xacidaArray
				.add(new Xacida(
						"Faazate Qilaamii",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Faazat%20qilaami.pdf",
						"http://www.tab28.com/media/xacidas/Faazate_Qilaamii.mp3"));

		xacidaArray
				.add(new Xacida(
						"Fathoul Moukarramil Baaqil Khadim",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Fathoul-Moukarrimi-ar.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Fi chahri Ramadaana Haama Alsachine",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Fi%20cheuhri%20ramadaana%20haama%20alssachin.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Fuzti (Félicité A Toi Marie)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Fuzti-Felicite-A-Toi-Marie.pdf",
						"http://www.tab28.com/media/xacidas/Fouzty.mp3"));

		xacidaArray
				.add(new Xacida(
						"Hadiyati",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Hadiyati.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Alal Mountaqa",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Halalmountakha.pdf",
				"http://www.tab28.com/media/xacidas/Alal_Mountaqa.mp3"));

		xacidaArray.add(new Xacida("Hasbounal Lahou Wo Nihmal Wakil",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Hamidtu-ar.pdf",
				"XND"));

		xacidaArray
				.add(new Xacida(
						"Hammat Soulaymane",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Hammat-Sulaymaa-ar.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Hammat Soulaymane",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Hammat_soulayma.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Hasbounal Lahou",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Hasbunaallaahu-ar.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Haynou Rahmati",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Haynou%20rahmati%20khassida.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Houqqal Boukaaou",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Huqqal%20bukka_u.pdf",
						"http://www.tab28.com/media/xacidas/Houqqal_Boukaaou.mp3"));

		xacidaArray.add(new Xacida("Houqqal Boukaaou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Huqqal.pdf",
				"http://www.tab28.com/media/xacidas/Houqqal_Boukaaou.mp3"));

		xacidaArray
				.add(new Xacida(
						"Ilhaamou Salam (Français)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/ILHMUL-SALAM-fr.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Ihdi Jami",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Ihdi_jami.pdf",
				"XND"));

		xacidaArray.add(new Xacida("Ihdi Jami",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Ihdidjami.pdf",
				"XND"));

		xacidaArray
				.add(new Xacida(
						"Ila Ghayrina Qad Wadjahal",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Ila%20hayrina%20Khad%20wa%20djahal.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Ila Ghayrina - Lyanqada",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Ila_khayrina.pdf",
				"XND"));

		xacidaArray.add(new Xacida("Ila Mouhidine",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Ila_mouhidine.pdf",
				"XND"));

		xacidaArray.add(new Xacida("Ileyka Yaa",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Ileyka%20Yaa.pdf",
				"http://www.tab28.com/media/xacidas/Ilayka.mp3"));

		xacidaArray
				.add(new Xacida(
						"Ilhamoul Wadoud (Français)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Ilhamul_Wadud_Cheikh_Ahmadou_Bamba.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Inani Houztou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Inani_houstou.pdf",
				"http://www.tab28.com/media/xacidas/Inani_Houstou.mp3"));


		xacidaArray.add(new Xacida("Inni Aqoulou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Inni-Akholou.pdf",
				"XND"));
		
		xacidaArray
				.add(new Xacida(
						"Irwaou Nadiim",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Irwaawun_naddym.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Irwaou Nadiim",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Irwaou%20nadim[1].pdf",
						"XND"));
		
		xacidaArray
				.add(new Xacida(
						"Irwaou Nadiim(Wolof)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Irwaun-Nadiim-wolof.pdf",
						"XND"));
		
		xacidaArray
				.add(new Xacida(
						"Islaahud Daarayni",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Islaahud-Daarayni-ar.pdf",
						"XND"));
		
		xacidaArray.add(new Xacida("Islam",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Islam.pdf", "XND"));

		xacidaArray.add(new Xacida("Jaawartou (Français)",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Jawartou-fr.pdf",
				"http://www.tab28.com/media/xacidas/Jaawartou.mp3"));

		xacidaArray.add(new Xacida("Jaawartou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Jawartu.pdf",
				"http://www.tab28.com/media/xacidas/Jaawartou.mp3"));

		xacidaArray
				.add(new Xacida(
						"Jawharou Nafiis",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Jawharou-n-nafis.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Jazboul Qouloub (Français)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Jazboul%20khouloob.pdf",
						"http://www.tab28.com/media/xacidas/Dieuzbou.mp3"));

		xacidaArray
				.add(new Xacida(
						"Khaatimatoul Mounaadjati(Français)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/KHATIMATOU-MOUNAJATI-fr.pdf",
						"http://www.tab28.com/media/xacidas/Khaatimatoul_Mounaadjati.mp3"));

		xacidaArray.add(new Xacida("Koun Kaatimane",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/KUN-KAATIMAN.pdf",
				"XND"));

		xacidaArray
				.add(new Xacida(
						"Kamilou Warche",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Kamilou%20warse.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Walaqad Karamna et  Minal Haqqi",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Karamna%20wal%20Minal%20Haqqi.pdf",
						"http://www.tab28.com/media/xacidas/Waqani.mp3"));

		xacidaArray.add(new Xacida("Karamna",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Karamna-ar.pdf",
				"http://www.tab28.com/media/xacidas/Karamna.mp3"));

		xacidaArray.add(new Xacida("Kawine",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Kawine-ar.pdf",
				"http://www.tab28.com/media/xacidas/Kawine.mp3"));

		xacidaArray.add(new Xacida("Kawine",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Kawine_liyal.pdf",
				"http://www.tab28.com/media/xacidas/Kawine.mp3"));

		xacidaArray
				.add(new Xacida(
						"Khaalou Liyarkan",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Khaalou%20liyal%20kaan.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Khadmoul Qourane",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Khadmoul_khourane.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Irwawoun Nadiim (Français)",
						"Cheikh Mouhamadou Lamine Diop Dagana",
						"http://khassidaenpdf.free.fr/khassida_pdf/Abreuvoir-du-commensal-irwanoun%20nadiim.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Khalo Liyarkane",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Khalo%20liyarkane.pdf",
						"XND"));


		xacidaArray
				.add(new Xacida(
						"Khassida Ramadane",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Khassida_ramadane.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Laa Takhaf",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Laa-Takhaf.pdf",
				"XND"));

		xacidaArray
				.add(new Xacida(
						"Laka",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Laka-ar.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Lamyabdou",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Lamyabdu.pdf",
						"XND"));
		
		xacidaArray
				.add(new Xacida(
						"Lirabbine Kariimine",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Li%20Rabbin%20Kariimine.pdf",
						"XND"));
		
		xacidaArray
				.add(new Xacida(
						"Lillahi Rabbil Lezii",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Lillahi%20Rabbil%20Leuzi.pdf",
						"http://www.tab28.com/media/xacidas/Lillahi_Rabbil_Lezi.mp3"));

		xacidaArray
				.add(new Xacida(
						"Limaahine Bashiirine",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Limahine_bashirine.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Lirabine Khaffourine",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Lirabbin%20Qaffour.pdf",
						"http://www.tab28.com/media/xacidas/Lirabine_Khafourine.mp3"));
		xacidaArray
				.add(new Xacida(
						"Lirabine Khaffourine",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Lirabine%20khafforine.pdf",
						"http://www.tab28.com/media/xacidas/Lirabine_Khafourine.mp3"));
		xacidaArray
				.add(new Xacida(
						"Lirabbine Kariimine",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Lirabine_kariimine.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Liyane Qaada",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Liyane%20Qaada%20mimmane%20Bil%20Leuzi.pdf",
						"http://www.tab28.com/media/xacidas/Liyanqada.mp3"));
		xacidaArray
				.add(new Xacida(
						"Mawaahibou Naafih(Français)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/MAWAHIBOU-NAFIH-fr.pdf",
						"http://www.tab28.com/media/xacidas/Mawahibou.mp3"));

		xacidaArray
				.add(new Xacida(
						"Madahtou",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Madahtou.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Maddal Kabirou et Lissaanou Choukri",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Maddal%20habirou-Lissanou.pdf",
						"http://www.tab28.com/media/xacidas/Madal_khabiroul.mp3"));
		xacidaArray
				.add(new Xacida(
						"Maddal Khabirou",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Maddal-khabirou-ar.pdf",
						"http://www.tab28.com/media/xacidas/Madal_khabiroul.mp3"));


		xacidaArray.add(new Xacida("Mane Zannani",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Manezanani.pdf",
				"http://www.tab28.com/media/xacidas/Mane_Zanneni.mp3"));

		xacidaArray
				.add(new Xacida(
						"Maraamiya",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Maramiya.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Massalikoul Jinnan (Français)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Masaalik.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Massalikoul Jinnan (Français)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Massalik-ul%20jinnan.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Matlabou Chifayi  (Français)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Mathlabou-Chifaa.pdf",
						"http://www.tab28.com/media/xacidas/Chifahi.mp3"));
		xacidaArray
				.add(new Xacida(
						"Matlabou Chifayi (Français)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Matlabou%20chiffaai.pdf",
						"http://www.tab28.com/media/xacidas/Chifahi.mp3"));

		xacidaArray
				.add(new Xacida(
						"Matlaboul Fawzayni (Français)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Matlaboul%20fawzayni.pdf",
						"http://www.tab28.com/media/xacidas/Fawzeyni.mp3"));
		xacidaArray
				.add(new Xacida(
						"Matlabou Chifayi",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Matlaboul%20shiffah.pdf",
						"http://www.tab28.com/media/xacidas/Chifahi.mp3"));

		xacidaArray
				.add(new Xacida(
						"Matlabou Chifayi",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Matlaboul_chiffai.pdf",
						"http://www.tab28.com/media/xacidas/Chifahi.mp3"));
		xacidaArray
				.add(new Xacida(
						"Matlabou Chifayi",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Matlabul-Chifah-ar.pdf",
						"http://www.tab28.com/media/xacidas/Chifahi.mp3"));

		xacidaArray.add(new Xacida("Matlabou Chifayi",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Matlabushifayi.pdf",
				"http://www.tab28.com/media/xacidas/Chifahi.mp3"));

		xacidaArray
				.add(new Xacida(
						"Mawahiboul Khoudouss (Français)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Mawahboul%20khoudouss.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Mawahibou (Français)",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Mawahibou%20.pdf",
				"http://www.tab28.com/media/xacidas/Mawahibou.mp3"));

		xacidaArray
				.add(new Xacida(
						"Mawahibou Nafih (Français)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Mawahibou-Nafih-ar.pdf",
						"http://www.tab28.com/media/xacidas/Mawahibou.mp3"));

		xacidaArray.add(new Xacida("Mawahibou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Mawahibou.pdf",
				"http://www.tab28.com/media/xacidas/Mawahibou.mp3"));

		xacidaArray.add(new Xacida("Midaadi (Français)",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Midaadi.pdf",
				"http://www.tab28.com/media/xacidas/Midadi.mp3"));
		
		xacidaArray.add(new Xacida("Midadi (Français)",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Midadi-fr.pdf",
				"http://www.tab28.com/media/xacidas/Midadi.mp3"));
		
		xacidaArray
				.add(new Xacida(
						"Miftahoun Nassr",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Miftahoul_nassr.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Miftahoun Nasri",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Miftahu%20Nasri.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Miftahoun Nasri",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Miftahun-nasri.pdf",
				"XND"));

		xacidaArray
				.add(new Xacida(
						"Miftahoun Nasri",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Miftahun-nasri001.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Mimiya",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Mimiya.pdf",
				"http://www.tab28.com/media/xacidas/Mimiya.mp3"));

		xacidaArray
				.add(new Xacida(
						"Minal Lawhil Mahfouz",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Minal%20lawhil%20mahfoussi.pdf",
						"XND"));
		
		xacidaArray.add(new Xacida("Minal Haqqi",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Minal-Haqqi.pdf",
				"XND"));
		
		xacidaArray.add(new Xacida("Mix 1",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Mix-1.pdf", "XND"));
		
		xacidaArray.add(new Xacida("Moukhadamaate",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Mouhadimat.pdf",
				"http://www.tab28.com/media/xacidas/Moukhadimat.mp3"));
		
		xacidaArray.add(new Xacida("Moukhadamaate (Français)",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Moukhadimat.pdf",
				"http://www.tab28.com/media/xacidas/Moukhadimat.mp3"));
		

		xacidaArray
				.add(new Xacida(
						"Nadjzil Moukhssinine",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Nadjzil-moukhssinine.pdf",
						"XND"));
		
		xacidaArray
				.add(new Xacida(
						"Nahjul Xadaa Al Haaji (Français)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Nahdjou.pdf",
						"XND"));
		
		xacidaArray
				.add(new Xacida(
						"Nahjul Xadaa Al Haaji(wolof)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Nahjul-Xadaail-Haaji-wolof.pdf",
						"XND"));


		xacidaArray
				.add(new Xacida(
						"Qad Akhani",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Qad%20akhani-NB.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Qad Akhani",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Quad%20akhani.pdf",
				"XND"));


		xacidaArray.add(new Xacida("Raaiya",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Raaiya.pdf",
				"http://www.tab28.com/media/xacidas/Rahiya.mp3"));

		xacidaArray.add(new Xacida("Rabbi Bimaa Chachrhou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Rabbi.pdf",
				"http://www.tab28.com/media/xacidas/Rabbi.mp3"));

		xacidaArray.add(new Xacida("Rabbi Bimaa Chachrhou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Rabbi002.pdf",
				"http://www.tab28.com/media/xacidas/Rabbi.mp3"));

		xacidaArray.add(new Xacida("Rabiya Ahmadou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Rabiya-ahmadou.pdf",
				"http://www.tab28.com/media/xacidas/Rabbia_Ahmadou.mp3"));
		
		xacidaArray
				.add(new Xacida(
						"Rabiya Ahmadou 2",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Rabiya-ahmadou_2.pdf",
						"http://www.tab28.com/media/xacidas/Rabbia_Ahmadou.mp3"));
		
		xacidaArray.add(new Xacida("Radiitou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Raditou.pdf",
				"http://www.tab28.com/media/xacidas/Raditou.mp3"));
		
		xacidaArray
				.add(new Xacida(
						"Rafakhna et Liyan Qaada",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Rafakhna-liyan-khada-ar.pdf",
						"http://www.tab28.com/media/xacidas/Liyanqada.mp3"));
		xacidaArray.add(new Xacida("Raiyya",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Raiyya.pdf",
				"http://www.tab28.com/media/xacidas/Rahiya.mp3"));
		xacidaArray.add(new Xacida("Roumna",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Roumna.pdf",
				"http://www.tab28.com/media/xacidas/Roumna.mp3"));
		xacidaArray
				.add(new Xacida(
						"SIlkoul Jawahir (Français)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/SILKUL-JAWAAHIRI-fr.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Sabhoune Takhi (Français)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Sabhoune-takhi-fr.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Sabhoune Takhii",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Sabhun%20takhii.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Chahrou Ramadaane Tahhara Kouliyati",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Sahri_ramadana_tahara_Kouliyati.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Chahrou Ramadaane Cirriya",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Sahrou_ramadana_siriya.pdf",
						"XND"));
		xacidaArray.add(new Xacida("Sayadjhala Lahou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Sayajhala.pdf",
				"XND"));
		xacidaArray
				.add(new Xacida(
						"Serign Mbaye diakhate",
						"Serign Mbaye diakhate",
						"http://khassidaenpdf.free.fr/khassida_pdf/Serign%20Mbaye%20diakhate.pdf",
						"XND"));
		xacidaArray.add(new Xacida("Chakawtou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Shakawtou.pdf",
				"XND"));
		xacidaArray
				.add(new Xacida(
						"Silkoul Jawahir 1",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Silkoul%20Jawahir_1.pdf",
						"XND"));
		xacidaArray.add(new Xacida("Sindidi",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Sindidi.pdf",
				"http://www.tab28.com/media/xacidas/Sindidi.mp3"));

		xacidaArray.add(new Xacida("Sindidi (Français)",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Sindidi_fr.pdf",
				"http://www.tab28.com/media/xacidas/Sindidi.mp3"));

		xacidaArray
				.add(new Xacida(
						"Tanwiirou Coudour",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Tanwiirussudor-ar.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Tazawwoudou Cighar(Wolof)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Tasawudus-Sixaar-wolof.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Tawbatou Nassoukh",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Tawbatou%20Nassoukh.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Tawbatou Nassoukh",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Tawbatou-ar.pdf",
				"XND"));

		xacidaArray
				.add(new Xacida(
						"Tawbatou Nassoukh",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Tawbatou_nassoukh.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Tazawwoudou Cighar",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Tazawudu%20ss%20sikhar.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Tazawwoudou Cighar",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Tazawwud-ss-sighar.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Tazawwoudou Choubbaane",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Tazawwudou-sh-subban.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Touhfatou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Toufatou.pdf",
				"http://www.tab28.com/media/xacidas/Touhfatou.mp3"));

		xacidaArray
				.add(new Xacida(
						"Touhfatoul Hawaa",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Touhatoutoul%20awaa.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Touhfatou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Tuhfatu.pdf",
				"http://www.tab28.com/media/xacidas/Touhfatou.mp3"));

		xacidaArray
				.add(new Xacida(
						"Viatique du Muharram",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Viatique_du_Muharram.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Walqad Karramna (Français)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/WALAQAD-KARAMNA-BANNI-ADAMA-fr.pdf",
						"http://www.tab28.com/media/xacidas/Karamna.mp3"));

		xacidaArray
				.add(new Xacida(
						"Wadiahtou Wadjhiya",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/WAdieuhtou_wadjhiya.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Wadiahtou Lilahi Hamdane",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Wadieuhtou_lilahi_hamdane.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Wadiahtou Wadjhiya",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Wadieuhtou_wadjhiya.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Wadjahtou Lillahi Hamdane",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Wadjahtou%20Lillahi%20Hamdane.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Wadjahtou Wadjhiyazaa  Koulliyazaa",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Wadjahtou%20Wadjhiya%20zaa%20-Koulliya%20zaa.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Wadjahtou lil Haadi",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Wadjahtou%20lil%20Haadi.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Wakaana Haqqane",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Wakaanahaqann.pdf",
				"http://www.tab28.com/media/xacidas/Wakana.mp3"));

		xacidaArray
				.add(new Xacida(
						"Wakhani Madadii Yassourrou",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Wakhani-Madadii-Yassourrou.pdf",
						"http://www.tab28.com/media/xacidas/Midadi.mp3"));
		xacidaArray
				.add(new Xacida(
						"Waqaanii et Midaadi et Yassourou",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Wakhani_midadi_yassourou.pdf",
						"http://www.tab28.com/media/xacidas/Waqani.mp3"));
		xacidaArray
				.add(new Xacida(
						"Wa Qoul Rabbi",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/WakhoulRabbi-ar.pdf",
						"XND"));
		xacidaArray.add(new Xacida("Wal Baladou",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Wal_baladou.pdf",
				"XND"));

		xacidaArray
				.add(new Xacida(
						"Walqad Karramna",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Walakhad_karamena.pdf",
						"http://www.tab28.com/media/xacidas/Karamna.mp3"));

		xacidaArray
				.add(new Xacida(
						"Wal Baladou Tayibou",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Walbaladou-Tayibou-ar.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Walqad Karramna",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Walqad%20karramna.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Waqaani",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Waqaani.pdf",
				"http://www.tab28.com/media/xacidas/Waqani.mp3"));

		xacidaArray
				.add(new Xacida(
						"Wakana Haqqane",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Waqana%20Haqqane.pdf",
						"http://www.tab28.com/media/xacidas/Wakana.mp3"));

		xacidaArray
				.add(new Xacida(
						"Wassilatou Roubouh",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Wassilatou%20Roubokh.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Wa Wassaynal Inssana Biwaalidayhi Housnan",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Wawassaynal%20Inssana%20Biwaalidayhi%20Housnan.pdf",
						"XND"));

		xacidaArray
				.add(new Xacida(
						"Woudohou",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Woudohou.pdf",
						"XND"));

		xacidaArray.add(new Xacida("Kharnou Bi", "Serigne Moussa Ka",
				"http://khassidaenpdf.free.fr/khassida_pdf/XARNU%20BI.pdf",
				"XND"));

		xacidaArray
				.add(new Xacida(
						"Kharnou Bi",
						"Serigne Moussa Ka",
						"http://khassidaenpdf.free.fr/khassida_pdf/XARNU-BI.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Khaatimul munadiati",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Xaatimul_munadiati.pdf",
						"http://www.tab28.com/media/xacidas/Khaatimatoul_Mounaadjati.mp3"));

		xacidaArray.add(new Xacida("Xidma",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Xidma.pdf", "XND"));

		xacidaArray.add(new Xacida("Xurratul Ayni",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Xurratul_ayni.pdf",
				"XND"));

		xacidaArray
				.add(new Xacida(
						"Yaa Khayra Dayfine(Français)",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/YA-KHAYRA-DAYFINN-fr.pdf",
						"http://www.tab28.com/media/xacidas/Ya_Khayra_Dayfin.mp3"));
		xacidaArray
				.add(new Xacida(
						"Yaa Rabbi bil Moustapha",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Ya%20rabbi%20bil%20moustapha.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Yaa Khayra Dayfine",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Ya_khayra_dayfin.pdf",
						"http://www.tab28.com/media/xacidas/Ya_Khayra_Dayfin.mp3"));
		xacidaArray
				.add(new Xacida(
						"Yaazal Bouchaaraati",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Ya_zal_bousharati.pdf",
						"http://www.tab28.com/media/xacidas/Ya_Zal_Bouchaaraty.mp3"));
		xacidaArray
				.add(new Xacida(
						"Yaa Moukrima  Dayfi et Yaa Khayra Dayfine",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Yaa%20Moukrimaa-Yaa%20Khayra.pdf",
						"http://www.tab28.com/media/xacidas/Ya_Moukrimou_Dayfi.mp3"));
		xacidaArray
				.add(new Xacida(
						"Yaa Kitaabal Kariimi  et Koun Kaatimane",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Yaa%20kitabal%20kariimi-%20Kounkaatimane.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Yaa mane bi ame adjabani khayrou bahine",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Yaa%20mane%20bi%20ame_adjabani%20khayrou%20bahine.pdf",
						"XND"));
		xacidaArray
				.add(new Xacida(
						"Yaa Moukrima  Dayfi et Yaa Khayra Dayfine",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Yaa_moukrima_yaa_khayra_dayfine.pdf",
						"http://www.tab28.com/media/xacidas/Ya_Moukrimou_Dayfi.mp3"));
		xacidaArray
				.add(new Xacida(
						"Yaarabii",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Yaarabii.pdf",
						"XND"));
		xacidaArray.add(new Xacida("Yaqiini",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Yaqiini.pdf",
				"http://www.tab28.com/media/xacidas/Yaqini.mp3"));
		xacidaArray
				.add(new Xacida(
						"Yarahmanou Yarahimou",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Yarahmanou-Yarahimou.pdf",
						"http://www.tab28.com/media/xacidas/Rabbi.mp3"));
		xacidaArray
				.add(new Xacida(
						"Zadoul Moussaafir",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/Zadoul-moussafir.pdf",
						"XND"));
		xacidaArray.add(new Xacida("Zalat",
				"Cheikh Ahmadou Bamba Khadimou Rassoul",
				"http://khassidaenpdf.free.fr/khassida_pdf/Zalat.pdf", "XND"));
		xacidaArray
				.add(new Xacida(
						"Tawbatou Nassoukh",
						"Cheikh Ahmadou Bamba Khadimou Rassoul",
						"http://khassidaenpdf.free.fr/khassida_pdf/tawbatou_nassoukh%20(2).pdf",
						"XND"));
		/**
		 * set item into adapter
		 */
		xacidaAdapter = new XacidaCustomAdapter(XacidaOnlineActivity.this,
				R.layout.row, R.id.textView1, xacidaArray, imageId);
		xacidaList = (ListView) findViewById(R.id.listView);
		xacidaList.setItemsCanFocus(false);
		xacidaList.setAdapter(xacidaAdapter);
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		inputSearch.addTextChangedListener(new TextWatcher() {

			@SuppressLint("DefaultLocale")
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				textlength = inputSearch.getText().length();

				sortedXacida.clear();
				// langue = (Spinner) findViewById(R.id.spinner);
				// malangue = String.valueOf(langue.getSelectedItem());
				// if (malangue.equalsIgnoreCase("French")) {
				// malangue = "Français";
				// } else if (malangue.equalsIgnoreCase("Arabic")) {
				// malangue = "Arabe";
				// }

				for (int i = 0; i < xacidaArray.size(); i++) {
					if (textlength <= xacidaArray.get(i).getName().length()) {
						if (xacidaArray
								.get(i)
								.getName()
								.trim()
								.toLowerCase()
								.contains(
										inputSearch.getText().toString().trim()
												.toLowerCase())
						// && (xacidaArray.get(i).getName().trim()
						// .contains(malangue.trim()))
						) {
							sortedXacida.add(xacidaArray.get(i));
						}
					}
				}
				xacidaList.setAdapter(new XacidaCustomAdapter(
						XacidaOnlineActivity.this, R.layout.row,
						R.id.textView1, sortedXacida, imageId));

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});
		/**
		 * get on item click listener
		 */
		xacidaList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					final int position, long id) {
				String xacida_name = null;
				String googleDocsUrl2 = null;
				String urlAudio = null;
				//if (isOnline()) {

					textlength = inputSearch.getText().length();
					if (textlength == 0) {
						// googleDocsUrl = "https://docs.google.com/viewer?url="
						// + xacidaArray.get(position).getUrl();
						googleDocsUrl2 = "http://docs.google.com/gview?embedded=true&url="
								+ xacidaArray.get(position).getUrl();
						xacida_name = xacidaArray.get(position).getName();
						urlAudio = xacidaArray.get(position).getUrlAudio();
					} else {
						// googleDocsUrl = "https://docs.google.com/viewer?url="
						// + sortedXacida.get(position).getUrl();
						googleDocsUrl2 = "http://docs.google.com/gview?embedded=true&url="
								+ sortedXacida.get(position).getUrl();
						xacida_name = sortedXacida.get(position).getName();
						urlAudio = sortedXacida.get(position).getUrlAudio();

					}
					/*
					 * V1 0k Intent intent = new Intent(Intent.ACTION_VIEW);
					 * intent.setDataAndType(Uri.parse(googleDocsUrl),
					 * "text/html"); startActivity(intent);
					 */
					Intent i = new Intent(getApplicationContext(),
							PlayerDrouss.class);
					i.putExtra("url", googleDocsUrl2);
					i.putExtra("XacidaName", xacida_name);
					i.putExtra("UrlAudio", urlAudio);
					startActivity(i);
//				} else {
//					Toast.makeText(
//							XacidaOnlineActivity.this,
//							XacidaOnlineActivity.this
//									.getString(R.string.no_connection),
//							Toast.LENGTH_LONG).show();
//				}
			}

		});

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
								onDestroy();
								finish();
								System.exit(0);
							}
						}).show();
	}

	private void openOptionsDialog() {
		AboutDialog about = new AboutDialog(this);
		about.setTitle(Html.fromHtml(this.getString(R.string.app_about)));
		about.show();
	}

}
