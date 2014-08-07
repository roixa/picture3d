package com.tomsksummer.roix.picture3d.GLEngine;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import com.tomsksummer.roix.picture3d.interfaces.TouchSurfaceListener;

import java.nio.FloatBuffer;

/**
 * Created by root on 06.08.14.
 */
public class PictureSurfaceView extends GLSurfaceView {

    public final String TAG="PictureSurfaceView";
    public  PictureRenderer renderer;

    public PictureSurfaceView(Context context) {
        super(context);
        renderer = new PictureRenderer();
        renderer.setContext(this.getContext());
        setRenderer(renderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, we are only
        // interested in events where the touch position changed.
        TouchSurfaceListener touchListener=renderer;

        float x = e.getX();
        float y = e.getY();


        //this convert to normal coords [-1,1]
        float w=this.getWidth();
        float h=this.getHeight();

        float nx=x*2/w-1f;
        float ny=y*2/h-1f;



        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchListener.OnStartTouch();
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.d(TAG,"on touch x, y: "+String.valueOf(x)+" "+String.valueOf(y));
                //requestRender();

                //-1 for in gl y inverted
                touchListener.OnTouch(nx,-ny);

                break;
            case MotionEvent.ACTION_UP:
                touchListener.OnEndTouch();
        }


        return true;
    }

}
