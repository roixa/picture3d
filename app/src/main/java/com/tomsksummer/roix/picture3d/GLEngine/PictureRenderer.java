package com.tomsksummer.roix.picture3d.GLEngine;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import com.tomsksummer.roix.picture3d.GLEngine.models3d.PictureSurface;
import com.tomsksummer.roix.picture3d.R;
import com.tomsksummer.roix.picture3d.interfaces.TouchSurfaceListener;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by root on 06.08.14.
 */
public class PictureRenderer implements GLSurfaceView.Renderer, TouchSurfaceListener {
    private final String TAG ="PictureRenderer";

    public AccelModule base;
    public Context context;
    private Texture box;
    private PictureSurface pictureSurface;
    private boolean touched;
    CoordHelper coord;


    public void setContext(Context context) {
        base=new AccelModule();
        this.context = context;
        //cube=new Cube();
        pictureSurface=new PictureSurface(this.context, R.drawable.room);
        touched=false;
        coord=new CoordHelper(0,0,6);
    }



    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);  // Set color's clear-value to black
        gl.glClearDepthf(1.0f);            // Set depth's clear-value to farthest
        gl.glEnable(GL10.GL_DEPTH_TEST);   // Enables depth-buffer for hidden surface removal
        gl.glDepthFunc(GL10.GL_LEQUAL);    // The type of depth testing to do
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);  // nice perspective view
        gl.glShadeModel(GL10.GL_SMOOTH);   // Enable smooth shading of color
        gl.glDisable(GL10.GL_DITHER);      // Disable dithering for better performance
        pictureSurface.loadTextures(gl);
        //pictureSurface.loadTexture(gl,context);
        //cube.loadTexture(gl, context);    // Load image into Texture (NEW)
        gl.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture (NEW)
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (height == 0) height = 1;   // To prevent divide by zero
        float aspect = (float)width / height;

        // Set the viewport (display area) to cover the entire window
        gl.glViewport(0, 0, width, height);

        // Setup perspective projection, with aspect ratio matches viewport
        gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
        gl.glLoadIdentity();                 // Reset projection matrix
        // Use perspective projection
        GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);  // Select model-view matrix
        gl.glLoadIdentity();                 // Reset

        // You OpenGL|ES display re-sizing code here
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //if(touched) return;

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);



        gl.glLoadIdentity();                  // Reset the current model-view matrix
        gl.glTranslatef(-coord.camX, -coord.camY, -coord.camZ);   // Translate into the screen

        if(!touched){
        gl.glRotatef(base.getXAngle(), 1.0f, 0.0f, 0.0f); // Rotate accel x
        gl.glRotatef(base.getYAngle(), 0.0f, 1.0f, 0.0f); // Rotate accel y
        gl.glRotatef(base.getZAngle(), 0.0f, 0.0f, 1.0f); // Rotate accel y
        //
        }
        else  base.returnSurfaceAngle();
        //cube.draw(gl);
        pictureSurface.draw(gl);

        // Update the rotational angle after each refresh.



    }

    @Override
    public void OnTouch(float x,float y) {
        pictureSurface.touch(coord.getX(x),coord.getY(y));



    }

    @Override
    public void OnStartTouch() {
        touched=true;
        Log.d(TAG,"start touch");
    }

    @Override
    public void OnEndTouch() {
        touched=false;
        pictureSurface.untouch();
        Log.d(TAG,"stop touch");
    }
}
