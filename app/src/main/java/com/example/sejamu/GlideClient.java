package com.example.sejamu;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

public class GlideClient {

    public static void downloadImage(Context context, String url, ImageView imageView) {
        if(url != null && url.length() > 0) {
            Log.d(MainActivity.DEBUG_TAG, url);
            Glide.with(context).load(url).fitCenter()
                    .error(R.drawable.broken_image)
                    .into(imageView);
        } else {
            Glide.with(context).load(R.drawable.image_not_found).into(imageView);
        }
    }
}