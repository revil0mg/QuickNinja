package edu.utep.cs.cs4381.quickninja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;

public abstract class GameObject {
    protected int x, y;
    public int speed;
    protected int maxY, minY;
    private float yVelocity;
    
    protected Bitmap bitmap;
    protected Rect hitBox;

    protected boolean isMoving = true;
    protected float runSpeedPerSecond = 1000;
    protected float manXPos = 0, manYPos = 0;
    protected int frameWidth = 1300, frameHeight = 1500;
    protected int frameCount = 6;
    protected int currentFrame = 0;
    protected long fps;
    protected long timeThisFrame;
    protected long lastFrameChangeTime = 0;
    protected int frameLengthInMillisecond = 50;

    protected Rect frameToDraw = new Rect(0, 0, frameWidth, frameHeight);
    protected RectF whereToDraw = new RectF(manXPos, manYPos, manXPos + frameWidth, frameHeight);

    public GameObject() {
        //minY = 0;
    }

    public Bitmap getBitmap() {  return bitmap; }

    public Rect getHitbox() { return hitBox; }

    public int getX() { return x; }

    public int getY() { return y; }

    protected void setyVelocity(float yv) {
        yVelocity = yv;
    }

    public void manageCurrentFrame() {
        long time = System.currentTimeMillis();
        if (isMoving) {
            if (time > lastFrameChangeTime + frameLengthInMillisecond) {
                lastFrameChangeTime = time;
                currentFrame++;
                if (currentFrame >= frameCount) {
                    currentFrame = 0;
                }
            }
        }
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;
    }

}
