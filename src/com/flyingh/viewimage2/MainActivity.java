package com.flyingh.viewimage2;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {
	protected static final String TAG = "MainActivity";
	protected static final int OK = 1;
	protected static final int ERROR = -1;
	private EditText pathText;
	private ImageView imageView;
	private Handler handler = new UIHandler();

	private class UIHandler extends Handler {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case OK:
				imageView.setImageBitmap((Bitmap) msg.getData().get("bitmap"));
				break;
			case ERROR:
				Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		};
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pathText = (EditText) findViewById(R.id.path);
		imageView = (ImageView) findViewById(R.id.imageView);
	}

	public void view(View view) throws MalformedURLException, IOException {
		new ImageViewThread(pathText.getText().toString()).start();
	}

	private class ImageViewThread extends Thread {
		private String path;

		public ImageViewThread(String path) {
			super();
			this.path = path;
		}

		@Override
		public void run() {
			Message msg = handler.obtainMessage();
			try {
				HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				if (conn.getResponseCode() == 200) {
					msg.what = OK;
					msg.getData().putParcelable("bitmap", BitmapFactory.decodeStream(conn.getInputStream()));
					handler.sendMessage(msg);
				} else {
					msg.what = ERROR;
					handler.sendMessage(msg);
				}
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
				msg.what = ERROR;
				handler.sendMessage(msg);
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
