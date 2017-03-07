package com.tab28.xacida.online;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class XacidaCustomAdapter extends ArrayAdapter<Xacida> {
	Context context;
	int layoutResourceId, fitre;
	ArrayList<Xacida> data = new ArrayList<Xacida>();
	private final Integer[] imageId;

	public XacidaCustomAdapter(Context context, int layoutResourceId,
			ArrayList<Xacida> data, Integer[] imageId) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
		this.imageId = imageId;
	}

	public XacidaCustomAdapter(Context context, int layoutResourceId,
			int fitre, ArrayList<Xacida> data, Integer[] imageId) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
		this.fitre = fitre;
		this.imageId = imageId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		XacidaHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new XacidaHolder();
			ImageView imageView = (ImageView) row.findViewById(R.id.img);
			imageView.setImageResource(imageId[0]);
			holder.textName = (TextView) row.findViewById(R.id.textView1);
			holder.textAuteur = (TextView) row.findViewById(R.id.textView2);
			//holder.btnEdit = (Button) row.findViewById(R.id.button2);
			//holder.btnDelete = (Button) row.findViewById(R.id.button2);
			row.setTag(holder);
		} else {
			holder = (XacidaHolder) row.getTag();
		}
		Xacida xacida = data.get(position);
		holder.textName.setText(xacida.getName());
		holder.textAuteur.setText(xacida.getAuteur());
//		holder.btnEdit.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Log.i("Edit Button Clicked", "**********");
//				Toast.makeText(context, "Edit button Clicked",
//						Toast.LENGTH_LONG).show();
//			}
//		});
//		holder.btnDelete.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Log.i("Delete Button Clicked", "**********");
//				Toast.makeText(context, "Delete button Clicked",
//						Toast.LENGTH_LONG).show();
//			}
//		});
		return row;

	}

	static class XacidaHolder {
		TextView textName;
		TextView textAuteur;
		//Button btnEdit;
//		Button btnDelete;
	}
}