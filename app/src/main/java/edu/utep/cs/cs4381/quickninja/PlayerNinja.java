package edu.utep.cs.cs4381.quickninja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.graphics.Rect;

import java.io.InputStream;

public class PlayerNinja extends GameObject{
    private static final int GRAVITY = -12;

    protected boolean isFalling;
    protected boolean isJumping;
    protected boolean isAttacking;
    private long jumpTime;
    private long maxJumpTime = 2000; // jump 7 10ths of second
    protected int speed;
    private int screenYRes;



    public PlayerNinja(Context context, int screenX, int screenY) {
        super();


        screenYRes = screenY;
        maxY = screenY/2;

        if (isJumping) {
            bitmap = BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.player_jump);
            //TODO: Add Jumping animation
        }
        else if (isAttacking) {
            bitmap = BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.player_attack);
            //TODO: Add attacking animation
        }
        else {
            //TODO: Shrink player for other animations

            bitmap = BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.player_single);
            bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, false);
//            bitmap = Bitmap.createScaledBitmap(bitmap, frameWidth * frameCount, frameHeight, false);
        }
        x = 0;
        y = screenY / 2;

//        /** Refresh hitbox location **/
        hitBox = new Rect(x, y, x + bitmap.getWidth(), y + bitmap.getHeight());
//        hitBox = frameToDraw;
//        whereToDraw.roundOut(hitBox);
    }


    public void update(long fps, float gravity) {

//        /** Animation Stuff **/
//        manXPos = manXPos + runSpeedPerSecond / fps;
//        if (manXPos > bitmap.getWidth()) {
////            manYPos += frameHeight;
//            manXPos = 10;
//        }
//        if (manYPos + frameHeight > bitmap.getHeight()) {
////            manYPos = (float) screenYRes / 2;
//            manXPos = 0;
//        }

        if (isJumping) {
            long timeJumping = System.currentTimeMillis() - jumpTime;
            if (timeJumping < maxJumpTime) {
                if (timeJumping < maxJumpTime / 2) {
                    setyVelocity(-gravity); // on the way up
                } else if (timeJumping > maxJumpTime / 2) {
                    setyVelocity(gravity); // going down
                }
            } else {
                isJumping = false;
            }
        } else {
            setyVelocity(gravity);
            isFalling = true;
        }
        /** Keep the player at the left side of the screen **/
        if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
        }

        hitBox = new Rect(x, y, x + bitmap.getWidth(), y + bitmap.getWidth());
//        whereToDraw.roundOut(hitBox);

    }

    public void jump() {
        isJumping = true;
    }

    public void attack() {
        isAttacking = true;
    }
}
