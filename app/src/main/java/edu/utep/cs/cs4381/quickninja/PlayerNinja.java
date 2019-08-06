package edu.utep.cs.cs4381.quickninja;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class PlayerNinja extends GameObject{
    private static final int GRAVITY = -12;

    private int shieldStrength;
    public boolean isFalling;
    private boolean isJumping;
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

        // Set this object up to be animated
        final int ANIMATION_FPS = 16;
        final int ANIMATION_FRAME_COUNT = 5;

//        setAnimFps(ANIMATION_FPS);
//        setAnimFrameCount(ANIMATION_FRAME_COUNT);
//        setAnimated(context, pixelsPerMeter, true);

        /** Refresh hitbox location **/
        hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }


    public void update(long fps, float gravity) {
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
