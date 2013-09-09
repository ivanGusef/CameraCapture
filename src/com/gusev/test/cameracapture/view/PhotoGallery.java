package com.gusev.test.cameracapture.view;

import android.R;
import android.animation.LayoutTransition;
import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import com.gusev.test.cameracapture.model.Photo;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * Creator: Gusev Ivan (ivan.gusev@altarix.ru)
 * Date: 09.09.13
 * Time: 16:29
 * May the Force be with you, always
 */
public class PhotoGallery extends LinearLayout {

    private Integer padding;

    private OnPhotoClickListener onPhotoClickListener;
    private OnCommentClickListener onCommentClickListener;

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

    public void setOnPhotoClickListener(OnPhotoClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
        LinearLayout line;
        PhotoView photoView;
        for (int i = 0; i < getChildCount(); i++) {
            line = getChildAt(i);
            for (int j = 0; j < line.getChildCount(); j++) {
                photoView = (PhotoView) line.getChildAt(j);
                photoView.setOnPhotoClickListener(onPhotoClickListener);
            }
        }
    }

    public void setOnCommentClickListener(OnCommentClickListener onCommentClickListener) {
        this.onCommentClickListener = onCommentClickListener;
        LinearLayout line;
        PhotoView photoView;
        for (int i = 0; i < getChildCount(); i++) {
            line = getChildAt(i);
            for (int j = 0; j < line.getChildCount(); j++) {
                photoView = (PhotoView) line.getChildAt(j);
                photoView.setOnCommentClickListener(onCommentClickListener);
            }
        }
    }

    public void addPhoto(Photo photo) {
        addPhoto(photo.res, photo.comment);
    }

    public void addPhotos(Collection<Photo> photos) {
        for (Photo photo : photos) {
            addPhoto(photo.res, photo.comment);
        }
    }

    private void addPhoto(int res, String comment) {
        PhotoView photoItem = new PhotoView(getContext());
        LinearLayout line;
        if (isNewLineRequired(photoItem)) {
            (line = addLine()).addView(configurePhotoView(photoItem, line, res, comment));
        } else {
            (line = getChildAt(getChildCount() - 1)).addView(configurePhotoView(photoItem, line, res, comment));
        }
    }

    public void removePhoto(PhotoView photoView) {
        LinearLayout line = (LinearLayout) photoView.getTag();
        if (line.getChildCount() == 1) {
            removeView(line);
        } else {
            line.removeView(photoView);
            shift((Integer) line.getTag());
        }
    }

    private void shift(int startIndex) {
        LinearLayout line = getChildAt(startIndex);
        LinearLayout nextLine = getChildAt(startIndex + 1);
        PhotoView replaceableView;
        if (nextLine != null) {
            replaceableView = (PhotoView) nextLine.getChildAt(0);
            line.addView(newPhotoView(line, replaceableView.getRes(), replaceableView.getComment()));
            if (nextLine.getChildCount() == 1) {
                removeView(nextLine);
            } else {
                nextLine.removeViewAt(0);
                shift(++startIndex);
            }
        }
    }

    private PhotoView newPhotoView(LinearLayout line, int res, CharSequence comment) {
        PhotoView photoView = new PhotoView(getContext());
        configurePhotoView(photoView, line, res, comment);
        return photoView;
    }

    private PhotoView configurePhotoView(PhotoView photoView, LinearLayout line, int res, CharSequence comment) {
        photoView.setImage(res);
        photoView.setComment(comment);
        photoView.setOnPhotoClickListener(onPhotoClickListener);
        photoView.setOnCommentClickListener(onCommentClickListener);
        photoView.setPadding(padding / 2, padding / 2, padding / 2, padding / 2);
        photoView.setTag(line);
        return photoView;
    }

    private boolean isNewLineRequired(View view) {
        view.measure(0, 0);
        int lineWidth = getMeasuredWidth();
        int width = view.getMeasuredWidth();
        if (padding == null) {
            int countPerRow = lineWidth / width;
            padding = lineWidth / countPerRow - width;
        }
        if (getChildCount() == 0) {
            return true;
        }
        LinearLayout lastLine = getChildAt(getChildCount() - 1);
        int contentWidth = 0;
        for (int i = 0; i < lastLine.getChildCount(); i++) {
            contentWidth += lastLine.getChildAt(0).getMeasuredWidth();
        }
        return lineWidth - contentWidth - padding < width;
    }

    private LinearLayout addLine() {
        LinearLayout line = new LinearLayout(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        line.setLayoutParams(params);
        line.setLayoutTransition(new LayoutTransition());
        line.setTag(getChildCount());
        addView(line);
        return line;
    }

    public static interface OnPhotoClickListener {
        void onPhotoClick(PhotoView photoView);
    }

    public static interface OnCommentClickListener {
        void onCommentClick(PhotoView photoView);
    }
}
