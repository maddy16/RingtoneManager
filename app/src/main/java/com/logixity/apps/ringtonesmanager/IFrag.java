package com.logixity.apps.ringtonesmanager;

import android.widget.ImageView;

/**
 * Created by ahmed on 04/06/2017.
 */

public interface IFrag {
    public ImageView getCurrentView();
    public void setCurrentView(ImageView view);
    void setSelectedImage(int i);
    int getSelectedImage();
}
