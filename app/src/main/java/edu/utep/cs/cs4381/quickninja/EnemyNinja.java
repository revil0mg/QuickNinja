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
                ctx.getResources(), R.drawable.player_run);
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        speed = random.nextInt(10) + 10;
        x = screenX;
        y = random.nextInt(maxY) - bitmap.getHeight();
        hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }


    public void update(int playerSpeed) {
        x -= playerSpeed;
        x -= speed;

        /** Respawn when off screen **/
        if (x < minX - bitmap.getWidth()) {
            speed = random.nextInt(10)+10;
            x = maxX;
            y = random.nextInt(maxY) - bitmap.getHeight();
        }

        /** Refresh hitbox location **/
        hitBox.set(x, y, x + bitmap.getWidth(), y + bitmap.getHeight());

    }
    

    public void setX(int newX) { x = newX; }

    public int width() {
        return bitmap.getWidth();
    }

}
