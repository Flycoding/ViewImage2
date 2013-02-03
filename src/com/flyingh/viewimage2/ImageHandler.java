package com.flyingh.viewimage2;

import static com.flyingh.message.MessageConst.ERROR;
import static com.flyingh.message.MessageConst.ERROR_CODE;
import static com.flyingh.message.MessageConst.OK;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageHandler extends Handler {
	private Context ctx;
	private ImageView imageView;

	public ImageHandler(Context ctx, ImageView imageView) {
		super();
		this.ctx = ctx;
		this.imageView = imageView;
	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case OK:
			imageView.setImageBitmap((Bitmap) msg.getData().get("bitmap"));
			break;
		case ERROR:
			Toast.makeText(ctx, "error:" + msg.getData().get("error"), Toast.LENGTH_LONG).show();
			break;
		case ERROR_CODE:
			Toast.makeText(ctx, "error_code:" + msg.getData().get("error_code"), Toast.LENGTH_LONG).show();
			break;

		default:
			break;
		}
	}

}
