package edu.utep.cs.cs4381.quickninja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.graphics.Rect;
import android.util.Log;

import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerNinja extends GameObject{
    private static final int GRAVITY = -200;

    protected boolean isFalling;
    protected boolean isJumping;
    protected boolean isAttacking;
    private long jumpTime;
    private long maxJumpTime = 3000; // jump 7 10ths of second
    protected int speed;
    private int screenYRes;
    Rect attackHitbox;



    public PlayerNinja(Context context, int screenX, int screenY) {
        super();

        attackHitbox = new Rect();
        isFalling = false;

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
        hitBox = new Rect(x, y, x + bitmap.getWidth() - 10, y + bitmap.getHeight());
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
                Log.e("ddddddddDNPWODF", "We Set isJumping");
                if (timeJumping < maxJumpTime / 2) {
                    Log.e("ddddddddDNPWODF", "We in boys");
                    y += GRAVITY; // on the way up
                }
                else if (timeJumping > maxJumpTime / 2) {
                    y -= GRAVITY; // going down
                }
            }
            else {
                isJumping = false;
            }
        }
        else {
            y -= GRAVITY;
            isFalling = true;
        }

        /** Keep the player at the left side of the screen **/
        if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
        }

        hitBox = new Rect(x, y, x + bitmap.getWidth() - 10, y + bitmap.getWidth());
//        whereToDraw.roundOut(hitBox);

    }

    public void jump() {
        if (!isFalling && // can't jump if falling
                !isJumping) { // not already jumping?
            isJumping = true;
            jumpTime = System.currentTimeMillis();
        }
    }

    public void attack(Context ctx) {
        isAttacking = true;
        bitmap = BitmapFactory.decodeResource(
                ctx.getResources(), R.drawable.player_single_attack);
        attackHitbox = new Rect(x + bitmap.getWidth() - 10, y, x + 120, y + bitmap.getHeight());
        bitmap = Bitmap.createScaledBitmap(bitmap, 400, 350, false);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                sheatheSword(ctx);
                Log.e("==============++", "Sword Sheathed");
            }
        }, 1000); // 1 seconds
    }

    private void sheatheSword(Context ctx) {
        bitmap = BitmapFactory.decodeResource(
                ctx.getResources(), R.drawable.player_single);
        bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, false);
    }

    public Rect getAttackHitbox() {
        return attackHitbox;
    }
}
