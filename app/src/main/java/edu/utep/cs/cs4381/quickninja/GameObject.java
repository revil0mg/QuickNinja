package edu.utep.cs.cs4381.quickninja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

public abstract class GameObject {
    protected int x, y;
    public int speed;
    protected int maxY, minY;
    private float yVelocity;
    
    protected Bitmap bitmap;
    protected Rect hitBox;

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

//    public Rect getRectToDraw(long currentTimeMillis) {
//        return anim.getCurrentFrame(currentTimeMillis, xVelocity, isMoves());
//    }
//
//    public void setAnimFps(int animFps) {
//        this.animFps = animFps;
//    }
//
//    public void setAnimFrameCount(int animFrameCount) {
//        this.animFrameCount = animFrameCount;
//    }
//
//    public void setAnimated(Context context, int pixelsPerMeter, boolean animated){
//        this.animated = animated;
//        this.anim = new Animation(context, bitmapName,
//                height,
//                width,
//                animFps,
//                animFrameCount,
//                pixelsPerMeter );
//    }
}
