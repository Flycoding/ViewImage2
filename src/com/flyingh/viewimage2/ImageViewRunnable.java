package com.flyingh.viewimage2;

import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import com.flyingh.message.MessageConst;

public class ImageViewRunnable implements Runnable {
	private String path;
	private Handler handler;

	public ImageViewRunnable(String path, Handler handler) {
		this.path = path;
		this.handler = handler;
	}

	@Override
	public void run() {
		Message msg = handler.obtainMessage();
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == 200) {
				msg.what = MessageConst.OK;
				msg.getData().putParcelable("bitmap", BitmapFactory.decodeStream(conn.getInputStream()));
				handler.sendMessage(msg);
			} else {
				msg.what = MessageConst.ERROR_CODE;
				msg.getData().putInt("error_code", conn.getResponseCode());
				handler.sendMessage(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.what = MessageConst.ERROR;
			msg.getData().putString("error", e.getMessage());
			handler.sendMessage(msg);
		}
	}

}
