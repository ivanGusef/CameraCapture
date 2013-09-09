package com.gusev.test.cameracapture.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.gusev.test.cameracapture.R;
import com.gusev.test.cameracapture.model.Photo;
import com.gusev.test.cameracapture.view.PhotoGallery;
import com.gusev.test.cameracapture.view.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends Activity implements PhotoGallery.OnPhotoClickListener {

    private PhotoGallery photoGallery;

    private static final int[] pictures = new int[]{R.drawable.australia, R.drawable.austria, R.drawable.azerbaijan,
            R.drawable.belarus, R.drawable.brazil, R.drawable.canada, R.drawable.china, R.drawable.croatia,
            R.drawable.cyprus, R.drawable.denmark, R.drawable.egypt, R.drawable.finland, R.drawable.france,
            R.drawable.germany, R.drawable.greece, R.drawable.japan, R.drawable.mexico, R.drawable.netherlands,
            R.drawable.poland, R.drawable.portugal, R.drawable.romania, R.drawable.russia, R.drawable.serbia,
            R.drawable.sweden, R.drawable.switzerland, R.drawable.ukraine, R.drawable.united_kingdom,
            R.drawable.united_states};

    private static final String[] names = new String[]{"Australia", "Austria", "Azerbaijan", "Belarus", "Brazil",
            "Canada", "China", "Croatia", "Cyprus", "Denmark", "Egypt", "Finland", "France", "Germany", "Greece",
            "Japan", "Mexico", "Netherlands", "Poland", "Portugal", "Romania", "Russia", "Serbia", "Sweden",
            "Switzerland", "Ukraine", "United Kingdom", "United States"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_photo_activity);
        photoGallery = (PhotoGallery) findViewById(R.id.gallery);
        photoGallery.setOnPhotoClickListener(this);
    }

    public void onAdd(View view) {
        List<Photo> photos = new ArrayList<Photo>();
        for (int i = 0; i < pictures.length; i++) {
            photos.add(new Photo(pictures[i], names[i]));
        }
        photoGallery.addPhotos(photos);
    }

    public void onTakePhoto(View view) {
        startActivity(new Intent(this, CameraActivity.class));
    }

    @Override
    public void onPhotoClick(PhotoView photoView) {
        photoGallery.removePhoto(photoView);
    }
}
