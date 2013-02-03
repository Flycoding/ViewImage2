package com.flyingh.viewimage2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private EditText editText;
	private ImageView imageView;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editText = (EditText) findViewById(R.id.path);
		imageView = (ImageView) findViewById(R.id.imageView);
		handler = new ImageHandler(getApplicationContext(), imageView);
	}

	public void view(View view) {
		new Thread(new ImageViewRunnable(editText.getText().toString(), handler)).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
