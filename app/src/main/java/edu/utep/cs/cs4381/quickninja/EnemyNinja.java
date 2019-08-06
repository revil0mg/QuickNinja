package edu.utep.cs.cs4381.quickninja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class EnemyNinja extends GameObject {

    private static final Random random = new Random();
    private int maxX, minX;
    
    

    public EnemyNinja(Context ctx, int screenX, int screenY){
        super();
        // TODO: Add enemy ninja graphic
        bitmap = BitmapFactory.decodeResource(
                ctx.getResources(), R.drawable.enemy_single);
        bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, false);

        maxX = screenX;
        maxY = screenY / 2;
        minX = 0;
        speed = random.nextInt(10) + 10;
        x = screenX;
        y = maxY;
        hitBox = new Rect(x, y, x + bitmap.getWidth(), y + bitmap.getHeight());

//        runSpeedPerSecond = -3;
//        manXPos = 0;
//        manYPos = screenY / 2;
//        frameWidth = 1190;
//        frameHeight = 1500;
//        frameCount = 5;
    }


    public void update(long fps, int gameSpeed) {
        x -= gameSpeed;
        x -= speed;

//        /** Animation Stuff **/
//        manXPos = manXPos + runSpeedPerSecond / fps;
//        manYPos = 1196;
//        if (manXPos > bitmap.getWidth()) {
//            manYPos += frameHeight;
//            manXPos = 10;
//        }
//        if (manYPos + frameHeight > bitmap.getHeight()) {
//            manYPos = 10;
//        }
//
        /** Respawn when off screen **/
        if (x < minX - bitmap.getWidth()) {
            speed = random.nextInt(10)+15;
            x = maxX;
            y = maxY;
        }

        /** Refresh hitbox location **/
        hitBox = new Rect(x, y, x + bitmap.getWidth(), y + bitmap.getHeight());

    }
    

    public void setX(int newX) { x = newX; }

    public int width() {
        return bitmap.getWidth();
    }

}
