package com.gusev.test.cameracapture.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gusev.test.cameracapture.R;

/**
 * Created with IntelliJ IDEA.
 * User: ivan
 * Date: 9/9/13
 * Time: 8:58 PM
 * May the force be with you always.
 */
public class PhotoView extends LinearLayout {

    private final ImageView imageView;
    private final TextView commentView;

    private int res;

    public PhotoView(Context context) {
        this(context, null);
    }

    public PhotoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(VERTICAL);
        inflate(context, R.layout.photo, this);
        imageView = (ImageView) findViewById(R.id.photo);
        commentView = (TextView) findViewById(R.id.comment);
    }

    public void setImage(int res) {
        this.res = res;
        imageView.setImageResource(res);
    }

    public int getRes() {
        return res;
    }

    public void setComment(CharSequence comment) {
        commentView.setText(comment);
    }

    public CharSequence getComment() {
        return commentView.getText();
    }

    public void setOnPhotoClickListener(final PhotoGallery.OnPhotoClickListener onPhotoClickListener) {
        if(onPhotoClickListener == null) return;
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onPhotoClickListener.onPhotoClick(PhotoView.this);
            }
        });
    }

    public void setOnCommentClickListener(final PhotoGallery.OnCommentClickListener onCommentClickListener) {
        if(onCommentClickListener == null) return;
        commentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onCommentClickListener.onCommentClick(PhotoView.this);
            }
        });
    }
}
