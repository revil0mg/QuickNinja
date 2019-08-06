package edu.utep.cs.cs4381.quickninja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class PlayerNinja extends GameObject{
    private static final int GRAVITY = -12;

    protected boolean isFalling;
    protected boolean isJumping;
    protected boolean isAttacking;
    private long jumpTime;
    private long maxJumpTime = 700; // jump 7 10ths of second
    protected int speed;

    public PlayerNinja(Context context, int screenX, int screenY) {
        super();

        bitmap = BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player_run);

        x = 50;
        y = screenY / 2 - bitmap.getHeight();
        speed = 1;

        maxY = screenY - bitmap.getHeight(); // Q: why?
        if (isJumping) {
            bitmap = BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.player_jump);
        }
        else {
            runSpeedPerSecond = 0;
            manXPos = 0;
            frameWidth = 1190;
            frameHeight = 1500;
            frameCount = 5;
        }

        /** Refresh hitbox location **/
        hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }


    public void update(long fps, float gravity) {

        /** Animation Stuff **/
        manXPos = manXPos + runSpeedPerSecond / fps;
        if (manXPos > bitmap.getWidth()) {
            manYPos += frameHeight;
            manXPos = 10;
        }
        if (manYPos + frameHeight > bitmap.getHeight()) {
            manYPos = 10;
        }

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

        whereToDraw.roundOut(hitBox);

    }

    public void jump() {
        isJumping = true;
    }

    public void attack() {
        isAttacking = true;
    }
}
