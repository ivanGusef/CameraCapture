package com.gusev.test.cameracapture.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.gusev.test.cameracapture.R;
import com.gusev.test.cameracapture.view.PhotoGallery;

public class PhotoActivity extends Activity implements View.OnClickListener {

    private PhotoGallery photoGallery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_photo_activity);
        photoGallery = (PhotoGallery) findViewById(R.id.gallery);
        photoGallery.setOnPhotoClickListener(this);
    }

    public void onAdd(View view) {
        photoGallery.addPhoto(Uri.parse("android.resource://com.gusev.test.cameracapture/" + R.drawable.ic_launcher), "Simple text");
    }

    @Override
    public void onClick(View v) {
        photoGallery.removePhoto((ImageView) v);
    }
}
