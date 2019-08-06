package edu.utep.cs.cs4381.quickninja;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameView extends SurfaceView implements Runnable {

    private SurfaceHolder holder;
    private Thread gameThread;

    private Canvas canvas;
    private Paint paint;
    private long startFrameTime;
    private long timeThisFrame;
    private long fps;
    private int gameSpeed;

    Context context;
    int screenWidth;
    int screenHeight;

    private boolean playing;
    boolean gamePaused = false;

    private PlayerNinja player;
    private EnemyNinja enemyNinja;
    private FriendlyNinja friendlyNinja;

    private int timeTaken;

    SoundEffect soundEffect;
    private long hiScore;
    // For saving and loading the high score
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private Bitmap playerBitmap;
    private float runSpeedPerSecond = 500;
    private float manXPos = 0, manYPos = 1196;
    private int frameWidth = 275, frameHeight = 450;
    private int frameCount = 5;
    private int currentFrame = 0;
    private long lastFrameChangeTime = 0;
    private int frameLengthInMillisecond = 13;

    private boolean gameOver;

    private Rect frameToDraw = new Rect(0, 0, frameWidth, frameHeight);
    private RectF whereToDraw = new RectF(manXPos, manYPos, manXPos + frameWidth, frameHeight);
    private int score;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        holder = getHolder();
        paint = new Paint();
        this.context = context;

        gameSpeed = 30;

        screenWidth = screenX;
        screenHeight = screenY;
        soundEffect = new SoundEffect(context);

        // Load fastest time
        prefs = context.getSharedPreferences("HiScores", Context.MODE_PRIVATE);
        // Initialize the editor ready
        editor = prefs.edit();
        // Load fastest time
        // if not available our highscore = 0
        hiScore = prefs.getLong("hiScore", 0);

        startGame();
    }

    private void startGame () {
        /** Initialize Game Objects **/
        if (gameOver) {
            Intent i = new Intent(context, GameOverActivity.class);
            i.putExtra("score", score);
            i.putExtra("hiScore", hiScore);
            context.startActivity(i);
            gameOver = false;
        }
        player = new PlayerNinja(context, screenWidth, screenHeight);

        playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_run);
        playerBitmap = Bitmap.createScaledBitmap(playerBitmap, frameWidth * frameCount, frameHeight, false);

        enemyNinja = new EnemyNinja(context, screenWidth, screenHeight);
        friendlyNinja = new FriendlyNinja(context, screenWidth, screenHeight);


//        dusts.clear();
//        for (int i = 0; i < 40; i++) {
//            dusts.add(new SpaceDust(screenWidth, screenHeight));
//        }

        /** Reset Score **/
        timeTaken = 0;
        score = 0;

