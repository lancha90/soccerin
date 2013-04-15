package com.dmha.soccerin.asynctask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;

public class DownloadImagesTask extends AsyncTask<ImageView, Void, Bitmap>{

	ImageView imageView = null;

	protected Bitmap doInBackground(ImageView... imageViews) {
	    this.imageView = imageViews[0];
	    return download_Image((String)imageView.getTag());
	}

	protected void onPostExecute(Bitmap result) {
	    imageView.setImageBitmap(result);
	}


	private Bitmap download_Image(String url) {
		
	    Bitmap bmp =null;
        try{
            URL ulrn = new URL(url);
            HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
            InputStream is = con.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
            if (null != bmp)
                return bmp;

            }catch(Exception e){}
        return bmp;
	}
	

}
