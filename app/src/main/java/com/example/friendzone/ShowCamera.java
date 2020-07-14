package com.example.friendzone;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback {

    Camera camera;
    SurfaceHolder holder;

    public ShowCamera(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        holder = getHolder();
        holder.addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Camera.Parameters param = camera.getParameters();

        List<Camera.Size> sizes = param.getSupportedPictureSizes();
        Camera.Size mSize = null;

        for(Camera.Size size : sizes)
        {
            mSize = size;
        }

        if(this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)
        {
            param.set("orientation", "portrait");
            camera.setDisplayOrientation(90);
            param.setRotation(90);
        }  else {
             param.set("orientation","landscape");
             camera.setDisplayOrientation(0);
             param.setRotation(0);
        }

        param.setPictureSize(mSize.width,mSize.height);

        camera.setParameters(param);
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
       camera.stopPreview();
    camera.release();

    }
}