//
//        timeStarted = System.currentTimeMillis();
        gamePaused = false;
    }

    @Override
    public void run () {
        while (playing) {
            startFrameTime = System.currentTimeMillis();
            update();
            draw();
            control();
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1200 / timeThisFrame;
            }
        }
    }


    private void draw () {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();


            // Draw background image
            Bitmap gameBackground = BitmapFactory.decodeResource(getResources(), R.drawable.game_background);
            gameBackground = Bitmap.createScaledBitmap(gameBackground, screenWidth, (screenHeight / 2) + (screenWidth / 4), true);
            canvas.drawBitmap(gameBackground, 0, 0, paint);

            // Draw platform

            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            paint.setAlpha(150); // Adjust transparency
            // You can change the second parameter of drawRect *(screenHeight/2)+(screenWidth/4)* to adjust the height of the platform.
            canvas.drawRect(0, (screenHeight / 2) + (screenWidth / 4), screenWidth, screenHeight, paint);
            paint.setAlpha(255); // Reset transparency
            paint.setColor(Color.WHITE);
            /** Draw the Game Objects **/
            // Animate Player
//            player.whereToDraw.set((int) player.manXPos, (int) player.manYPos,
//                    (int) player.manXPos + player.frameWidth, (int) player.manYPos + player.frameHeight);
//            player.manageCurrentFrame();
//            canvas.drawBitmap(player.getBitmap(), player.frameToDraw, player.whereToDraw, null);
//            Log.e("---------", String.valueOf(player.whereToDraw.top));
//            Log.e("---------", String.valueOf(player.whereToDraw.left));
//            whereToDraw.set((int) manXPos, (int) manYPos, (int) manXPos + frameWidth, (int) manYPos + frameHeight);
//            manageCurrentFrame();
//            canvas.drawBitmap(playerBitmap, frameToDraw, whereToDraw, null);
            canvas.drawBitmap(player.getBitmap(), player.x, player.y, paint);


            paint.setStyle(Paint.Style.FILL);
            //canvas.drawRect(player.getHitbox(), paint);

            // Draw Enemy Ninjas
            canvas.drawBitmap(enemyNinja.getBitmap(), enemyNinja.x, enemyNinja.y, paint);
            canvas.drawBitmap(friendlyNinja.getBitmap(), friendlyNinja.x, friendlyNinja.y, paint);

            canvas.drawRect(player.attackHitbox, paint);
//            for (EnemyNinja enemy : enemyNinjas) {
//                enemy.whereToDraw.set((int) enemy.manXPos, (int) enemy.manYPos,
//                        (int) enemy.manXPos + enemy.frameWidth, (int) enemy.manYPos + enemy.frameHeight);
//                enemy.manageCurrentFrame();
//                canvas.drawBitmap(enemy.getBitmap(), enemy.frameToDraw, enemy.whereToDraw, null);
//            }
//
//            // Draw Friendly Ninjas
//            for (FriendlyNinja friend : friendlyNinjas) {
//                friend.whereToDraw.set((int) friend.manXPos, (int) friend.manYPos,
//                        (int) friend.manXPos + friend.frameWidth, (int) friend.manYPos + friend.frameHeight);
//                friend.manageCurrentFrame();
//                canvas.drawBitmap(friend.getBitmap(), friend.frameToDraw, friend.whereToDraw, null);
//            }
//
//            for (SpaceDust sd : dusts) {
//                canvas.drawPoint(sd.getX(), sd.getY(), paint);
//            }
//
//            canvas.drawBitmap(friendShip.getBitmap(), friendShip.getX(), friendShip.getY(), paint);


            /** Draw the HUD **/
            paint.setStrokeWidth(4);
            paint.setTextSize(48);

            int yy = 50;
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText("Score: " + score, 10, yy, paint);

            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("HighScore: " + hiScore, screenWidth / 2, yy, paint);


            if (gameOver) {
                paint.setTextSize(100);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Game Over!", screenWidth / 2, screenHeight / 2, paint);
                canvas.drawText("Tap to View Results!", screenWidth / 2, screenHeight / 2 + 100, paint);
            }

            if (gamePaused) {
                canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.play),
                        screenWidth - 120, 0, paint);
                paint.setTextSize(100);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Game Paused", screenWidth / 2, screenHeight / 2, paint);
                canvas.drawText("Tap Play to Resume", screenWidth / 2, screenHeight / 2 + 100, paint);
            }
            else {
                /** Draw the Pause Button **/
                canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.pause),
                        screenWidth - 120, 0, paint);
            }

            holder.unlockCanvasAndPost(canvas);

        }
    }

    private void update () {
        if (gamePaused) {
            return;
        }
        boolean hitDetected = false;


        /** Check Collision between Player and enemy ninjas **/
        if (Rect.intersects(player.getHitbox(), enemyNinja.getHitbox())) {
            soundEffect.play(SoundEffect.Sound.BUMP);
            enemyNinja.update(fps, gameSpeed);
            hitDetected = true;
            gameOver = true;
            enemyNinja.setBitmap(context, R.drawable.enemy_single_swing, false);
        }

        /** Check Collision between Player Sword and Enemy Ninjas **/
        if ((Rect.intersects(player.getAttackHitbox(), enemyNinja.getHitbox()))) {
            soundEffect.play(SoundEffect.Sound.BUMP);
            enemyNinja.update(fps, gameSpeed);
            score++;
            enemyNinja.setX(screenWidth);
        }

        /** Check Collision between Player Sword and Friendly Ninjas **/
        if ((Rect.intersects(player.getAttackHitbox(), friendlyNinja.getHitbox()))) {
            friendlyNinja.update(fps, gameSpeed);
            hitDetected = true;
            friendlyNinja.setX(screenWidth);
            friendlyNinja.setBitmap(context, R.drawable.friendly_single_hit, false);
        }

        if (hitDetected) {
            player.bitmap = BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.player_single_hit);
            player.bitmap = Bitmap.createScaledBitmap(player.bitmap, 300, 220, false);
            player.y += 500;

            enemyNinja.setSpeed(0);
            gameSpeed = 0;
            if (score > hiScore) {
                editor.putLong("hiScore", score);
                editor.commit();
                hiScore = score;
            }
            gameOver = true;

        }

        player.update(fps, gameSpeed);
        enemyNinja.update(fps, gameSpeed);
        friendlyNinja.update(fps, gameSpeed);


    }

    public void manageCurrentFrame() {
        long time = System.currentTimeMillis();

        if (time > lastFrameChangeTime + frameLengthInMillisecond) {
            lastFrameChangeTime = time;
            currentFrame++;
            if (currentFrame >= frameCount) {
                currentFrame = 0;
            }
        }

        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                Log.e("==========", "x: " + x + "// y: " + y);

                /** Check if Pause Button was pressed **/
                if (x > screenWidth - 140 && y < 140) {
                    if (gamePaused) {
                        gamePaused = false;
                        resume();
                    }
                    else {
                        gamePaused = true;
                        pause();
                    }
                }

                /** Tap left side of screen to jump **/
                if (x <= screenWidth / 2 && y > 140) {
                    player.jump();
                }

                /** Tap right side of screen to attack **/
                if (x >= screenWidth / 2 && y > 140) {
                    player.attack(context);
                }

                if (gameOver)
                    startGame();

                break;
        }
        return true;
    }

    private void control(){
        try {
            gameThread.sleep(8); // in milliseconds
        } catch (InterruptedException e) {
        }
    }

    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

}