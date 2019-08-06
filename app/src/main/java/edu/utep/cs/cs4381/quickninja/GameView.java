package edu.utep.cs.cs4381.quickninja;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

    Context context;
    int screenWidth;
    int screenHeight;

    private boolean playing;
    private boolean gameEnded;
    boolean gamePaused = false;

    private PlayerNinja player;
    private List<EnemyNinja> enemyNinjas = new CopyOnWriteArrayList<>();
    private List<FriendlyNinja> friendlyNinjas = new CopyOnWriteArrayList<>();

    private int enemiesDefeated;
    private int timeTaken;

    SoundEffect soundEffect;
    private long hiScore;
    // For saving and loading the high score
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        holder = getHolder();
        paint = new Paint();
        this.context = context;

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
        player = new PlayerNinja(context, screenWidth, screenHeight);
        enemyNinjas.clear();

        enemyNinjas.add(new EnemyNinja(context, screenWidth, screenHeight));
//        dusts.clear();
//        for (int i = 0; i < 40; i++) {
//            dusts.add(new SpaceDust(screenWidth, screenHeight));
//        }

        /** Reset Time and Distance **/
        enemiesDefeated = 0; // 10 km
        timeTaken = 0;
//
//        timeStarted = System.currentTimeMillis();
        gameEnded = false;
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
                fps = 1000 / timeThisFrame;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                float x = motionEvent.getX();
                float y = motionEvent.getY();

                /** Check if Pause Button was pressed **/
                if (x > screenWidth - 140 && y < 140) {
                    gamePaused = true;
                    pause();
                }
                if (gamePaused) {
                    if (x > screenWidth - 140 && y < 140) {
                        gamePaused = false;
                    }
                }

                /** Tap left side of screen to jump **/
                if (x < screenWidth / 2 && y > 140) {

                }

                /** Tap right side of screen to attack **/
                if (x > screenWidth / 2 && y > 140) {

                }

                /** Restart Game **/
                if (gameEnded) {
                    startGame();
                }
                break;
        }
        return true;
    }

    private void draw () {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

            // Draw background image
            Bitmap gameBackground = BitmapFactory.decodeResource(getResources(), R.drawable.game_background);
            gameBackground = Bitmap.createScaledBitmap(gameBackground,screenWidth,(screenHeight / 2) + (screenWidth / 4),true);
            canvas.drawBitmap(gameBackground,0,0, paint);

            // Draw platform
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            paint.setAlpha(150); // Adjust transparency
            // You can change the second parameter of drawRect *(screenHeight/2)+(screenWidth/4)* to adjust the height of the platform.
            canvas.drawRect(0, (screenHeight / 2) + (screenWidth / 4), screenWidth, screenHeight, paint);
            paint.setAlpha(255); // Reset transparency

            /** Draw the Game Objects **/
            //canvas.drawColor(Color.argb(255, 0, 0, 0));
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(), player.getY(), paint);

//            for (EnemyShip enemy : enemyShips) {
//                canvas.drawBitmap(enemy.getBitmap(), enemy.getX(), enemy.getY(), paint);
//            }
//
//            for (SpaceDust sd : dusts) {
//                canvas.drawPoint(sd.getX(), sd.getY(), paint);
//            }
//
//            canvas.drawBitmap(friendShip.getBitmap(), friendShip.getX(), friendShip.getY(), paint);

            if (!gameEnded) {
                /** Draw the HUD **/
                paint.setStrokeWidth(4);
                paint.setTextSize(48);

                int yy = 50;
                paint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText(formatTime("Farthest", hiScore), 10, yy, paint);

                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(formatTime("Time", timeTaken), screenWidth / 2, yy, paint);
                canvas.drawText("Distance: " + enemiesDefeated / 1000 + " KM", screenWidth / 2, screenHeight - yy, paint);

                paint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("Speed: " + player.speed * 60 + " MPS", screenWidth - 10, screenHeight - yy, paint);

                /** Draw the Pause Button **/
                canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.pause),
                        screenWidth - 120, 0, paint);


                if (gamePaused) {
                    canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.play),
                            screenWidth - 120, 0, paint);
                    paint.setTextSize(100);
                    canvas.drawText("Game Paused", screenWidth * 2 / 3, screenHeight / 2, paint);
                    canvas.drawText("Tap to Resume", screenWidth * 2 / 3, screenHeight / 2 + 100, paint);
                }
            } else {
                /** Draw the Game Over screen **/
                paint.setTextSize(80);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Game Over", screenWidth / 2, 120, paint);
                paint.setTextSize(25);
                //canvas.drawText("Fastest:"+ fastestTime + "s", screenX/2, 160, paint);
                canvas.drawText(formatTime("Farthest", hiScore) + "s", screenWidth / 2, 180, paint);
                //canvas.drawText("Time:" + timeTaken + "s", screenX / 2, 200, paint);
                canvas.drawText(formatTime("Time", timeTaken) + "s", screenWidth / 2, 220, paint);
                canvas.drawText("Enemies Defeated:" + enemiesDefeated / 1000 + " KM", screenWidth / 2, 260, paint);
                paint.setTextSize(80);
                canvas.drawText("Tap to replay!", screenWidth / 2, 370, paint);
            }

            holder.unlockCanvasAndPost(canvas);

        }

    }


    private void update () {
        if (gamePaused) {
            return;
        }
        boolean hitDetected = false;
        boolean friendlyHitDetected = false;


//        for (EnemyShip ship : enemyShips) {
//            if (Rect.intersects(player.getHitbox(), ship.getHitbox())) {
//                ship.update(player.getSpeed());
//                hitDetected = true;
//                ship.setX(-ship.width());
//            }
//        }
//
//        if (hitDetected) {
//            soundEffect.play(SoundEffect.Sound.BUMP);
//            if (player.reduceShieldStrength() < 0) {
//                gameEnded = true;
//            }
//        }



        player.update(fps, 6);

//        for (EnemyShip enemy : enemyShips) {
//            enemy.update(player.getSpeed());
//        }

//        for (SpaceDust sd : dusts) {
//            sd.update(player.getSpeed());
//        }

//        friendShip.update(player.getSpeed());

//        if (!gameEnded) {
//            distanceRemaining -= player.getSpeed();
//            timeTaken = System.currentTimeMillis() - timeStarted;
//        }
//        if (distanceRemaining < 0) {
//            soundEffect.play(SoundEffect.Sound.WIN);
//            if (timeTaken < fastestTime) {
//                soundEffect.play(SoundEffect.Sound.DESTROYED);
//                // Save high score
//                editor.putLong("fastestTime", timeTaken);
//                editor.commit();
//                fastestTime = timeTaken;
//            }
//            distanceRemaining = 0;
//            gameEnded = true;
//        }

    }



    private String formatTime(String label, long time) { // time in milliseconds
        return String.format("%s: %d.%03ds", label, time / 1000, time % 1000);
    }

    private void control(){
        try {
            gameThread.sleep(17); // in milliseconds
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