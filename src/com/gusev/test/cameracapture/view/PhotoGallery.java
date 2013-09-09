package com.gusev.test.cameracapture.view;

import android.animation.LayoutTransition;
import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gusev.test.cameracapture.R;

/**
 * Created with IntelliJ IDEA.
 * Creator: Gusev Ivan (ivan.gusev@altarix.ru)
 * Date: 09.09.13
 * Time: 16:29
 * May the Force be with you, always
 */
public class PhotoGallery extends LinearLayout {

    private static final int PADDING = 10;

    private OnClickListener onPhotoClickListener;
    private OnClickListener onCommentClickListener;

    public PhotoGallery(Context context) {
        this(context, null);
    }

    public PhotoGallery(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(VERTICAL);
    }

    @Override
    public LinearLayout getChildAt(int index) {
        return (LinearLayout) super.getChildAt(index);
    }

    public void setOnPhotoClickListener(OnClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
    }

    public void setOnCommentClickListener(OnClickListener onCommentClickListener) {
        this.onCommentClickListener = onCommentClickListener;
    }

    public void addPhoto(Uri uri, String comment) {
        View photoItem = inflate(getContext(), R.layout.photo, null);
        LinearLayout line;
        if (isNewLineRequired(photoItem)) {
            (line = addLine()).addView(photoItem);
        } else {
            (line = getChildAt(getChildCount() - 1)).addView(photoItem);
        }
        ImageView photoView = (ImageView) photoItem.findViewById(R.id.photo);
        photoView.setImageURI(uri);
        photoView.setOnClickListener(onPhotoClickListener);
        photoView.setTag(line);
        TextView commentView = (TextView) photoItem.findViewById(R.id.comment);
        commentView.setText(comment);
        commentView.setOnClickListener(onCommentClickListener);
    }

    public void removePhoto(ImageView imageView) {
        LinearLayout line = (LinearLayout) imageView.getTag();
        if(line.getChildCount() == 1) {
            removeView(line);
        } else {
            line.removeView((View) imageView.getParent());
        }
    }

    private boolean isNewLineRequired(View view) {
        view.measure(0, 0);
        int width = view.getMeasuredWidth();
        if (getChildCount() == 0) {
            return true;
        }
        LinearLayout lastLine = getChildAt(getChildCount() - 1);
        int lineWidth = lastLine.getMeasuredWidth();
        int contentWidth = 0;
        for (int i = 0; i < lastLine.getChildCount(); i++) {
            contentWidth += lastLine.getChildAt(0).getMeasuredWidth();
        }
        return lineWidth - contentWidth - PADDING < width;
    }

    private LinearLayout addLine() {
        LinearLayout line = new LinearLayout(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        line.setLayoutParams(params);
        line.setLayoutTransition(new LayoutTransition());
        addView(line);
        return line;
    }
}
