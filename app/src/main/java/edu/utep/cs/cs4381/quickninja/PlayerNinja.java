package edu.utep.cs.cs4381.quickninja;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class PlayerNinja extends GameObject{
    private static final int GRAVITY = -12;

    protected boolean isFalling;
    protected boolean isJumping;
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

        /** Refresh hitbox location **/
        hitBox = frameToDraw;
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

        hitBox.set(x, y, x + bitmap.getWidth(), y + bitmap.getHeight());

    }

}
