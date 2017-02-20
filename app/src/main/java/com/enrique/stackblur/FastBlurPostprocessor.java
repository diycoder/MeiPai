package com.enrique.stackblur;

import android.graphics.Bitmap;

import com.facebook.imagepipeline.request.BasePostprocessor;

/**
 * Created by MuMu on 2017/01/07.
 */

public class FastBlurPostprocessor extends BasePostprocessor {

    private float mRadius;

    public FastBlurPostprocessor(float blurRadius) {
        this.mRadius = blurRadius;
    }

    public void process(Bitmap bitmap) {
        try {
            bitmap.setHasAlpha(true);
            NativeBlurProcess blur = new NativeBlurProcess();
            blur.blur(bitmap, mRadius);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return "FastBlurPostprocessor";
    }
}
