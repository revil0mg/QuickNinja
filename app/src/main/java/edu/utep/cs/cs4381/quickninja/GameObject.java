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

    private Bitmap bitmapRunningMan;
    private boolean isMoving;
    private float runSpeedPerSecond = 500;
    private float manXPos = 1196, manYPos = 1196;
    private int frameWidth = 230, frameHeight = 274;
    private int frameCount = 8;
    private int currentFrame = 0;
    private long lastFrameChangeTime = 0;
    private int frameLengthInMillisecond = 50;

    private Rect frameToDraw = new Rect(0, 0, frameWidth, frameHeight);
    private RectF whereToDraw = new RectF(manXPos, manYPos, manXPos + frameWidth, frameHeight);

//    /** Animation Stuff **/
//    protected float runSpeedPerSecond = 250;
//    protected float manXPos, manYPos = 1196;
//    protected int frameWidth, frameHeight;
//    protected int frameCount;
//    protected int currentFrame = 0;
//
//    protected long lastFrameChangeTime = 10;
//    protected int frameLengthInMillisecond = 50;
//
//    protected Rect frameToDraw = new Rect(0, (int) manYPos, frameWidth, frameHeight);
//    protected RectF whereToDraw = new RectF(manXPos, manYPos, manXPos + frameWidth, manYPos + frameHeight);

    public GameObject() {
        minY = 0;
    }

    public Bitmap getBitmap() {  return bitmap; }

    public Rect getHitbox() { return hitBox; }

    public int getX() { return x; }

    public int getY() { return y; }

    protected void setyVelocity(float yv) {
        yVelocity = yv;
    }


//    /** Animation Stuff **/
//    public void manageCurrentFrame() {
//        long time = System.currentTimeMillis();
//        if (time > lastFrameChangeTime + frameLengthInMillisecond) {
//            lastFrameChangeTime = time;
//            currentFrame++;
//            if (currentFrame >= frameCount) {
//                currentFrame = 0;
//            }
//        }
//
//        frameToDraw.left = currentFrame * frameWidth;
//        frameToDraw.right = frameToDraw.left + frameWidth;
//    }

}
