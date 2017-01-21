package com.waveplatform;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;


public class WaveActivity extends Activity {

    WaveView Wave;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Wave = new WaveView(this);
        setContentView(Wave);
    }
    class WaveView extends SurfaceView implements Runnable {
        Thread gameThread = null; //game thread
        SurfaceHolder holder;
        volatile boolean playing;//are we playing?
        boolean paused = true;
        Canvas canvas;
        Paint paint;
        long fps;
        long timeThisFrame;
        private int lives = 3;
        private int score = 0;
        private int screenX;
        private int screenY;
        private int rx;


        public WaveView(Context context) {
            super(context);

            holder = getHolder();
            paint = new Paint();

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            screenX = size.x;
            screenY = size.y;
        }

        @Override
        public void run() {
            while (playing) {
                long startTimeFrame = System.currentTimeMillis();
                if (!paused)
                    //update();//update movements
                draw();//draw objects
                timeThisFrame = System.currentTimeMillis() - startTimeFrame;
                if (timeThisFrame >= 1)
                    fps = 1000 / timeThisFrame;
            }
        }

        public void update() {

        }

        public void draw() {
            if (holder.getSurface().isValid()) {
                canvas.drawColor(Color.WHITE);
            }
        }

        private boolean isAlive() {
            return lives > 0;
        }

        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }
        }

        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {

        return true;
    }
}
    public void onResume(){
        super.onResume();

       Wave.resume();
    }
    public void onPause(){
        super.onPause();

        Wave.pause();
    }
}