package edu.utep.cs.cs4381.quickninja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class FriendlyNinja extends GameObject {

    private static final Random random = new Random();
    private int maxX, minX;


    public FriendlyNinja(Context ctx, int screenX, int screenY) {
        super();
        bitmap = BitmapFactory.decodeResource(
                ctx.getResources(), R.drawable.friendly_run);
        maxX = screenX;
        maxY = screenY / 2;
        minX = 0;
        speed = random.nextInt(10) + 10;
        x = screenX;
        y = random.nextInt(maxY) - bitmap.getHeight();
        hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());

        manXPos = 0;
        frameWidth = 1190;
        frameHeight = 1500;
        frameCount = 5;
    }


    public void update(long fps, int playerSpeed) {
        x -= playerSpeed;
        x -= speed;

        /** Animation Stuff **/
        manXPos = manXPos + runSpeedPerSecond / fps;
        if (manXPos > bitmap.getWidth()) {
            manYPos += frameHeight;
            manXPos = 10;
        }
        if (manYPos + frameHeight > bitmap.getHeight()) {
            manYPos = 10;
        }

        /** Respawn when off screen **/
        if (x < minX - bitmap.getWidth()) {
            speed = random.nextInt(10) + 10;
            x = maxX;
            y = maxY - bitmap.getHeight();
        }

        /** Refresh hitbox location **/
        whereToDraw.roundOut(hitBox);

    }


    public void setX(int newX) {
        x = newX;
    }

    public int width() {
        return bitmap.getWidth();
    }
}