package com.vnpt360;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import view.Menufragment;
import view.appmenu.EntryAdapter;
import view.appmenu.EntryItem;
import view.appmenu.Item;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.slidingmenu.lib.SlidingMenu;

//import com.congnhanvienthong.dhsc.ActivityDoThu;

public class MainActivity extends SherlockFragmentActivity implements
		Menufragment.MenuClickInterFace {
	protected SlidingMenu menu;
	ListView list;
	// MenuClickInterFace mClick;
	ListView listview = null;
	ArrayList<Item> items = new ArrayList<Item>();
	protected ActionBar ab;
	protected Context context;
	protected ArrayList<Object> ketqua;
	ProgressDialog mProgressDialog;
	protected View body, foot;
	TextView headerText;
	ImageView buttonMenu;
	Button shortcut1, shortcut2, shortcut3;
	protected boolean showDialog = true;
	ImageView bttRefresh;
	RelativeLayout headerBar;
	LinearLayout shortcutView;
	public View rightMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_to_display);
		context = MainActivity.this;
		headerText = (TextView) findViewById(R.id.text_header);
		bttRefresh = (ImageView) findViewById(R.id.btt_refresh);
		buttonMenu = (ImageView) findViewById(R.id.menuViewButton);
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu);
		menu.setSlidingEnabled(true);
		menu.setOnOpenListener(new SlidingMenu.OnOpenListener() {

			@Override
			public void onOpen() {
				// TODO Auto-generated method stub
				menu.setSlidingEnabled(true);
			}
		});
		Display display = getWindowManager().getDefaultDisplay();
		int screenWidth = display.getWidth();
		int wiMenu = (int) screenWidth * 3 / 4;
		menu.setBehindWidth(wiMenu);
		body = findViewById(R.id.body);
		foot = findViewById(R.id.footer);
		shortcutView = (LinearLayout) findViewById(R.id.test);

		list = (ListView) findViewById(R.id.listView_main);
		onCreatRightMenu();

		EntryAdapter adapter = new EntryAdapter(context, items);
		list.setAdapter(adapter);
		list.setCacheColorHint(Color.TRANSPARENT);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				try {
					@SuppressWarnings("rawtypes")
					Class temp = ((EntryItem) arg0.getItemAtPosition(arg2)).subtitle;

					if (temp == null) {
						AlertDialog.Builder alert = new AlertDialog.Builder(
								context);
						alert.setTitle("Thông báo!");
						alert.setMessage("Bạn có muốn thoát không ");

						alert.setPositiveButton("Thoát",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										Intent intent = new Intent(
												Intent.ACTION_MAIN);
										intent.addCategory(Intent.CATEGORY_HOME);
										intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
												| IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
										intent.putExtra("EXIT", true);
										finish();
										System.exit(0);
										startActivity(intent);

									}
								});
						alert.setNegativeButton("Quay lại", null);
						alert.show();
					} else {
						Intent intent = new Intent(getApplicationContext(),
								temp);

						startActivity(intent);
						overridePendingTransition(R.anim.slide_in_right,
								R.anim.slide_out_left);
						finish();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		});
	}

	protected void setHeader(String header) {
		headerText.setText(header);
		headerText.setSelected(true);

	}

	public void bothMenu(boolean flag) {
		if (!flag)
			menu.setMode(SlidingMenu.LEFT);
		else {
			menu.setMode(SlidingMenu.LEFT_RIGHT);
		}

	}

	public void setRightMenu(int viewID) {
		rightMenu = LayoutInflater.from(context).inflate(viewID, null);
		menu.setSecondaryMenu(viewID);

	}

	protected void enableRefresh() {
		bttRefresh.setVisibility(View.VISIBLE);
		bttRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
				startActivity(getIntent());

			}
		});

	}

	// Đổ dữ liệu vào menu
	protected void onCreatRightMenu() {

	}

	public void setBodyLayout(int id) {
		try {
			((RelativeLayout) body).removeAllViews();
			((RelativeLayout) body).addView(getLayoutInflater().inflate(id,
					null));
		} catch (Exception ex) {
		}
	}

	public void disableShortCut() {
		try {
			shortcutView.setVisibility(View.GONE);
		} catch (Exception ex) {
		}
	}

	public void setFootLayout(int id) {
		((RelativeLayout) foot).removeAllViews();
		((RelativeLayout) foot).addView(getLayoutInflater().inflate(id, null));
	}

	public void onsucces() {
	}

	@Override
	public void onListitemClick(String item) {
		// TODO Auto-generated method stub

	}

}
